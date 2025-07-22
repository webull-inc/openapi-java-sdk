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
package com.webull.openapi.common;

public final class Headers {

    private Headers() {
    }

    public static final String REQUEST_ID = "x-request-id";
    public static final String APP_KEY = "x-app-key";
    public static final String SIGNATURE = "x-signature";
    public static final String SIGN_ALGORITHM = "x-signature-algorithm";
    public static final String SIGN_VERSION = "x-signature-version";
    public static final String NONCE = "x-signature-nonce";
    public static final String TIMESTAMP = "x-timestamp";
    public static final String VERSION = "x-version";

    public static final String USER_ID_KEY = "wb-user-id";
    public static final String CATEGORY_KEY = "category";


    public static final String NATIVE_HOST = "Host";
    public static final String NATIVE_CONTENT_TYPE = "Content-Type";
}
