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
package com.webull.openapi.quotes.subsribe;

import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsHandler;
import com.webull.openapi.quotes.subsribe.message.MarketData;
import com.webull.openapi.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.quotes.subsribe.retry.QuotesSubsRetryCondition;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.retry.backoff.BackoffStrategy;

import java.util.Set;
import java.util.function.Consumer;

public interface QuotesSubsClientBuilder {

    QuotesSubsClientBuilder appKey(String appKey);

    QuotesSubsClientBuilder appSecret(String appSecret);

    QuotesSubsClientBuilder host(String host);

    QuotesSubsClientBuilder port(int port);

    QuotesSubsClientBuilder regionId(String regionId);

    QuotesSubsClientBuilder connectTimeout(long timeoutMillis);

    QuotesSubsClientBuilder readTimeout(long timeoutMillis);

    QuotesSubsClientBuilder apiClient(QuotesApiClient quotesApiClient);

    default QuotesSubsClientBuilder reconnectBy(BackoffStrategy backoffStrategy) {
        return this.reconnectBy(new RetryPolicy(QuotesSubsRetryCondition.getInstance(), backoffStrategy));
    }

    QuotesSubsClientBuilder reconnectBy(RetryPolicy retryPolicy);

    QuotesSubsClientBuilder enableTls(boolean enableTls);

    QuotesSubsClientBuilder proxy(ProxyConfig proxyConfig);

    QuotesSubsClientBuilder addHandler(QuotesSubsHandler handler);

    QuotesSubsClientBuilder onMessage(Consumer<MarketData> consumer);

    QuotesSubsClientBuilder addSubscription(Set<String> symbols, String category, Set<String> subTypes);

    QuotesSubsClient build();
}
