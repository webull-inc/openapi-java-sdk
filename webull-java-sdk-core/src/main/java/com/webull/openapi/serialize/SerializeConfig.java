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
package com.webull.openapi.serialize;

public class SerializeConfig {

    private static final SerializeConfig HTTP_DEFAULT = SerializeConfig.builder().underscoresToCamel(true).build();

    private final boolean underscoresToCamel;

    SerializeConfig(boolean underscoresToCamel) {
        this.underscoresToCamel = underscoresToCamel;
    }

    public static SerializeConfigBuilder builder() {
        return new SerializeConfigBuilder();
    }

    public boolean isUnderscoresToCamel() {
        return underscoresToCamel;
    }

    public static SerializeConfig httpDefault() {
        return HTTP_DEFAULT;
    }
}
