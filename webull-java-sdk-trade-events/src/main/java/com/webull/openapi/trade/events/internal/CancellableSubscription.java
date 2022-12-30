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
package com.webull.openapi.trade.events.internal;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.grpc.StatefulResponseObserver;
import com.webull.openapi.trade.events.subscribe.Subscription;

import java.util.concurrent.CompletableFuture;

public final class CancellableSubscription implements Subscription {

    private final StatefulResponseObserver<?, ?> observer;

    public CancellableSubscription(StatefulResponseObserver<?, ?> observer) {
        this.observer = observer;
    }

    @Override
    public void unsubscribe() {
        this.observer.cancel();
    }

    @Override
    public CompletableFuture<Void> completable() {
        return this.observer.observingFuture();
    }

    @Override
    public void blockingAwait() {
        try {
            this.observer.observingFuture().get();
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new ClientException("Waiting for result interrupted.", interrupted);
        } catch (Exception ex) {
            throw new ClientException("Error waiting for result.", ex);
        }

    }
}
