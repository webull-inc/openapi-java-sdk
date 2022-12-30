/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.auth.composer;

import com.webull.openapi.auth.signer.Signer;
import com.webull.openapi.auth.signer.SignerFactory;
import com.webull.openapi.common.Headers;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.http.HttpRequest;
import com.webull.openapi.utils.DateUtils;
import com.webull.openapi.utils.GUID;
import com.webull.openapi.utils.MD5Utils;
import com.webull.openapi.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultSignatureComposer {

    private static final String PARAMS_JOIN = "&";
    private static final String SECRET_TAILER = "&";
    private static final String PARAM_KV_JOIN = "=";

    private DefaultSignatureComposer() {
    }

    private static Map<String, String> refreshSignHeaders(String host,
                                                          String appKey,
                                                          Map<String, String> headers,
                                                          Signer signer) {
        Map<String, String> signHeaders = new HashMap<>();
        signHeaders.put(Headers.APP_KEY, appKey);
        signHeaders.put(Headers.TIMESTAMP, DateUtils.getTimestamp());
        signHeaders.put(Headers.SIGN_VERSION, signer.signerVersion());
        signHeaders.put(Headers.SIGN_ALGORITHM, signer.signerName());
        signHeaders.put(Headers.NONCE, GUID.get());
        headers.putAll(signHeaders);

        signHeaders.put(Headers.NATIVE_HOST.toLowerCase(), host);
        headers.put(Headers.NATIVE_HOST, host);
        return signHeaders;
    }

    private static String buildSignString(Map<String, String> signParams, String uri, String payload) {
        String signString = "";
        if (StringUtils.isNotEmpty(uri)) {
            signString = uri;
        }
        List<String> signPairs = signParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + PARAM_KV_JOIN + entry.getValue())
                .collect(Collectors.toList());
        if (StringUtils.isNotEmpty(signString)) {
            signString += (PARAMS_JOIN + String.join(PARAMS_JOIN, signPairs));
        } else {
            signString = String.join(PARAM_KV_JOIN, signPairs);
        }
        if (StringUtils.isNotEmpty(payload)) {
            signString = signString + PARAMS_JOIN + payload;
        }
        try {
            return URLEncoder.encode(signString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new ClientException(ErrorCode.NOT_SUPPORT, e);
        }
    }

    public static String getSign(String signString, String secret, Signer signer) {
        return signer.getSign(signString, secret + SECRET_TAILER);
    }

    public static String getSign(Map<String, String> signParams, String url, String payload, String secret, Signer signer) {
        String signString = buildSignString(signParams, url, payload);
        return getSign(signString, secret, signer);
    }


    public static String getSign(String host, String appKey, String appSecret, HttpRequest request) {
        Signer signer = SignerFactory.getInstance().get(request.getSignAlgorithm());
        Map<String, String> signParams = refreshSignHeaders(host, appKey, request.getHeaders(), signer);
        Map<String, Object> queryParams = request.getQuery();
        queryParams.forEach((key, value) -> {
            String result;
            if (signParams.containsKey(key)) {
                result = signParams.get(key) + PARAMS_JOIN + (value != null ? value : "");
            } else {
                result = String.valueOf(value);
            }
            signParams.put(key, result);
        });

        String bodyString = "";
        if (request.getBodyString() != null) {
            bodyString = MD5Utils.md5(request.getBodyString().getBytes(StandardCharsets.UTF_8)).toUpperCase();
        }
        String signString = buildSignString(signParams, request.getUri(), bodyString);
        return getSign(signString, appSecret, signer);
    }
}
