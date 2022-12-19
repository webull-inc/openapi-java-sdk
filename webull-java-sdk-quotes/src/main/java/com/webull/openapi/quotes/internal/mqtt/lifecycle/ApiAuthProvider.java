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
package com.webull.openapi.quotes.internal.mqtt.lifecycle;

import com.webull.openapi.grpc.exception.UserCancelledException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.internal.mqtt.support.SchedulerConfig;
import com.webull.openapi.quotes.subsribe.lifecycle.AuthProvider;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.utils.Assert;
import io.reactivex.Single;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ApiAuthProvider implements AuthProvider {

    private static final Logger logger = LoggerFactory.getLogger(ApiAuthProvider.class);

    private final String appKey;
    private volatile String token;
    private final QuotesApiClient apiClient;

    private volatile boolean isClosed = false;

    public ApiAuthProvider(String appKey, QuotesApiClient apiClient) {
        Assert.notBlank("appKey", appKey);
        Assert.notNull("apiClient", apiClient);
        this.appKey = appKey;
        this.apiClient = apiClient;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public Optional<String> getToken() {
        return Optional.ofNullable(token);
    }

    @Override
    public Single<String> refreshToken() {
        return Single.fromCallable(() -> {
            this.token = apiClient.getToken();
            return this.token;
        }).subscribeOn(SchedulerConfig.api());
    }

    @Override
    public Single<String> refreshToken(ClientStateMachine state, RetryPolicy retryPolicy) {
        return Single.defer(() -> {
            try {
                this.token = this.getTokenLoop(retryPolicy, new QuotesApiFailedContext(state, 0, null));
                return Single.just(this.token);
            } catch (Exception e) {
                return Single.error(e);
            }
        }).subscribeOn(SchedulerConfig.api());
    }

    private String getTokenLoop(RetryPolicy retryPolicy, QuotesApiFailedContext context) {
        if (this.isClosed) {
            throw new UserCancelledException();
        }
        try {
            return apiClient.getToken();
        } catch (Exception ex) {
            if (!(ex instanceof UserCancelledException)) {
                logger.error("Get token error when subscribing quotes.", ex);
            }
            if (retryPolicy.shouldRetry(context)) {
                try {
                    Thread.sleep(retryPolicy.nextRetryDelay(context, TimeUnit.MILLISECONDS));
                } catch (InterruptedException ignore) {
                    // ignore
                    Thread.currentThread().interrupt();
                }
                return this.getTokenLoop(retryPolicy, new QuotesApiFailedContext(context.getState(), context.getRetriesAttempted() + 1, ex));
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void close() {
        this.isClosed = true;
        this.apiClient.close();
    }
}
