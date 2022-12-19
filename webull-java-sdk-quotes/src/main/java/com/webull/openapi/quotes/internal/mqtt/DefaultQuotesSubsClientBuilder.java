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
package com.webull.openapi.quotes.internal.mqtt;

import com.webull.openapi.common.ApiModule;
import com.webull.openapi.endpoint.EndpointResolver;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.internal.mqtt.codec.PublishToMarketDataDecoder;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.ApiAuthProvider;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.ApiSubscriptionManager;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.QuotesSubsLoggingHandler;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.QuotesSubsReconnectHandler;
import com.webull.openapi.quotes.subsribe.QuotesSubsClient;
import com.webull.openapi.quotes.subsribe.QuotesSubsClientBuilder;
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsInboundHandler;
import com.webull.openapi.quotes.subsribe.message.MarketData;
import com.webull.openapi.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.quotes.subsribe.retry.QuotesSubsRetryCondition;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.retry.backoff.FixedDelayStrategy;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.StringUtils;

import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class DefaultQuotesSubsClientBuilder implements QuotesSubsClientBuilder {

    private String appKey;
    private String appSecret;
    private String regionId;
    private String host;
    private int port = 8883;
    private long connectTimeoutMillis = 5000;
    private long readTimeoutMillis = 60000;
    private QuotesApiClient quotesApiClient;
    private RetryPolicy retryPolicy =
            new RetryPolicy(QuotesSubsRetryCondition.getInstance(), new FixedDelayStrategy(10, TimeUnit.SECONDS));
    private boolean enableTls = true;
    private ProxyConfig proxyConfig;

    private final LinkedList<QuotesSubsHandler> handlers = new LinkedList<>();
    private final LinkedList<QuotesSubsInboundHandler> onMessages = new LinkedList<>();

    private Set<String> symbols;
    private String category;
    private Set<String> subTypes;

    @Override
    public QuotesSubsClientBuilder appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder appSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder connectTimeout(long timeoutMillis) {
        this.connectTimeoutMillis = timeoutMillis;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder readTimeout(long timeoutMillis) {
        this.readTimeoutMillis = timeoutMillis;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder apiClient(QuotesApiClient quotesApiClient) {
        this.quotesApiClient = quotesApiClient;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder reconnectBy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder enableTls(boolean enableTls) {
        this.enableTls = enableTls;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder proxy(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
        return this;
    }

    @Override
    public QuotesSubsClientBuilder addHandler(QuotesSubsHandler handler) {
        Assert.notNull("handler", handler);
        this.handlers.add(handler);
        return this;
    }

    @Override
    public QuotesSubsClientBuilder onMessage(Consumer<MarketData> consumer) {
        Assert.notNull("consumer", consumer);
        QuotesSubsInboundHandler handler = message -> {
            consumer.accept((MarketData) message);
            // pass on message by default
            return message;
        };
        this.onMessages.add(handler);
        return this;
    }

    @Override
    public QuotesSubsClientBuilder addSubscription(Set<String> symbols, String category, Set<String> subTypes) {
        this.symbols = symbols;
        this.category = category;
        this.subTypes = subTypes;
        return this;
    }

    @Override
    public QuotesSubsClient build() {
        if (StringUtils.isBlank(this.host)) {
            Assert.notBlank("regionId", regionId);
            this.host = EndpointResolver.getDefault().resolve(regionId, ApiModule.QUOTES)
                    .orElseThrow(() -> new ClientException(ErrorCode.ENDPOINT_RESOLVING_ERROR, "Unknown region"));
        }

        if (this.quotesApiClient == null) {
            this.quotesApiClient = QuotesApiClient.builder()
                    .appKey(this.appKey)
                    .appSecret(this.appSecret)
                    .host(this.host)
                    .build();
        }

        LinkedList<QuotesSubsHandler> allHandlers = new LinkedList<>();
        AuthProvider authProvider = new ApiAuthProvider(this.appKey, this.quotesApiClient);

        // add logging handler
        allHandlers.add(new QuotesSubsLoggingHandler());
        // add reconnector
        if (this.retryPolicy != null) {
            QuotesSubsReconnectHandler reconnectHandler = new QuotesSubsReconnectHandler(this.appKey, authProvider, this.retryPolicy);
            allHandlers.add(reconnectHandler);
        }

        // add subscription manager
        ApiSubscriptionManager subscriptionManager;
        if (CollectionUtils.isNotEmpty(this.symbols) || StringUtils.isNotBlank(this.category) || CollectionUtils.isNotEmpty(this.subTypes)) {
            subscriptionManager = new ApiSubscriptionManager(this.quotesApiClient, this.retryPolicy, this.symbols, this.category, this.subTypes);
        } else {
            subscriptionManager = new ApiSubscriptionManager(this.quotesApiClient, this.retryPolicy);
        }
        allHandlers.add(subscriptionManager);

        // add decoder
        allHandlers.add(new PublishToMarketDataDecoder());
        // add on message handlers
        allHandlers.addAll(this.onMessages);
        // add other handlers
        allHandlers.addAll(this.handlers);

        return new DefaultQuotesSubsClient(this.host, this.port, this.enableTls, this.retryPolicy, authProvider,
                subscriptionManager, allHandlers, this.connectTimeoutMillis, this.readTimeoutMillis, this.proxyConfig);
    }
}
