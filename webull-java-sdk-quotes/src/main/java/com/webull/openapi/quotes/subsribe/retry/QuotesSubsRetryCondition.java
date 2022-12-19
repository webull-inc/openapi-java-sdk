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
package com.webull.openapi.quotes.subsribe.retry;

import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;
import com.webull.openapi.quotes.subsribe.message.ConnAck;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.RetryCondition;
import com.webull.openapi.utils.ExceptionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QuotesSubsRetryCondition implements RetryCondition {

    private QuotesSubsRetryCondition() {
    }

    private static class InstanceHolder {
        private static final QuotesSubsRetryCondition instance = new QuotesSubsRetryCondition();
    }

    public static QuotesSubsRetryCondition getInstance() {
        return QuotesSubsRetryCondition.InstanceHolder.instance;
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        if (!(context instanceof QuotesSubsFailedContext)) {
            throw new IllegalArgumentException("Retry context[" + context.getClass().getName() + "] is inappropriate to mqtt retry condition!");
        }
        QuotesSubsFailedContext quotesCtx = (QuotesSubsFailedContext) context;

        if (quotesCtx.userDisconnect()) {
            return false;
        }

        if (quotesCtx.getConnAck().isPresent()) {
            int connAck = quotesCtx.getConnAck().get();
            if (connAck != ConnAck.SERVER_UNAVAILABLE && connAck != ConnAck.NOT_AUTHORIZED) {
                return false;
            }
        }

        Throwable rootCause = ExceptionUtils.getRootCause(quotesCtx.getCause());
        return !quotesCtx.clientDisconnect() || rootCause instanceof IOException || rootCause instanceof TimeoutException;
    }
}
