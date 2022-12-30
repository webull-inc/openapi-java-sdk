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

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public abstract class SynchronousRetryable<T> implements Retryable<T> {

    private static final Logger logger = LoggerFactory.getLogger(SynchronousRetryable.class);

    private final Supplier<T> doRetry;
    private final RetryPolicy retryPolicy;

    protected SynchronousRetryable(Supplier<T> doRetry, RetryPolicy retryPolicy) {
        this.doRetry = doRetry;
        this.retryPolicy = retryPolicy;
    }

    @Override
    public T retry(RetryContext context) throws RetriedFailedException {
        boolean shouldRetry = this.retryPolicy.shouldRetry(context);
        if (shouldRetry) {
            if (logger.isTraceEnabled()) {
                logger.error("Start retrying, attempts={}, caused by:", context.getRetriesAttempted(), context.getCause());
            }
            long delayMillis = this.retryPolicy.nextRetryDelay(context, TimeUnit.MILLISECONDS);
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new RetriedFailedException(interrupted);
            }

            RetryContext continueCtx;
            try {
                T result = doRetry.get();
                Optional<RetryContext> maybeContinue = this.maybeContinue(context, result, null);
                if (!maybeContinue.isPresent()) {
                    return result;
                } else {
                    continueCtx = maybeContinue.get();
                }
            } catch (Exception ex) {
                Optional<RetryContext> maybeContinue = this.maybeContinue(context, null, ex);
                if (!maybeContinue.isPresent()) {
                    throw new RetriedFailedException(ex);
                } else {
                    continueCtx = maybeContinue.get();
                }
            }

            return this.retry(continueCtx);
        }
        if (context.getCause() != null) {
            throw new RetriedFailedException(context.getCause());
        }
        return null;
    }

    /**
     * Return a new context for continue retrying.
     * @param previousContext previous retry context
     * @param result retried result
     * @param cause retried error cause
     * @return Optional.empty() if no need to continue.
     */
    protected abstract Optional<RetryContext> maybeContinue(RetryContext previousContext, T result, Throwable cause);
}
