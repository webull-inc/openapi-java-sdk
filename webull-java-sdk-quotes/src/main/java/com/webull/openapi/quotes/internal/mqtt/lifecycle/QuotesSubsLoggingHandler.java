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

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsConnectedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsInboundHandler;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsSessionHandler;

public final class QuotesSubsLoggingHandler implements QuotesSubsSessionHandler, QuotesSubsInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(QuotesSubsLoggingHandler.class);

    @Override
    public void onConnected(QuotesSubsConnectedContext context) {
        logger.debug("Quotes subscribe client connected, app key={}.", context.getAuthProvider().getAppKey());
    }

    @Override
    public void onDisconnected(QuotesSubsFailedContext context) {
        if (context.userDisconnect()) {
            logger.debug("Quotes subscribe client disconnected by user.");
        } else {
            Integer ack = context.getConnAck().orElse(null);
            logger.error("Quotes subscribe client disconnected by error, ack={}, attempts={}.",
                    ack, context.getRetriesAttempted(), context.getCause());
        }
    }

    @Override
    public Object onMessage(Object message) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received quotes subscribe message={}", message);
        }
        return message;
    }
}
