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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChainedEndpointResolver implements EndpointResolver {

    private final List<EndpointResolver> endpointResolvers;

    public ChainedEndpointResolver(List<EndpointResolver> endpointResolvers) {
        this.endpointResolvers = endpointResolvers.stream()
                .sorted(Comparator.comparing(EndpointResolver::order))
                .collect(Collectors.toList());
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public Optional<String> resolve(final String region, final ApiModule apiModule) {
        for (EndpointResolver resolver : endpointResolvers) {
            Optional<String> endpointOpt = resolver.resolve(region, apiModule);
            if (endpointOpt.isPresent()) {
                return endpointOpt;
            }
        }
        return Optional.empty();
    }

    @Override
    public void addEndpoint(final String region, final ApiModule apiModule, final String endpoint) {
        this.endpointResolvers.forEach(resolver -> resolver.addEndpoint(region, apiModule, endpoint));
    }
}
