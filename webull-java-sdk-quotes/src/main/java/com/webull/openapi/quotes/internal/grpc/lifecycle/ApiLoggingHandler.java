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
package com.webull.openapi.quotes.internal.grpc.lifecycle;

import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.internal.grpc.proto.Gateway;

public final class ApiLoggingHandler implements SubStreamObserver<Gateway.ClientResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingHandler.class);

    private ApiLoggingHandler() {
    }

    private static final ApiLoggingHandler instance = new ApiLoggingHandler();

    public static ApiLoggingHandler getInstance() {
        return instance;
    }

    @Override
    public void onReady() {
        if (logger.isDebugEnabled()) {
            logger.debug("Quotes grpc session on ready.");
        }
    }

    @Override
    public void onNext(Gateway.ClientResponse value) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received quotes grpc msg[{}]={}", value.getType().name(), value);
        }
    }

    @Override
    public void onError(Throwable cause) {
        logger.error("Quotes grpc session on error.", cause);
    }

    @Override
    public void onCompleted() {
        if (logger.isDebugEnabled()) {
            logger.debug("Quotes grpc session on completed.");
        }
    }
}
