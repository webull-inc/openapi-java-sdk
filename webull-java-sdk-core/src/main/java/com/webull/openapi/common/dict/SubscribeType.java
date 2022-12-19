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
package com.webull.openapi.common.dict;

import java.util.Optional;

public enum SubscribeType {

    /**
     * Order Book
     */
    QUOTE(0),

    /**
     * Market Snapshot
     */
    SNAPSHOT(1),

    /**
     * Tick-by-Tick
     */
    TICK(2);

    private final int code;

    SubscribeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final SubscribeType[] items = new SubscribeType[SubscribeType.values().length];

    static {
        for (SubscribeType item : values()) {
            items[item.getCode()] = item;
        }
    }

    public static Optional<SubscribeType> fromCode(int code) {
        if (code < 0 || code > items.length - 1) {
            return Optional.empty();
        }
        return Optional.ofNullable(items[code]);
    }
}
