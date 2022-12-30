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
package com.webull.openapi.trade.events.internal.lifecycle.proxy;

import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.lifecycle.GrpcSessionHandler;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.grpc.lifecycle.proxy.HandlerProxyFactory;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.events.internal.proto.Events;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeInboundHandler;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeSysEventHandler;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SubscribeHandlerProxyFactory implements HandlerProxyFactory<Events.SubscribeResponse> {

    private static final Logger logger = LoggerFactory.getLogger(SubscribeHandlerProxyFactory.class);

    private SubscribeHandlerProxyFactory() {
    }

    private static class InstanceHolder {
        private static final SubscribeHandlerProxyFactory instance = new SubscribeHandlerProxyFactory();
    }

    public static SubscribeHandlerProxyFactory getInstance() {
        return SubscribeHandlerProxyFactory.InstanceHolder.instance;
    }

    @Override
    public List<SubStreamObserver<Events.SubscribeResponse>> create(GrpcHandler handler) {
        return create(Collections.singletonList(handler));
    }

    @Override
    public List<SubStreamObserver<Events.SubscribeResponse>> create(List<GrpcHandler> handlers) {
        List<SubStreamObserver<SubscribeResponse>> proxies = new LinkedList<>();
        handlers.forEach(handler -> this.addProxies(proxies, handler));
        ComposeSubscribeHandlerProxy composeProxy = new ComposeSubscribeHandlerProxy(proxies);
        return Collections.singletonList(composeProxy);
    }

    private void addProxies(List<SubStreamObserver<SubscribeResponse>> proxies, GrpcHandler handler) {
        final int size = proxies.size();
        if (handler instanceof GrpcSessionHandler) {
            proxies.add(new SubscribeSessionHandlerProxy((GrpcSessionHandler) handler));
        }
        if (handler instanceof SubscribeInboundHandler) {
            proxies.add(new SubscribeInboundHandlerProxy((SubscribeInboundHandler) handler));
        }
        if (handler instanceof SubscribeSysEventHandler) {
            proxies.add(new SubscribeSysEventHandlerProxy((SubscribeSysEventHandler) handler));
        }
        if (size == proxies.size()) {
            logger.warn("Incapable of creating proxy for handler type={}.", handler.getClass());
        }
    }
}
