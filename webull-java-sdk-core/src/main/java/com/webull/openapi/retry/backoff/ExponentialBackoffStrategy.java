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
package com.webull.openapi.retry.backoff;

import com.webull.openapi.retry.RetryContext;

import java.util.concurrent.TimeUnit;

public class ExponentialBackoffStrategy implements BackoffStrategy {

    private static final int MAX_RETRY_LIMIT = 30;

    private final long initialDelayNanos;
    private final long maxDelayNanos;

    public ExponentialBackoffStrategy() {
        this(1, 120, TimeUnit.SECONDS);
    }

    public ExponentialBackoffStrategy(long initialDelay, long maxDelay, TimeUnit timeUnit) {
        this.initialDelayNanos = timeUnit.toNanos(initialDelay);
        this.maxDelayNanos = timeUnit.toNanos(maxDelay);
    }

    @Override
    public long nextRetryDelay(RetryContext context, TimeUnit timeUnit) {
        int retries = Math.min(ExponentialBackoffStrategy.MAX_RETRY_LIMIT, context.getRetriesAttempted());
        long delay = Math.min(this.maxDelayNanos, this.initialDelayNanos << retries);
        if (delay < this.initialDelayNanos) {
            delay = this.maxDelayNanos;
        }
        return timeUnit.convert(delay, TimeUnit.NANOSECONDS);
    }
}
