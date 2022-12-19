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

public class ClientException extends RuntimeException {

    private final String code;
    private final String message;

    public ClientException(String code) {
        this.code = code;
        this.message = null;
    }

    public ClientException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
        this.message = message;
    }

    public ClientException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
