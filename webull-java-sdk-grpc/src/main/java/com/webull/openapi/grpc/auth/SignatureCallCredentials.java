/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
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
package com.webull.openapi.grpc.auth;

import com.webull.openapi.auth.composer.DefaultSignatureComposer;
import com.webull.openapi.auth.signer.SignAlgorithm;
import com.webull.openapi.auth.signer.Signer;
import com.webull.openapi.auth.signer.SignerFactory;
import com.webull.openapi.common.Headers;
import com.webull.openapi.utils.DateUtils;
import com.webull.openapi.utils.GUID;
import com.webull.openapi.utils.MD5Utils;
import com.webull.openapi.utils.StringUtils;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class SignatureCallCredentials extends CallCredentials {

    private static final Metadata.Key<String> APP_KEY = Metadata.Key.of(Headers.APP_KEY, Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> SIGN_ALGORITHM = Metadata.Key.of(Headers.SIGN_ALGORITHM, Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> SIGN_VERSION = Metadata.Key.of(Headers.SIGN_VERSION, Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> NONCE = Metadata.Key.of(Headers.NONCE, Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> TIMESTAMP = Metadata.Key.of(Headers.TIMESTAMP, Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> SIGNATURE = Metadata.Key.of(Headers.SIGNATURE, Metadata.ASCII_STRING_MARSHALLER);

    private final String appKey;
    private final String appSecret;
    private final String host;
    private final Integer port;
    private final String url;
    private final Signer signer;
    private final byte[] requestBytes;

    public SignatureCallCredentials(String appKey, String appSecret, byte[] requestBytes) {
        this(appKey, appSecret, null, null, null, requestBytes);
    }

    public SignatureCallCredentials(String appKey, String appSecret, String host, Integer port, String url, byte[] requestBytes) {
        this(appKey, appSecret, host, port, url, requestBytes, SignerFactory.getInstance().get(SignAlgorithm.HMAC_SHA1));
    }

    public SignatureCallCredentials(String appKey, String appSecret, String host, Integer port, String url, byte[] requestBytes, Signer signer) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.host = host;
        this.port = port;
        this.url = url;
        this.requestBytes = requestBytes;
        this.signer = signer;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier applier) {
        executor.execute(() -> {
            try {
                String signAlgorithm = this.signer.signerName();
                String signVersion = this.signer.signerVersion();
                String guid = GUID.get();
                String timestamp = DateUtils.getTimestamp();

                Metadata headers = new Metadata();
                headers.put(APP_KEY, this.appKey);
                headers.put(SIGN_ALGORITHM, signAlgorithm);
                headers.put(SIGN_VERSION, signVersion);
                headers.put(NONCE, guid);
                headers.put(TIMESTAMP, timestamp);

                // sign param key should be lowercase.
                Map<String, String> signParams = new HashMap<>();
                if (StringUtils.isNotEmpty(this.host) && this.port != null) {
                    signParams.put(Headers.NATIVE_HOST.toLowerCase(), this.host + ":" + this.port);
                }
                signParams.put(Headers.APP_KEY, this.appKey);
                signParams.put(Headers.SIGN_ALGORITHM, signAlgorithm);
                signParams.put(Headers.SIGN_VERSION, signVersion);
                signParams.put(Headers.NONCE, guid);
                signParams.put(Headers.TIMESTAMP, timestamp);

                String payload = this.requestBytes != null ? MD5Utils.md5(this.requestBytes) : null;
                String sign = DefaultSignatureComposer.getSign(signParams, this.url, payload, this.appSecret, this.signer);
                headers.put(SIGNATURE, sign);
                applier.apply(headers);
            } catch (Exception e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // Should be a noop but never called.
    }
}
