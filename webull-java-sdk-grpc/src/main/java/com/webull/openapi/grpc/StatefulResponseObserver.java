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
package com.webull.openapi.grpc;

import com.webull.openapi.grpc.exception.UserCancelledException;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.grpc.retry.GrpcRetryContext;
import com.webull.openapi.retry.RetriedFailedException;
import com.webull.openapi.retry.Retryable;
import com.webull.openapi.utils.ExceptionUtils;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StatefulResponseObserver<ReqT, RespT> implements ClientResponseObserver<ReqT, RespT> {

    private ClientCallStreamObserver<ReqT> clientCallStreamObserver;
    private final CompletableFuture<Void> observingFuture = new CompletableFuture<>();

    private Retryable<?> retryable;
    private GrpcRetryContext retryContext;

    private final List<SubStreamObserver<RespT>> subObservers;

    public StatefulResponseObserver(List<SubStreamObserver<RespT>> subObservers) {
        this.subObservers = subObservers;
    }

    @Override
    public void beforeStart(ClientCallStreamObserver<ReqT> requestStream) {
        this.clientCallStreamObserver = requestStream;
        this.clientCallStreamObserver.setOnReadyHandler(() -> subObservers.forEach(SubStreamObserver::onReady));
    }

    @Override
    public void onNext(RespT value) {
        this.subObservers.forEach(sub -> sub.onNext(value));
    }

    @Override
    public void onError(Throwable cause) {
        Throwable finalCause = cause;
        this.subObservers.forEach(sub -> sub.onError(finalCause));

        int attempts = this.retryContext != null ? this.retryContext.getRetriesAttempted() + 1 : 1;
        this.retryContext = new GrpcRetryContext(attempts, cause);

        if (!this.observingFuture.isDone() && this.retryable != null) {
            try {
                this.retryable.retry(this.retryContext);
                return;
            } catch (RetriedFailedException ex) {
                cause = ex.getCause();
            }
        }

        // end of retry
        Throwable rootCause = ExceptionUtils.getRootCause(cause);
        if (rootCause instanceof UserCancelledException) {
            if (!this.observingFuture.isDone()) {
                this.observingFuture.cancel(false);
            }
        } else {
            this.observingFuture.completeExceptionally(cause);
        }
    }

    @Override
    public void onCompleted() {
        this.subObservers.forEach(SubStreamObserver::onCompleted);
        this.observingFuture.complete(null);
    }

    public void setRetryable(Retryable<?> retryable) {
        this.retryable = retryable;
    }

    /**
     * @return the observing future.
     */
    public CompletableFuture<Void> observingFuture() {
        return this.observingFuture;
    }

    /**
     * Cancel observer processing.
     */
    public void cancel() {
        if (this.clientCallStreamObserver != null) {
            this.clientCallStreamObserver.cancel(null, new UserCancelledException());
        }
        if (!this.observingFuture.isDone()) {
            this.observingFuture.cancel(false);
        }
    }
}
