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
package com.webull.openapi.quotes.internal.grpc.lifecycle;

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.lifecycle.ApiDowngradeHandler;
import com.webull.openapi.quotes.api.message.ApiResponse;

public class ApiDowngradeLoggingHandler implements ApiDowngradeHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiDowngradeLoggingHandler.class);

    @Override
    public void onDowngrade(ApiResponse response) {
        logger.warn("Your market authority has been taken by another device, " +
                "and no implementation of [ApiDowngradeHandler] is available, please restart later.");
    }
}
