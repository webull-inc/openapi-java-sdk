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

import com.hivemq.client.internal.rx.RxFutureConverter;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.MqttProxyProtocol;
import com.hivemq.client.mqtt.exceptions.MqttClientStateException;
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.QuotesClientState;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.binder.MqttClientHandlerBinder;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.binder.MqttClientHandlerBinderRegistry;
import com.webull.openapi.quotes.internal.mqtt.message.MqttPublish;
import com.webull.openapi.quotes.subsribe.QuotesSubsClient;
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.quotes.subsribe.lifecycle.SubscriptionManager;
import com.webull.openapi.quotes.subsribe.message.ConnAck;
import com.webull.openapi.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.CollectionUtils;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class DefaultQuotesSubsClient implements QuotesSubsClient {

    private static final Logger logger = LoggerFactory.getLogger(DefaultQuotesSubsClient.class);

    private final ClientStateMachine stateMachine = new QuotesClientState();

    private final Mqtt3RxClient client;
    private final MqttClientHandlerBinderRegistry handlerBinderRegistry = MqttClientHandlerBinderRegistry.getInstance();

    private final RetryPolicy retryPolicy;
    private final AuthProvider authProvider;
    private final SubscriptionManager subscriptionManager;
    private final LinkedList<QuotesSubsHandler> handlers;


    protected DefaultQuotesSubsClient(String host,
                                      int port,
                                      boolean enableTls,
                                      RetryPolicy retryPolicy,
                                      AuthProvider authProvider,
                                      SubscriptionManager subscriptionManager,
                                      LinkedList<QuotesSubsHandler> handlers,
                                      long connectTimeoutMillis,
                                      long readTimeoutMillis,
                                      ProxyConfig proxyConfig) {
        Assert.notBlank("host", host);
        Assert.inRange("port", port, 0, 65535);
        Assert.notNull("authProvider", authProvider);
        Assert.notNull("subscriptionManager", subscriptionManager);

        this.retryPolicy = retryPolicy != null ? retryPolicy : RetryPolicy.never();
        this.authProvider = authProvider;
        this.subscriptionManager = subscriptionManager;
        this.handlers = handlers;

        Mqtt3ClientBuilder clientBuilder = Mqtt3Client.builder()
                .identifier(authProvider.getAppKey() + "_" + UUID.randomUUID());

        // transport config
        MqttClientTransportConfigBuilder transportBuilder = MqttClientTransportConfig.builder()
                .serverHost(host)
                .serverPort(port)
                .socketConnectTimeout(connectTimeoutMillis, TimeUnit.MILLISECONDS)
                .mqttConnectTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS);

        if (enableTls) {
            transportBuilder = transportBuilder.sslWithDefaultConfig();
        }

        if (proxyConfig != null) {
            MqttProxyProtocol protocol = null;
            switch (proxyConfig.getProtocol()) {
                case SOCKS4:
                    protocol = MqttProxyProtocol.SOCKS_4;
                    break;
                case SOCKS5:
                    protocol = MqttProxyProtocol.SOCKS_5;
                    break;
                case HTTP:
                    protocol = MqttProxyProtocol.HTTP;
                    break;
            }
            if (protocol != null) {
                transportBuilder.proxyConfig()
                        .protocol(protocol)
                        .host(proxyConfig.getHost())
                        .port(proxyConfig.getPort())
                        .username(proxyConfig.getUsername())
                        .password(proxyConfig.getPassword())
                        .applyProxyConfig();
            }
        }

        final Mqtt3ClientBuilder finalBuilder = clientBuilder.transportConfig(transportBuilder.build());

        if (CollectionUtils.isNotEmpty(this.handlers)) {
            this.handlers.forEach(handler -> handlerBinderRegistry.get(handler)
                    .ifPresent(binder -> binder.bindOnSession(handler, this.stateMachine, this.authProvider, finalBuilder)));
        }

        this.client = finalBuilder.buildRx();
        this.subscriptionManager.subscribeOnConnected(err -> {
            if (this.client.getState().isConnected()) {
                this.client.disconnect().blockingAwait();
            }
        });
    }

    @Override
    public Single<ConnAck> connectRx() {
        return Single.fromCallable(stateMachine::callConnect)
                .flatMap(success -> {
                    if (!success) {
                        return Single.error(new ClientException(ErrorCode.INVALID_STATE, "Quotes client is not disconnected"));
                    }
                    return authProvider.refreshToken(this.stateMachine, this.retryPolicy);
                }).flatMap(token -> this.client.connectWith()
                        .simpleAuth()
                        .username(this.authProvider.getAppKey())
                        .password(token.getBytes(StandardCharsets.UTF_8))
                        .applySimpleAuth()
                        .applyConnect()
                        .map(mqtt3ConnAck -> new ConnAck(mqtt3ConnAck.getReturnCode().getCode())))
                .doOnSuccess(ack -> stateMachine.connected())
                .doOnError(err -> stateMachine.connectFailed());
    }

    @Override
    public Completable disconnectRx() {
       return stateMachine.callDisconnect(() -> {
           if (this.client.getState().isConnectedOrReconnect()) {
               try {
                   this.client.disconnect().blockingAwait();
               } catch (MqttClientStateException e) {
                   logger.warn("Disconnect mqtt client that is not connected.");
               }
           }
       });
    }

    @Override
    public Flowable<Object> subscribeRx() {
        Flowable<Object> publishes = this.client.publishes(MqttGlobalPublishFilter.ALL)
                .map(mqtt3Publish -> new MqttPublish(mqtt3Publish.getTopic().toString(), mqtt3Publish.getPayload().orElse(null)));

        if (CollectionUtils.isEmpty(this.handlers)) {
            return publishes;
        }
        LinkedHashMap<QuotesSubsHandler, MqttClientHandlerBinder> handlerToBinders = new LinkedHashMap<>();
        for (QuotesSubsHandler handler : this.handlers) {
            handlerBinderRegistry.get(handler).ifPresent(binder -> handlerToBinders.put(handler, binder));
        }

        return this.bindBatch(handlerToBinders.entrySet().iterator(), publishes);
    }

    private Flowable<Object> bindBatch(Iterator<Map.Entry<QuotesSubsHandler, MqttClientHandlerBinder>> iterator, Flowable<Object> flowable) {
        if (!iterator.hasNext()) {
            return flowable;
        }
        Map.Entry<QuotesSubsHandler, MqttClientHandlerBinder> entry = iterator.next();
        return bindBatch(iterator, entry.getValue().bindOnPublishes(entry.getKey(), flowable));
    }

    @Override
    public Completable addSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes) {
        return Completable.fromRunnable(() -> {
            String token = this.authProvider.getToken()
                    .orElseThrow(() -> new IllegalStateException("Cannot add subscription before session initialized"));
            this.subscriptionManager.addSubscription(token, symbols, category, subTypes);
        });
    }

    @Override
    public Completable removeSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes) {
        return removeSubscriptionRx(symbols, category, subTypes, false);
    }

    @Override
    public Completable removeAllSubscriptionRx() {
        return removeSubscriptionRx(null, null, null, true);
    }

    private Completable removeSubscriptionRx(Set<String> symbols, String category, Set<String> subTypes, Boolean unsubscribeAll) {
        return Completable.fromRunnable(() -> {
            String token = this.authProvider.getToken()
                    .orElseThrow(() -> new IllegalStateException("Cannot add subscription before session initialized"));
            this.subscriptionManager.removeSubscription(token, symbols, category, subTypes, unsubscribeAll);
        });
    }

    @Override
    public CompletableFuture<ConnAck> connectAsync() {
        return RxFutureConverter.toFuture(this.connectRx());
    }

    @Override
    public CompletableFuture<Void> disconnectAsync() {
        return RxFutureConverter.toFuture(this.disconnectRx());
    }

    @Override
    public CompletableFuture<Void> subscribeAsync() {
        return RxFutureConverter.toFuture(this.subscribeRx().ignoreElements());
    }

    @Override
    public CompletableFuture<Void> addSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes) {
        return RxFutureConverter.toFuture(this.addSubscriptionRx(symbols, category, subTypes));
    }

    @Override
    public CompletableFuture<Void> removeSubscriptionAsync(Set<String> symbols, String category, Set<String> subTypes) {
        return RxFutureConverter.toFuture(this.removeSubscriptionRx(symbols, category, subTypes));
    }

    @Override
    public CompletableFuture<Void> removeAllSubscriptionAsync() {
        return RxFutureConverter.toFuture(this.removeAllSubscriptionRx());
    }

    @Override
    public ConnAck connectBlocking() {
        return this.connectRx().blockingGet();
    }

    @Override
    public void disconnectBlocking() {
        this.disconnectRx().blockingAwait();
    }

    @Override
    public void subscribeBlocking() {
        this.subscribeRx().ignoreElements().blockingAwait();
    }

    @Override
    public void addSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes) {
        this.addSubscriptionRx(symbols, category, subTypes).blockingAwait();
    }

    @Override
    public void removeSubscriptionBlocking(Set<String> symbols, String category, Set<String> subTypes) {
        this.removeSubscriptionRx(symbols, category, subTypes).blockingAwait();
    }

    @Override
    public void removeAllSubscriptionBlocking() {
        this.removeAllSubscriptionRx().blockingAwait();
    }

    @Override
    public void close() {
        // change state first
        this.stateMachine.close();
        // release other resources
        this.authProvider.close();
        this.subscriptionManager.close();
        if (!this.stateMachine.isDisconnected()) {
            this.disconnectRx().blockingAwait();
        }
    }
}
