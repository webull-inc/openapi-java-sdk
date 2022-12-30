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
package com.webull.openapi.quotes.subsribe.lifecycle;

import com.webull.openapi.retry.RetryPolicy;
import io.reactivex.Single;

import java.io.Closeable;
import java.util.Optional;

public interface AuthProvider extends Closeable {

    String getAppKey();

    Optional<String> getToken();

    Single<String> refreshToken();

    Single<String> refreshToken(ClientStateMachine state, RetryPolicy retryPolicy);

    @Override
    default void close() {
        // cover IOException
    }
}
