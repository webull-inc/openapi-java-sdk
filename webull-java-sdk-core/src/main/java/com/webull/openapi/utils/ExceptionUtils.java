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
package com.webull.openapi.utils;

import java.util.ArrayList;
import java.util.List;

public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static Throwable getRootCause(final Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        Throwable cause = throwable;
        while (cause != null && !list.contains(cause)) {
            list.add(cause);
            cause = cause.getCause();
        }
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }
}
