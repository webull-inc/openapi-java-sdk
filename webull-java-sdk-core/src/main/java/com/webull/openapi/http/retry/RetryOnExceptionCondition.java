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
package com.webull.openapi.http.retry;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.RetryCondition;
import com.webull.openapi.utils.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class RetryOnExceptionCondition implements RetryCondition {

    private static final Set<String> RETRYABLE_NORMAL_ERRORS = CollectionUtils.newHashSet(
            "SERVICE_NOT_AVAILABLE",
            "GATEWAY_TIMEOUT",
            "INTERNAL_ERROR");

    private static final Set<String> RETRYABLE_THROTTLING_ERRORS = Collections.singleton("TOO_MANY_REQUESTS");

    @Override
    public boolean shouldRetry(RetryContext context) {
        Throwable cause = context.getCause();
        if (cause instanceof ClientException && cause.getCause() instanceof IOException) {
            return true;
        }
        if (cause instanceof ServerException) {
            ServerException serverCause = (ServerException) cause;
            if (RETRYABLE_NORMAL_ERRORS.contains(serverCause.getErrorCode())) {
                return true;
            }
            if (RETRYABLE_THROTTLING_ERRORS.contains(serverCause.getErrorCode())) {
                context.setThrottled(true);
                return true;
            }
        }
        return false;
    }
}
