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
package com.webull.openapi.http.retry;

import com.webull.openapi.http.common.HttpMethod;
import com.webull.openapi.retry.RetryContext;

public class HttpRetryContext implements RetryContext {

    private final String uri;
    private final HttpMethod method;
    private final int httpStatusCode;
    private final int retriesAttempted;
    private final Throwable cause;
    private boolean throttled;

    public HttpRetryContext(String uri,
                            HttpMethod method,
                            int httpStatusCode,
                            int retriesAttempted,
                            Throwable cause) {
        this.uri = uri;
        this.method = method;
        this.httpStatusCode = httpStatusCode;
        this.retriesAttempted = retriesAttempted;
        this.cause = cause;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public int getRetriesAttempted() {
        return retriesAttempted;
    }

    @Override
    public boolean throttled() {
        return throttled;
    }

    @Override
    public void setThrottled(boolean throttled) {
        this.throttled = throttled;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
