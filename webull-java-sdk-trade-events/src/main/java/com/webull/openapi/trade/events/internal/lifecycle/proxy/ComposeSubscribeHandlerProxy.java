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

import com.webull.openapi.grpc.lifecycle.ComposeSubStreamObserver;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.trade.events.internal.proto.Events;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

import java.util.List;

public class ComposeSubscribeHandlerProxy extends ComposeSubStreamObserver<Events.SubscribeResponse, SubscribeResponse> {

    public ComposeSubscribeHandlerProxy(List<SubStreamObserver<SubscribeResponse>> observers) {
        super(observers);
    }

    @Override
    protected SubscribeResponse decodeForSubs(Events.SubscribeResponse value) {
        return new SubscribeResponse(
                value.getEventTypeValue(),
                value.getSubscribeType(),
                value.getContentType(),
                value.getPayload(),
                value.getRequestId(),
                value.getTimestamp());
    }
}
