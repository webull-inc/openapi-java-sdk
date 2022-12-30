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
package com.webull.openapi.retry;

import com.webull.openapi.retry.backoff.BackoffStrategy;
import com.webull.openapi.retry.backoff.NoDelayStrategy;
import com.webull.openapi.retry.condition.NoRetryCondition;
import com.webull.openapi.retry.condition.RetryCondition;
import com.webull.openapi.utils.Assert;

import java.util.concurrent.TimeUnit;

public class RetryPolicy implements RetryCondition, BackoffStrategy {

    private final RetryCondition retryCondition;
    private final BackoffStrategy backoffStrategy;

    public RetryPolicy(RetryCondition retryCondition, BackoffStrategy backoffStrategy) {
        Assert.notNull("retryCondition", retryCondition);
        Assert.notNull("backoffStrategy", backoffStrategy);
        this.retryCondition = retryCondition;
        this.backoffStrategy = backoffStrategy;
    }

    @Override
    public long nextRetryDelay(RetryContext context, TimeUnit timeUnit) {
        return this.backoffStrategy.nextRetryDelay(context, timeUnit);
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        return this.retryCondition.shouldRetry(context);
    }

    public static RetryPolicy never() {
        return new RetryPolicy(NoRetryCondition.getInstance(), NoDelayStrategy.getInstance());
    }
}
