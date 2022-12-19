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
package com.webull.openapi.retry.backoff;

import com.webull.openapi.retry.RetryContext;

import java.util.concurrent.TimeUnit;

public class DefaultMixedBackoffStrategy implements BackoffStrategy {

    private static final long DEFAULT_INITIAL_DELAY_NANOS = TimeUnit.MILLISECONDS.toNanos(100);
    private static final long DEFAULT_JITTER_INITIAL_DELAY_NANOS = TimeUnit.MILLISECONDS.toNanos(500);
    private static final long DEFAULT_MAX_DELAY_NANOS = TimeUnit.SECONDS.toNanos(20);

    private final ExponentialBackoffStrategy exponentialBackoffStrategy;
    private final ExponentialBackoffStrategy defaultThrottledBackoffStrategy;

    public DefaultMixedBackoffStrategy() {
        this.exponentialBackoffStrategy = new ExponentialBackoffStrategy(
                DEFAULT_INITIAL_DELAY_NANOS,
                DEFAULT_MAX_DELAY_NANOS,
                TimeUnit.NANOSECONDS);

        this.defaultThrottledBackoffStrategy = new JitterExponentialBackoffStrategy(
                DEFAULT_JITTER_INITIAL_DELAY_NANOS,
                DEFAULT_MAX_DELAY_NANOS,
                TimeUnit.NANOSECONDS);
    }

    @Override
    public long nextRetryDelay(RetryContext context, TimeUnit timeUnit) {
        if (context.throttled()) {
            return defaultThrottledBackoffStrategy.nextRetryDelay(context, timeUnit);
        }
        return exponentialBackoffStrategy.nextRetryDelay(context, timeUnit);
    }

}
