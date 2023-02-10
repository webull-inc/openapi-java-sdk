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
package com.webull.openapi.grpc.lifecycle;

import com.webull.openapi.utils.Assert;

import java.util.List;

public abstract class ComposeSubStreamObserver<I, O> implements SubStreamObserver<I> {

    private final List<SubStreamObserver<O>> subObservers;

    protected ComposeSubStreamObserver(List<SubStreamObserver<O>> observers) {
        Assert.notNull("observers", observers);
        this.subObservers = observers;
    }

    @Override
    public void onReady() {
        this.subObservers.forEach(SubStreamObserver::onReady);
    }

    @Override
    public void onNext(I value) {
        if (!this.subObservers.isEmpty()) {
            O decoded = decodeForSubs(value);
            this.subObservers.forEach(observer -> observer.onNext(decoded));
        }
    }

    @Override
    public void onError(Throwable cause) {
        this.subObservers.forEach(observer -> observer.onError(cause));
    }

    @Override
    public void onCompleted() {
        this.subObservers.forEach(SubStreamObserver::onCompleted);
    }

    protected abstract O decodeForSubs(I value);
}
