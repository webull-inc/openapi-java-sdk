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
package com.webull.openapi.quotes.internal.mqtt.lifecycle.binder;

import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsInboundHandler;
import io.reactivex.Flowable;

public final class MqttInboundHandlerBinder implements MqttClientHandlerBinder {

    private MqttInboundHandlerBinder() {
    }

    private static final MqttInboundHandlerBinder instance = new MqttInboundHandlerBinder();

    public static MqttInboundHandlerBinder getInstance() {
        return instance;
    }

    @Override
    public int order(QuotesSubsHandler handler) {
        return (handler instanceof QuotesSubsInboundHandler ? 0 : NOT_BIND_ORDER);
    }

    @Override
    public Flowable<Object> bindOnPublishes(QuotesSubsHandler handler, Flowable<Object> publishes) {
        QuotesSubsInboundHandler inboundHandler = (QuotesSubsInboundHandler) handler;
        return publishes.flatMap(publish -> {
            Object result = inboundHandler.onMessage(publish);
            return result != null ? Flowable.just(result) : Flowable.empty();
        });
    }
}
