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
package com.webull.openapi.http.okhttp;

import com.webull.openapi.http.HttpRequest;
import okhttp3.Request;

import java.net.URL;
import java.util.Map;

public class OkHttpRequestBuilder {

    private final HttpRequest request;
    private final Request.Builder builder;

    public static OkHttpRequestBuilder newRequest(HttpRequest request) {
        return new OkHttpRequestBuilder(request);
    }

    private OkHttpRequestBuilder(HttpRequest request) {
        this.request = request;
        this.builder = new Request.Builder();
    }

    public OkHttpRequestBuilder url(URL url) {
        this.builder.url(url);
        return this;
    }

    public OkHttpRequestBuilder header(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.builder.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Request build() {
        OkHttpRequestBody requestBody;
        switch (request.getMethod()) {
            case DELETE:
                requestBody = new OkHttpRequestBody(request);
                this.builder.delete(requestBody);
                break;
            case POST:
                requestBody = new OkHttpRequestBody(request);
                this.builder.post(requestBody);
                break;
            case PUT:
                requestBody = new OkHttpRequestBody(request);
                this.builder.put(requestBody);
                break;
            case PATCH:
                requestBody = new OkHttpRequestBody(request);
                this.builder.patch(requestBody);
                break;
            case HEAD:
                this.builder.head();
                break;
            default:
                this.builder.get();
                break;
        }
        return this.builder.build();
    }
}
