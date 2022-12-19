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
package com.webull.openapi.endpoint;

import com.webull.openapi.common.ApiModule;
import com.webull.openapi.common.Region;

import java.util.Optional;

class PredefineEndpointResolver implements EndpointResolver {

    private PredefineEndpointResolver() {
    }

    private static class InstanceHolder {
        private static final PredefineEndpointResolver instance = new PredefineEndpointResolver();
    }

    public static PredefineEndpointResolver getInstance() {
        return PredefineEndpointResolver.InstanceHolder.instance;
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public Optional<String> resolve(final String region, final ApiModule apiModule) {
        return Region.of(region).map(apiModule::getHost);
    }

    @Override
    public void addEndpoint(final String region, final ApiModule apiModule, final String endpoint) {
        // do nothing
    }
}
