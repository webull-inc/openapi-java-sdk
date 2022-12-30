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
package com.webull.openapi.http;

import com.webull.openapi.auth.signer.SignAlgorithm;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.http.common.HttpMethod;
import com.webull.openapi.http.common.HttpProtocol;
import com.webull.openapi.serialize.JsonSerializer;
import com.webull.openapi.serialize.SerializeConfig;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String endpoint;
    private Integer port;
    private final String uri;
    private final String version;
    private final HttpMethod method;
    private final HttpProtocol protocol;
    private final SignAlgorithm signAlgorithm;
    private Map<String, String> headers;
    private Map<String, Object> query;
    private Object body;
    private String bodyString;
    private RuntimeOptions runtimeOptions;

    String setEndpointIfAbsent(final String endpoint) {
        Assert.notBlank("endpoint", endpoint);
        if (StringUtils.isBlank(this.endpoint)) {
            this.endpoint = endpoint;
        }
        return this.endpoint;
    }

    Integer setPortIfAbsent(final Integer port) {
        if (this.port == null) {
            this.port = port;
        }
        return this.port;
    }

    public HttpRequest(String requestUri,
                       String version,
                       HttpMethod httpMethod) {
        this(null, null, requestUri, version, httpMethod, HttpProtocol.HTTPS, SignAlgorithm.HMAC_SHA1);
    }

    public HttpRequest(String endpoint,
                       String requestUri,
                       String version,
                       HttpMethod httpMethod) {
        this(endpoint, null, requestUri, version, httpMethod, HttpProtocol.HTTPS, SignAlgorithm.HMAC_SHA1);
    }

    public HttpRequest(String endpoint,
                       Integer port,
                       String requestUri,
                       String version,
                       HttpMethod method,
                       HttpProtocol protocol,
                       SignAlgorithm signAlgorithm) {
        this.endpoint = endpoint;
        this.port = port;
        this.uri = requestUri;
        this.version = version;
        this.method = method;
        this.protocol = protocol;
        this.signAlgorithm = signAlgorithm;
        this.headers = new HashMap<>();
        this.query = new HashMap<>();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getPort() {
        return port;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpProtocol getProtocol() {
        return protocol;
    }

    public SignAlgorithm getSignAlgorithm() {
        return signAlgorithm;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public Object getBody() {
        return body;
    }

    public String getBodyString() {
        return bodyString;
    }

    public RuntimeOptions getRuntimeOptions() {
        return runtimeOptions;
    }

    public void setHeaders(Map<String, String> headers) {
        Assert.notNull("headers", headers);
        this.headers = headers;
    }

    public void setQuery(Map<String, Object> query) {
        Assert.notNull("query", query);
        this.query = query;
    }

    public void setBody(Object body) {
        this.body = body;
        this.bodyString = JsonSerializer.toJson(this.body, SerializeConfig.httpDefault());
    }

    public void setRuntimeOptions(RuntimeOptions runtimeOptions) {
        this.runtimeOptions = runtimeOptions;
    }

    private static final String COLON = ":";
    private static final String QUERY_START = "?";
    private static final String QUERY_AND = "&";
    private static final String QUERY_EQUALS = "=";

    String getURL() {
        StringBuilder urlBuilder = new StringBuilder();
        buildUri(urlBuilder);
        appendQuery(urlBuilder, this.query);
        return urlBuilder.toString();
    }

    private void buildUri(final StringBuilder urlBuilder) {
        HttpProtocol httpProtocol = this.protocol != null ? this.protocol : HttpProtocol.HTTPS;
        urlBuilder.append(httpProtocol.getPrefix());
        urlBuilder.append(this.endpoint);
        if (this.port != null) {
            urlBuilder.append(COLON).append(this.port);
        }
        if (this.uri != null) {
            urlBuilder.append(this.uri);
        }
    }

    private void appendQuery(final StringBuilder urlBuilder, final Map<String, Object> queries) {
        if (queries != null && queries.size() > 0) {
            if (urlBuilder.indexOf(QUERY_START) >= 1) {
                urlBuilder.append(QUERY_AND);
            } else {
                urlBuilder.append(QUERY_START);
            }
            try {
                for (Map.Entry<String, Object> entry : queries.entrySet()) {
                    String key = entry.getKey();
                    if (entry.getValue() == null) {
                        continue;
                    }
                    String val = String.valueOf(entry.getValue());
                    if (StringUtils.isNotEmpty(val)) {
                        urlBuilder.append(URLEncoder.encode(key, StandardCharsets.UTF_8.name()));
                        urlBuilder.append(QUERY_EQUALS);
                        urlBuilder.append(URLEncoder.encode(val, StandardCharsets.UTF_8.name()));
                        urlBuilder.append(QUERY_AND);
                    }
                }
            } catch (Exception e) {
                throw new ClientException("Parse URL error", e);
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "endpoint='" + endpoint + '\'' +
                ", port=" + port +
                ", uri='" + uri + '\'' +
                ", version='" + version + '\'' +
                ", method=" + method +
                ", protocol=" + protocol +
                ", signAlgorithm=" + signAlgorithm +
                ", headers=" + headers +
                ", query=" + query +
                ", body=" + bodyString +
                ", runtimeOptions=" + runtimeOptions +
                '}';
    }
}
