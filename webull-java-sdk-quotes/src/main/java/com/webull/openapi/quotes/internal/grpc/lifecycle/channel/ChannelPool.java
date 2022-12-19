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
package com.webull.openapi.quotes.internal.grpc.lifecycle.channel;

import java.io.Closeable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface ChannelPool<ReqT, RespT> extends Closeable {

    /**
     * Acquire a channel if existed, do not try to connect.
     * @return a channel if existed
     */
    Optional<GrpcChannel<ReqT, RespT>> acquireIfExist();

    Optional<GrpcChannel<ReqT, RespT>> tryAcquire();

    GrpcChannel<ReqT, RespT> acquire();

    GrpcChannel<ReqT, RespT> acquire(long timeout, TimeUnit timeUnit);

    @Override
    default void close() {
        // cover IOException
    }
}
