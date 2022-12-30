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
package com.webull.openapi.endpoint;

import com.webull.openapi.common.ApiModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface EndpointResolver {

    int order();

    Optional<String> resolve(final String region, final ApiModule apiModule);

    void addEndpoint(final String region, final ApiModule apiModule, final String endpoint);

    static EndpointResolver getDefault() {
        return DefaultHolder.defaultResolver;
    }

    class DefaultHolder {

        private DefaultHolder() {
        }

        private static final EndpointResolver defaultResolver;

        static {
            List<EndpointResolver> resolvers = new ArrayList<>(2);
            resolvers.add(PredefineEndpointResolver.getInstance());
            resolvers.add(SettableEndpointResolver.getInstance());
            defaultResolver = new ChainedEndpointResolver(resolvers);
        }
    }
}
