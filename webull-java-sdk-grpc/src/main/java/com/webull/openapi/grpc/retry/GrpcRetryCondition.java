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
package com.webull.openapi.grpc.retry;

import com.webull.openapi.grpc.lifecycle.GrpcFailedContext;
import com.webull.openapi.retry.RetryContext;
import com.webull.openapi.retry.condition.RetryCondition;
import io.grpc.Status;

public class GrpcRetryCondition implements RetryCondition {

    private GrpcRetryCondition() {
    }

    private static class InstanceHolder {
        private static final GrpcRetryCondition instance = new GrpcRetryCondition();
    }

    public static GrpcRetryCondition getInstance() {
        return GrpcRetryCondition.InstanceHolder.instance;
    }

    @Override
    public boolean shouldRetry(RetryContext context) {
        if (!(context instanceof GrpcFailedContext)) {
            throw new IllegalArgumentException("Retry context[" + context.getClass().getName() +
                    "] is inappropriate to grpc retry condition!");
        }
        GrpcFailedContext grpcFailedContext = (GrpcFailedContext) context;
        if (grpcFailedContext.userCancelled()) {
            return false;
        }
        int statusCode = grpcFailedContext.getStatusCode();
        return Status.UNKNOWN.getCode().value() == statusCode
                || Status.INTERNAL.getCode().value() == statusCode
                || Status.UNAVAILABLE.getCode().value() == statusCode;
    }
}
