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
package com.webull.openapi.quotes.subsribe.message;

public final class ConnAck {

    public static final int ACCEPTED = 0;
    public static final int UNACCEPTABLE_PROTOCOL_VERSION = 1;
    public static final int IDENTIFIER_REJECTED = 2;
    public static final int SERVER_UNAVAILABLE = 3;
    public static final int BAD_USER_NAME_OR_PASSWORD = 4;
    public static final int NOT_AUTHORIZED = 5;

    private final int code;

    public ConnAck(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return ACCEPTED == code;
    }
}
