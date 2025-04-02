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
package com.webull.openapi.quotes.api;

import com.webull.openapi.common.CustomerType;
import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.retry.GrpcRetryCondition;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.retry.backoff.BackoffStrategy;

public interface QuotesApiClientBuilder {

    QuotesApiClientBuilder appKey(String appKey);

    QuotesApiClientBuilder appSecret(String appSecret);

    QuotesApiClientBuilder host(String host);

    QuotesApiClientBuilder port(int port);

    QuotesApiClientBuilder customerType(CustomerType customerType);

    QuotesApiClientBuilder regionId(String regionId);

    QuotesApiClientBuilder connectTimeout(long timeoutMillis);

    QuotesApiClientBuilder readTimeout(long timeoutMillis);

    QuotesApiClientBuilder userId(String userId);

    QuotesApiClientBuilder enableTls(boolean enableTls);

    default QuotesApiClientBuilder reconnectBy(BackoffStrategy backoffStrategy) {
        return this.reconnectBy(new RetryPolicy(GrpcRetryCondition.getInstance(), backoffStrategy));
    }

    QuotesApiClientBuilder reconnectBy(RetryPolicy retryPolicy);

    QuotesApiClientBuilder addHandler(GrpcHandler handler);

    QuotesApiClient build();
}
