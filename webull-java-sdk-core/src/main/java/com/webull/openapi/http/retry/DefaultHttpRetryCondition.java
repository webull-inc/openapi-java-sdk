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

import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.AndMergeRetryCondition;
import com.webull.openapi.retry.condition.MaxRetryTimesCondition;
import com.webull.openapi.retry.condition.OrMergeRetryCondition;
import com.webull.openapi.retry.condition.RetryCondition;

import java.util.ArrayList;
import java.util.List;

public class DefaultHttpRetryCondition implements RetryCondition {

    private final OrMergeRetryCondition orMergeRetryCondition;

    public DefaultHttpRetryCondition(int maxRetryTimes) {
        List<RetryCondition> orMergeRetryConditions = new ArrayList<>();
        orMergeRetryConditions.add(new MaxRetryTimesCondition(maxRetryTimes));
        orMergeRetryConditions.add(new RetryOnHttpMethodCondition());

        List<RetryCondition> andMergeRetryConditions = new ArrayList<>();
        andMergeRetryConditions.add(new RetryOnExceptionCondition());
        andMergeRetryConditions.add(new RetryOnHttpStatusCondition());

        orMergeRetryConditions.add(new AndMergeRetryCondition(andMergeRetryConditions));
        this.orMergeRetryCondition = new OrMergeRetryCondition(orMergeRetryConditions);
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        return orMergeRetryCondition.shouldRetry(context);
    }
}
