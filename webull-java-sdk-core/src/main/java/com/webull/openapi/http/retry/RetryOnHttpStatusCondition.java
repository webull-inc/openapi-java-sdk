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

import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.RetryCondition;

import java.util.Arrays;

public class RetryOnHttpStatusCondition implements RetryCondition {

    private static final Logger logger = LoggerFactory.getLogger(RetryOnHttpStatusCondition.class);
    private static final Integer[] DEFAULT_RETRYABLE_HTTP_STATUS_LIST = new Integer[]{500, 502, 503, 504};

    private final Integer[] retryableHttpStatusList;

    public RetryOnHttpStatusCondition() {
        this(DEFAULT_RETRYABLE_HTTP_STATUS_LIST);
    }

    public RetryOnHttpStatusCondition(Integer[] retryableHttpStatusList) {
        if (retryableHttpStatusList == null || retryableHttpStatusList.length == 0) {
            this.retryableHttpStatusList = DEFAULT_RETRYABLE_HTTP_STATUS_LIST;
        } else {
            this.retryableHttpStatusList = retryableHttpStatusList;
        }
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        if (!(context instanceof HttpRetryContext)) {
            throw new IllegalArgumentException("Retry context[" + context.getClass().getName() + "] is inappropriate to http retry condition!");
        }
        HttpRetryContext httpCtx = (HttpRetryContext) context;
        int httpStatusCode = httpCtx.getHttpStatusCode();
        if (Arrays.stream(this.retryableHttpStatusList).anyMatch(r -> r == httpStatusCode)) {
            logger.debug("Retryable HTTP error occurred. HTTP status code: {}", httpStatusCode);
            return true;
        }
        return false;
    }
}
