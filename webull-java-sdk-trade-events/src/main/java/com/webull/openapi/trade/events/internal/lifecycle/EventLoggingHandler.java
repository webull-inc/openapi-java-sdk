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
package com.webull.openapi.trade.events.internal.lifecycle;

import com.webull.openapi.grpc.lifecycle.GrpcFailedContext;
import com.webull.openapi.grpc.lifecycle.GrpcSessionHandler;
import com.webull.openapi.grpc.lifecycle.GrpcSuccessContext;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeInboundHandler;
import com.webull.openapi.trade.events.subscribe.lifecycle.SubscribeSysEventHandler;
import com.webull.openapi.trade.events.subscribe.message.SubscribeResponse;

public class EventLoggingHandler implements GrpcSessionHandler, SubscribeInboundHandler, SubscribeSysEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventLoggingHandler.class);

    @Override
    public void onSuccess(GrpcSuccessContext context) {
        if (logger.isDebugEnabled()) {
            logger.debug("Trade event session on ready, ack={}.", context.getAck().orElse(null));
        }
    }

    @Override
    public void onFailed(GrpcFailedContext context) {
        if (context.userCancelled()) {
            logger.debug("Trade event session ended by user cancel.");
        } else {
            logger.error("Trade event session on failed, code={}.", context.getStatusCode(), context.getCause());
        }
    }

    @Override
    public void onCompleted() {
        if (logger.isDebugEnabled()) {
            logger.debug("Trade event session on completed.");
        }
    }

    @Override
    public void onMessage(SubscribeResponse message) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received trade event msg={}", message);
        }
    }

    @Override
    public void onPing(SubscribeResponse response) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received ping, msg={}", response);
        }
    }

    @Override
    public void onAuthError(SubscribeResponse response) {
        logger.error("Received authentication error, msg={}", response);
    }

    @Override
    public void onNumOfConnectionExceed(SubscribeResponse response) {
        logger.error("Number of connection exceeded, msg={}", response);
    }

    @Override
    public void onSubscribeExpired(SubscribeResponse response) {
        logger.error("Trade event subscribe expired, msg={}", response);
    }
}
