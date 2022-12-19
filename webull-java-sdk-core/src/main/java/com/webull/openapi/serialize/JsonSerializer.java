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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonSerializer {

    private JsonSerializer() {
    }

    private static final Gson GSON = new Gson();

    private static final Gson UNDERSCORES_TO_CAMEL = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public static String toJson(Object object, SerializeConfig serializeConfig) {
        return get(serializeConfig).toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT, SerializeConfig serializeConfig) {
        return get(serializeConfig).fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT, SerializeConfig serializeConfig) {
        return get(serializeConfig).fromJson(json, typeOfT);
    }

    private static Gson get(SerializeConfig serializeConfig) {
        return serializeConfig.isUnderscoresToCamel() ? UNDERSCORES_TO_CAMEL : GSON;
    }
}
