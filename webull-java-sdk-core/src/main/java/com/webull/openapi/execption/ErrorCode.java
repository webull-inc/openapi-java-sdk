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
package com.webull.openapi.execption;

public class ErrorCode {

    private ErrorCode() {}

    public static final String INVALID_CREDENTIAL = "InvalidCredential";

    public static final String INVALID_PARAMETER = "InvalidParameter";

    public static final String INVALID_STATE = "InvalidState";

    public static final String NOT_SUPPORT = "NotSupport";

    public static final String INVALID_REQUEST = "InvalidRequest";

    public static final String TOO_MANY_REQUESTS = "TooManyRequests";

    public static final String ENDPOINT_RESOLVING_ERROR = "EndpointResolvingError";

    public static final String UNKNOWN_SERVER_ERROR = "UnknownServerError";

    public static final String TIMEOUT = "Timeout";
}
