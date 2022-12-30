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
package com.webull.openapi.trade.events.internal.lifecycle.proxy;

import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeInboundHandler;
import com.webull.openapi.trade.events.subscribe.message.EventType;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

public class SubscribeInboundHandlerProxy implements SubStreamObserver<SubscribeResponse> {

    private final SubscribeInboundHandler inboundHandler;

    public SubscribeInboundHandlerProxy(SubscribeInboundHandler inboundHandler) {
        this.inboundHandler = inboundHandler;
    }

    @Override
    public void onNext(SubscribeResponse response) {
        if (EventType.isTradeEvent(response.getEventType())) {
            inboundHandler.onMessage(response);
        }
    }
}
