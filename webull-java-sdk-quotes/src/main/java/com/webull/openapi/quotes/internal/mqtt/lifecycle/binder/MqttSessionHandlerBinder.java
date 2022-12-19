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
package com.webull.openapi.quotes.internal.mqtt.lifecycle.binder;

import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.MqttConnectedContextAdapter;
import com.webull.openapi.quotes.internal.mqtt.lifecycle.MqttDisconnectedContextAdapter;
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsConnectedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsSessionHandler;

public final class MqttSessionHandlerBinder implements MqttClientHandlerBinder {

    private MqttSessionHandlerBinder() {
    }

    private static final MqttSessionHandlerBinder instance = new MqttSessionHandlerBinder();

    public static MqttSessionHandlerBinder getInstance() {
        return instance;
    }

    @Override
    public int order(QuotesSubsHandler handler) {
        return (handler instanceof QuotesSubsSessionHandler ? 0 : NOT_BIND_ORDER);
    }

    @Override
    public Mqtt3ClientBuilder bindOnSession(QuotesSubsHandler handler, ClientStateMachine state,
                                            AuthProvider authProvider, Mqtt3ClientBuilder builder) {
        QuotesSubsSessionHandler sessionHandler = (QuotesSubsSessionHandler) handler;
        return builder.addConnectedListener(context -> {
            QuotesSubsConnectedContext adapter = MqttConnectedContextAdapter.of(state, authProvider);
            sessionHandler.onConnected(adapter);
        }).addDisconnectedListener(context -> {
            QuotesSubsFailedContext adapter = MqttDisconnectedContextAdapter.of(state, context);
            sessionHandler.onDisconnected(adapter);
        });
    }
}
