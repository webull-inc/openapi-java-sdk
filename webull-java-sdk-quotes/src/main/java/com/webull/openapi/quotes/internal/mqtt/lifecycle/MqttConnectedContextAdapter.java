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

import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsConnectedContext;

public class MqttConnectedContextAdapter implements QuotesSubsConnectedContext {

    private final ClientStateMachine state;
    private final AuthProvider authProvider;

    private MqttConnectedContextAdapter(ClientStateMachine state,
                                        AuthProvider authProvider) {
        this.state = state;
        this.authProvider = authProvider;
    }

    public static MqttConnectedContextAdapter of(ClientStateMachine state,
                                                 AuthProvider authProvider) {
        return new MqttConnectedContextAdapter(state, authProvider);
    }

    @Override
    public ClientStateMachine getState() {
        return state;
    }

    @Override
    public AuthProvider getAuthProvider() {
        return authProvider;
    }
}
