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

import java.util.EnumMap;

public enum ApiModule {
    /**
     * api
     */
    API(DefaultHost.API_US, DefaultHost.API_HK, DefaultHost.API_JP),
    /**
     * quotes api
     */
    QUOTES(DefaultHost.QUOTES_US, DefaultHost.QUOTES_HK, DefaultHost.QUOTES_JP),
    /**
     * events api
     */
    EVENTS(DefaultHost.EVENTS_US, DefaultHost.EVENTS_HK, DefaultHost.EVENTS_JP),
    ;

    private final EnumMap<Region, String> hosts;

    /**
     * @param regionHosts the host of each region ordered by the region position in {@link Region} enum declaration.
     */
    ApiModule(String... regionHosts) {
        this.hosts = new EnumMap<>(Region.class);
        for (Region region : Region.values()) {
            this.hosts.put(region, regionHosts[region.ordinal()]);
        }
    }

    public String getHost(Region region) {
        return hosts.get(region);
    }

    public static ApiModule of(String name) {
        for (ApiModule apiModule : ApiModule.values()) {
            if (apiModule.name().equals(name)) {
                return apiModule;
            }
        }
        return null;
    }
}
