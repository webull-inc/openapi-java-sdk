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
package com.webull.openapi.trade.events.subscribe;

import com.webull.openapi.trade.events.internal.DefaultEventClientBuilder;
import com.webull.openapi.trade.events.subscribe.message.SubscribeRequest;

import java.io.Closeable;

public interface EventClient extends Closeable {

    static EventClientBuilder builder() {
        return new DefaultEventClientBuilder();
    }

    Subscription subscribe(SubscribeRequest request);

    @Override
    default void close() {
        // cover IOException
    }
}
