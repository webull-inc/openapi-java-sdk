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

import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class MqttClientHandlerBinderRegistry {

    private MqttClientHandlerBinderRegistry() {
        this.registryDefault();
    }

    private static final MqttClientHandlerBinderRegistry instance = new MqttClientHandlerBinderRegistry();

    public static MqttClientHandlerBinderRegistry getInstance() {
        return instance;
    }

    private final Set<MqttClientHandlerBinder> registry = new LinkedHashSet<>();

    public void register(MqttClientHandlerBinder binder) {
        registry.add(binder);
    }

    public Optional<MqttClientHandlerBinder> get(QuotesSubsHandler handler) {
        return registry.stream().filter(binder -> binder.order(handler) >= 0)
                .max(Comparator.comparing(binder -> binder.order(handler)));
    }

    public void unregister(MqttClientHandlerBinder binder) {
        registry.remove(binder);
    }

    private void registryDefault() {
        this.register(MqttSessionHandlerBinder.getInstance());
        this.register(MqttInboundHandlerBinder.getInstance());
    }
}
