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
package com.webull.openapi.retry.condition;

import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.utils.CollectionUtils;

import java.util.List;

/**
 * Merge no retry with and operation.
 */
public class AndMergeRetryCondition implements RetryCondition {

    private final List<RetryCondition> conditions;

    public AndMergeRetryCondition(List<RetryCondition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            throw new IllegalArgumentException("Retry conditions can not be empty!");
        }
        this.conditions = conditions;
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        for (RetryCondition condition : conditions) {
            // merge no retry with and operation.
            if (condition.shouldRetry(context)) {
                return true;
            }
        }
        return false;
    }
}
