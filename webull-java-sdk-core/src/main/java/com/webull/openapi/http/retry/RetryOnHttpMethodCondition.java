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

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.common.HttpMethod;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.RetryCondition;

public class RetryOnHttpMethodCondition implements RetryCondition {

    @Override
    public boolean shouldRetry(RetryContext context) {
        if (!(context instanceof HttpRetryContext)) {
            throw new IllegalArgumentException("Retry context[" + context.getClass().getName() + "] is inappropriate to http retry condition!");
        }
        HttpRetryContext httpCtx = (HttpRetryContext) context;
        Throwable cause = httpCtx.getCause();
        if (cause instanceof ClientException || cause instanceof ServerException) {
            return HttpMethod.GET == httpCtx.getMethod();
        }
        return false;
    }
}
