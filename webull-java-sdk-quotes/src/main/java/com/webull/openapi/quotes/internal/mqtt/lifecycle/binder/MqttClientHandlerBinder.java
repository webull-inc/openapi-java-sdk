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
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import io.reactivex.Flowable;

public interface MqttClientHandlerBinder {

    /**
     * A order value for not binding the handler.
     */
    int NOT_BIND_ORDER = -1;

    /**
     * Get the bind order of the binder.
     * Higher values are interpreted as higher priority.
     * @return the bind order, or return {@link #NOT_BIND_ORDER} for not binding the handler.
     */
    int order(QuotesSubsHandler handler);

    default Mqtt3ClientBuilder bindOnSession(QuotesSubsHandler handler, ClientStateMachine state,
                                             AuthProvider authProvider, Mqtt3ClientBuilder builder) {
        // do nothing
        return builder;
    }

    default Flowable<Object> bindOnPublishes(QuotesSubsHandler handler, Flowable<Object> publishes) {
        // do nothing
        return publishes;
    }
}
