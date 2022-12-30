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
package com.webull.openapi.utils;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;

import java.util.Collection;
import java.util.List;

public final class Assert {

    private Assert() {
    }

    public static void notNull(String name, Object source) {
        if (source == null) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " is null");
        }
    }

    public static void notNull(final List<String> names, final Object... sources) {
        if (sources == null || sources.length == 0) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, "sources is empty");
        } else {
            if (CollectionUtils.isEmpty(names) || names.size() != sources.length) {
                throw new ClientException(ErrorCode.INVALID_PARAMETER, "names is empty");
            }
            for (int i = 0; i < sources.length; ++i) {
                Object val = sources[i];
                if (val == null) {
                    throw new ClientException(ErrorCode.INVALID_PARAMETER, names.get(i) + " is null");
                }
            }
        }
    }

    public static void notBlank(final List<String> names, final String... sources) {
        if (sources == null || sources.length == 0) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, "sources is empty");
        } else {
            if (CollectionUtils.isEmpty(names) || names.size() != sources.length) {
                throw new ClientException(ErrorCode.INVALID_PARAMETER, "names is empty");
            }
            for (int i = 0; i < sources.length; ++i) {
                String val = sources[i];
                if (StringUtils.isBlank(val)) {
                    throw new ClientException(ErrorCode.INVALID_PARAMETER, names.get(i) + " is blank");
                }
            }
        }
    }

    public static void notBlank(final String name, final String source) {
        if (StringUtils.isBlank(source)) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " is blank");
        }
    }

    public static void notEmpty(final String name, final Collection<?> source) {
        if (CollectionUtils.isEmpty(source)) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " is empty");
        }
    }

    public static void nonnegative(final String name, final int source) {
        if (source < 0) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " is negative");
        }
    }

    public static void nonnegative(final String name, final long source) {
        if (source < 0) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " is negative");
        }
    }

    public static void inRange(String name, int val, int min, int max) {
        if (min > max){
            throw new ClientException(ErrorCode.INVALID_PARAMETER,  "min greater than max");
        }
        if (val < min || val > max) {
            throw new ClientException(ErrorCode.INVALID_PARAMETER, name + " not in range");
        }
    }
}
