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
package com.webull.openapi.http.exception;

import com.webull.openapi.execption.ServerException;

public class HttpServerException extends ServerException {

    private final int httpStatus;

    public HttpServerException(String errorCode, String errorMsg, int httpStatus, String requestId) {
        super(errorCode, errorMsg, formatDetailMessage(httpStatus, errorCode, errorMsg, requestId), requestId, null);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    private static String formatDetailMessage(int httpStatus, String errorCode, String errorMsg, String requestId) {
        return String.format("httpStatus=%d, errorCode=%s, errorMsg=%s, requestId=%s",
                httpStatus, errorCode, errorMsg, requestId);
    }
}
