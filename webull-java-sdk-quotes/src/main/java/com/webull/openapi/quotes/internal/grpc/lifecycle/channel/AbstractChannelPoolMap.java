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
package com.webull.openapi.quotes.internal.grpc.lifecycle.channel;

import com.webull.openapi.utils.Assert;

import java.io.Closeable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractChannelPoolMap<E, P extends ChannelPool<?, ?>> implements ChannelPoolMap<E, P>, Closeable {

    private final ConcurrentMap<E, P> map = new ConcurrentHashMap<>();

    @Override
    public P get(E endpoint) {
        Assert.notNull("endpoint", endpoint);
        P pool = map.get(endpoint);
        if (pool == null) {
            pool = newPool(endpoint);
            P old = map.putIfAbsent(endpoint, pool);
            if (old != null) {
                // close the new pool
                pool.close();
                pool = old;
            }
        }
        return pool;
    }

    protected abstract P newPool(E endpoint);

    @Override
    public void close() {
        for (P pool : this.map.values()) {
            pool.close();
        }
        this.map.clear();
    }
}
