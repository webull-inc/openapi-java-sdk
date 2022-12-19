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
package com.webull.openapi.quotes.internal.mqtt.lifecycle;

import com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsConnectedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsSessionHandler;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.utils.Assert;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class QuotesSubsReconnectHandler implements QuotesSubsSessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuotesSubsReconnectHandler.class);

    private final String appKey;
    private final AuthProvider authProvider;
    private final RetryPolicy retryPolicy;

    public QuotesSubsReconnectHandler(String appKey, AuthProvider authProvider, RetryPolicy retryPolicy) {
        Assert.notBlank("appKey", appKey);
        Assert.notNull("authProvider", authProvider);
        Assert.notNull("retryPolicy", retryPolicy);
        this.appKey = appKey;
        this.authProvider = authProvider;
        this.retryPolicy = retryPolicy;
    }

    @Override
    public void onConnected(QuotesSubsConnectedContext context) {
        // do nothing
    }

    @Override
    public void onDisconnected(QuotesSubsFailedContext context) {
        if (!(context instanceof MqttDisconnectedContextAdapter)) {
            logger.error("Unsupported context class type: {}", context.getClass().getName());
            return;
        }
        MqttDisconnectedContextAdapter adapter = (MqttDisconnectedContextAdapter) context;
        final Mqtt3ClientReconnector reconnector = (Mqtt3ClientReconnector) adapter.getReconnector();

        boolean reconnect = this.retryPolicy.shouldRetry(context);
        if (reconnect) {
            // try retrieve token when reconnecting
            try {
                String token = this.authProvider.refreshToken().blockingGet();
                reconnector.connectWith().simpleAuth()
                        .username(appKey)
                        .password(token.getBytes(StandardCharsets.UTF_8))
                        .applySimpleAuth()
                        .applyConnect();
            } catch (Exception e) {
                logger.error("Retrieve token error when reconnecting, use old token.", e);
            }
            long delayNanos = this.retryPolicy.nextRetryDelay(context, TimeUnit.NANOSECONDS);
            reconnector.delay(delayNanos, TimeUnit.NANOSECONDS);
        } else {
            context.getState().connectFailed();
        }
        reconnector.reconnect(reconnect);
    }
}
