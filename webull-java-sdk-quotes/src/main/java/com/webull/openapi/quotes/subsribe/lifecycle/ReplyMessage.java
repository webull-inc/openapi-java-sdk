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

import java.io.Closeable;
import java.util.concurrent.TimeUnit;

public interface ReplyMessage<RecvT> extends Closeable {

    boolean isDone();

    RecvT get();

    RecvT get(long timeout, TimeUnit timeUnit);

    boolean receive(RecvT receive);

    boolean completeExceptionally(Throwable ex);

    default void close() {
        // cover IOException
    }
}