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
package com.webull.openapi.http.retry;

import com.webull.openapi.http.HttpResponse;
import com.webull.openapi.http.common.HttpStatus;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.retry.SynchronousRetryable;

import java.util.Optional;
import java.util.function.Supplier;

public class SynchronousHttpRetryable extends SynchronousRetryable<HttpResponse> {

    public SynchronousHttpRetryable(Supplier<HttpResponse> doRetry, RetryPolicy retryPolicy) {
        super(doRetry, retryPolicy);
    }

    @Override
    protected Optional<RetryContext> maybeContinue(RetryContext previousContext, HttpResponse response, Throwable cause) {
        if (!(previousContext instanceof HttpRetryContext)) {
            throw new IllegalArgumentException("Retry context[" + previousContext.getClass().getName() + "] is inappropriate to http retryable!");
        }
        HttpRetryContext prevHttpCtx = (HttpRetryContext) previousContext;
        if (cause != null) {
            HttpRetryContext newContext = new HttpRetryContext(
                    prevHttpCtx.getUri(),
                    prevHttpCtx.getMethod(),
                    HttpStatus.BAD_REQUEST,
                    prevHttpCtx.getRetriesAttempted() + 1,
                    cause);
            return Optional.of(newContext);
        } else if (response != null && !response.isSuccess()) {
            HttpRetryContext newContext = new HttpRetryContext(
                    prevHttpCtx.getUri(),
                    prevHttpCtx.getMethod(),
                    response.getStatusCode(),
                    prevHttpCtx.getRetriesAttempted() + 1,
                    response.getException());
            return Optional.of(newContext);
        }
        return Optional.empty();
    }
}
