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

import java.util.Optional;

/**
 * CustomerType
 */
public enum CustomerType {
    /**
     * Individual customer
     */
    INDIVIDUAL,

    /**
     * Institution customer
     */
    INSTITUTION,
    ;


    public static Optional<CustomerType> of(String name) {
        for (CustomerType region : CustomerType.values()) {
            if (region.name().equals(name)) {
                return Optional.of(region);
            }
        }
        return Optional.empty();
    }
}