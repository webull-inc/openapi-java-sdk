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
package com.webull.openapi.trade.events.internal;

import com.webull.openapi.common.ApiModule;
import com.webull.openapi.endpoint.EndpointResolver;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.retry.GrpcRetryCondition;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.retry.backoff.FixedDelayStrategy;
import com.webull.openapi.trade.events.internal.lifecycle.EventLoggingHandler;
import com.webull.openapi.trade.events.subscribe.EventClient;
import com.webull.openapi.trade.events.subscribe.EventClientBuilder;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeInboundHandler;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DefaultEventClientBuilder implements EventClientBuilder {

    private String appKey;
    private String appSecret;
    private String regionId;
    private String host;
    private int port = 443;

    private RetryPolicy retryPolicy = new RetryPolicy(GrpcRetryCondition.getInstance(), new FixedDelayStrategy(5, TimeUnit.SECONDS));
    private boolean enableTls = true;

    private final LinkedList<GrpcHandler> handlers = new LinkedList<>();
    private final LinkedList<SubscribeInboundHandler> onMessages = new LinkedList<>();

    @Override
    public EventClientBuilder appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    @Override
    public EventClientBuilder appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    @Override
    public EventClientBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public EventClientBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public EventClientBuilder regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    @Override
    public EventClientBuilder enableTls(boolean enableTls) {
        this.enableTls = enableTls;
        return this;
    }

    @Override
    public EventClientBuilder reconnectBy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    @Override
    public EventClientBuilder addHandler(GrpcHandler handler) {
        this.handlers.add(handler);
        return this;
    }

    @Override
    public EventClientBuilder onMessage(Consumer<SubscribeResponse> consumer) {
        this.onMessages.add(consumer::accept);
        return this;
    }

    @Override
    public EventClient build() {
        if (StringUtils.isBlank(this.host)) {
            Assert.notBlank("regionId", regionId);
            this.host = EndpointResolver.getDefault().resolve(regionId, ApiModule.EVENTS)
                    .orElseThrow(() -> new ClientException(ErrorCode.ENDPOINT_RESOLVING_ERROR, "Unknown region"));
        }
        List<GrpcHandler> allHandlers = new ArrayList<>();
        // add event logging handler
        allHandlers.add(new EventLoggingHandler());
        // add other handlers
        allHandlers.addAll(this.handlers);
        // add on message handlers
        allHandlers.addAll(this.onMessages);
        return new DefaultEventClient(
                this.appKey,
                this.appSecret,
                this.host,
                this.port,
                this.retryPolicy,
                this.enableTls,
                allHandlers);
    }
}
