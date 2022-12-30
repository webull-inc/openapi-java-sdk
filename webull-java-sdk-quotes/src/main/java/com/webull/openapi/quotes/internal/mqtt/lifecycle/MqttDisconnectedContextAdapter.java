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
package com.webull.openapi.quotes.internal.mqtt.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3ConnAckException;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;

import java.util.Optional;

public class MqttDisconnectedContextAdapter implements QuotesSubsFailedContext {

    private final ClientStateMachine state;
    private final MqttClientDisconnectedContext mqttClientDisconnectedContext;
    private boolean throttled;

    private MqttDisconnectedContextAdapter(ClientStateMachine state,
                                           MqttClientDisconnectedContext mqttClientDisconnectedContext) {
        this.state = state;
        this.mqttClientDisconnectedContext = mqttClientDisconnectedContext;
    }

    public static MqttDisconnectedContextAdapter of(ClientStateMachine state,
                                                    MqttClientDisconnectedContext mqttClientDisconnectedContext) {
        return new MqttDisconnectedContextAdapter(state, mqttClientDisconnectedContext);
    }

    @Override
    public Optional<Integer> getConnAck() {
        Throwable cause = mqttClientDisconnectedContext.getCause();
        if (cause instanceof Mqtt3ConnAckException) {
            Mqtt3ConnAckException connAckEx = (Mqtt3ConnAckException) cause;
            Mqtt3ConnAck connAck = connAckEx.getMqttMessage();
            return Optional.of(connAck.getReturnCode().getCode());
        }
        return Optional.empty();
    }

    @Override
    public boolean userDisconnect() {
        return MqttDisconnectSource.USER == mqttClientDisconnectedContext.getSource();
    }

    @Override
    public boolean clientDisconnect() {
        return MqttDisconnectSource.CLIENT == mqttClientDisconnectedContext.getSource();
    }

    @Override
    public boolean serverDisconnect() {
        return MqttDisconnectSource.SERVER == mqttClientDisconnectedContext.getSource();
    }

    @Override
    public ClientStateMachine getState() {
        return state;
    }

    @Override
    public int getRetriesAttempted() {
        return this.getReconnector().getAttempts();
    }

    @Override
    public Throwable getCause() {
        return mqttClientDisconnectedContext.getCause();
    }

    @Override
    public boolean throttled() {
        return throttled;
    }

    @Override
    public void setThrottled(boolean throttled) {
        this.throttled = throttled;
    }

    MqttClientReconnector getReconnector() {
        return mqttClientDisconnectedContext.getReconnector();
    }

    MqttClientReconnector get() {
        return mqttClientDisconnectedContext.getReconnector();
    }
}
