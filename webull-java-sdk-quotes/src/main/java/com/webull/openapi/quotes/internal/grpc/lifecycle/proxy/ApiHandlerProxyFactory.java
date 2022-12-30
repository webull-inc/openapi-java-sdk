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
package com.webull.openapi.quotes.internal.grpc.lifecycle.proxy;

import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.grpc.lifecycle.proxy.DefaultHandlerProxyFactory;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.lifecycle.ApiDowngradeHandler;
import com.webull.openapi.quotes.api.lifecycle.ApiInboundHandler;
import com.webull.openapi.quotes.internal.grpc.proto.Gateway;

import java.util.List;

public class ApiHandlerProxyFactory extends DefaultHandlerProxyFactory<Gateway.ClientResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ApiHandlerProxyFactory.class);

    private ApiHandlerProxyFactory() {
    }

    private static class InstanceHolder {
        private static final ApiHandlerProxyFactory instance = new ApiHandlerProxyFactory();
    }

    public static ApiHandlerProxyFactory getInstance() {
        return ApiHandlerProxyFactory.InstanceHolder.instance;
    }

    @Override
    public List<SubStreamObserver<Gateway.ClientResponse>> create(GrpcHandler handler) {
        List<SubStreamObserver<Gateway.ClientResponse>> result = super.create(handler);
        if (handler instanceof ApiInboundHandler) {
            result.add(new ApiInboundHandlerProxy((ApiInboundHandler) handler));
        }
        if (handler instanceof ApiDowngradeHandler) {
            result.add(new ApiDowngradeHandlerProxy((ApiDowngradeHandler) handler));
        }
        if (result.isEmpty()) {
            logger.warn("Incapable of creating proxy for handler type={}.", handler.getClass());
        }
        return result;
    }
}
