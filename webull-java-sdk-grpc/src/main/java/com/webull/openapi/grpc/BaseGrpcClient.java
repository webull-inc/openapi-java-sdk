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
package com.webull.openapi.grpc;

import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.grpc.lifecycle.proxy.HandlerProxyFactory;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.CollectionUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.Closeable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class BaseGrpcClient<RespT> implements GrpcClient, Closeable {

    protected final String appKey;
    protected final String appSecret;
    protected final String host;
    protected final int port;
    protected final RetryPolicy retryPolicy;
    protected final boolean enableTls;
    protected final LinkedList<SubStreamObserver<RespT>> subObservers;

    protected ManagedChannel channel;

    protected BaseGrpcClient(String appKey,
                          String appSecret,
                          String host,
                          int port,
                          RetryPolicy retryPolicy,
                          boolean enableTls) {
        this(appKey, appSecret, host, port, retryPolicy, enableTls, null, null);
    }

    protected BaseGrpcClient(String appKey,
                          String appSecret,
                          String host,
                          int port,
                          RetryPolicy retryPolicy,
                          boolean enableTls,
                          List<GrpcHandler> handlers,
                          HandlerProxyFactory<RespT> handlerProxyFactory) {
        Assert.notBlank("appKey", appKey);
        Assert.notBlank("appSecret", appSecret);
        Assert.notBlank("host", host);
        Assert.inRange("port", port, 0, 65535);
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.host = host;
        this.port = port;

        this.retryPolicy = retryPolicy != null ? retryPolicy : RetryPolicy.never();
        this.enableTls = enableTls;
        if (CollectionUtils.isNotEmpty(handlers)) {
            this.subObservers = handlers.stream().flatMap(handler -> handlerProxyFactory.create(handler).stream())
                    .collect(Collectors.toCollection(LinkedList::new));
        } else {
            this.subObservers = new LinkedList<>();
        }
    }

    protected void buildChannel() {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder
                .forAddress(this.host, this.port);
        if (this.enableTls) {
            channelBuilder.useTransportSecurity();
        } else {
            channelBuilder.usePlaintext();
        }
        this.channel = channelBuilder.build();
    }

    @Override
    public void shutdown(long timeout, TimeUnit timeUnit) throws InterruptedException {
        if (this.channel != null && !this.channel.isShutdown()) {
            boolean success = this.channel.shutdown().awaitTermination(timeout, timeUnit);
            if (!success) {
                this.channel.shutdownNow();
            }
        }
    }

    @Override
    public void shutdownNow() {
        if (this.channel != null && !this.channel.isShutdown()) {
            this.channel.shutdownNow();
        }
    }

    @Override
    public void close() {
        try {
            this.shutdown(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
