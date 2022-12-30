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
package com.webull.openapi.execption;

public class ServerException extends RuntimeException {

    protected final String errorCode;
    protected final String errorMsg;
    protected final String requestId;

    public ServerException(String errorCode, String errorMsg, String requestId) {
        this(errorCode, errorMsg, formatDetailMessage(errorCode, errorMsg, requestId), requestId, null);
    }

    public ServerException(String errorCode, String errorMsg, String requestId, Throwable cause) {
        this(errorCode, errorMsg, formatDetailMessage(errorCode, errorMsg, requestId), requestId, cause);
    }

    protected ServerException(String errorCode, String errorMsg, String detailMsg, String requestId) {
        this(errorCode, errorMsg, detailMsg, requestId, null);
    }

    protected ServerException(String errorCode, String errorMsg, String detailMsg, String requestId, Throwable cause) {
        super(detailMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.requestId = requestId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getRequestId() {
        return requestId;
    }

    private static String formatDetailMessage(String errorCode, String errorMsg, String requestId) {
        return String.format("errorCode=%s, errorMsg=%s, requestId=%s", errorCode, errorMsg, requestId);
    }
}
