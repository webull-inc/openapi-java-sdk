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

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.grpc.exception.UserCancelledException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import io.grpc.stub.StreamObserver;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GrpcStreamChannelPool<ReqT, RespT> implements SingletonChannelPool<ReqT, RespT> {

    private static final Logger logger = LoggerFactory.getLogger(GrpcStreamChannelPool.class);

    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private final AtomicReference<CompletableFuture<GrpcChannel<ReqT, RespT>>> channelRef = new AtomicReference<>();
    private final StreamObserverFactory<ReqT, RespT> streamObserverFactory;

    private StreamObserver<ReqT> requestStreamObserver;

    public GrpcStreamChannelPool(StreamObserverFactory<ReqT, RespT> streamObserverFactory) {
        this.streamObserverFactory = streamObserverFactory;
    }

    @Override
    public Optional<GrpcChannel<ReqT, RespT>> acquireIfExist() {
        CompletableFuture<GrpcChannel<ReqT, RespT>> future = this.channelRef.get();
        if (future != null) {
            try {
                GrpcChannel<ReqT, RespT> channel = future.getNow(null);
                return Optional.ofNullable(channel);
            } catch (CancellationException | CompletionException e) {
                // ignore
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<GrpcChannel<ReqT, RespT>> tryAcquire() {
        Optional<GrpcChannel<ReqT, RespT>> opt = this.acquireIfExist();
        if (opt.isPresent()) {
            return opt;
        }
        try {
            this.tryConnect();
        } catch (UserCancelledException ignored) {
            // do nothing
        }
        return opt;
    }

    @Override
    public GrpcChannel<ReqT, RespT> acquire() {
        try {
            CompletableFuture<GrpcChannel<ReqT, RespT>> future = this.channelRef.get();
            if (future != null) {
                return future.get();
            }
            return this.tryConnect().get();
        } catch (ExecutionException e) {
            throw new ClientException("Connect error.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(ErrorCode.INVALID_STATE, "Connect interrupted.", e);
        } catch (CancellationException e) {
            throw new ClientException(ErrorCode.INVALID_STATE, "Connect cancelled.", e);
        }
    }

    @Override
    public GrpcChannel<ReqT, RespT> acquire(long timeout, TimeUnit timeUnit) {
        try {
            CompletableFuture<GrpcChannel<ReqT, RespT>> future = channelRef.get();
            if (future != null) {
                return future.get(timeout, timeUnit);
            }
            return this.tryConnect().get(timeout, timeUnit);
        } catch (ExecutionException e) {
            throw new ClientException("Connect error.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(ErrorCode.INVALID_STATE, "Connect interrupted.", e);
        } catch (TimeoutException e) {
            throw new ClientException(ErrorCode.TIMEOUT, "Connect timeout.", e);
        } catch (CancellationException e) {
            throw new ClientException(ErrorCode.INVALID_STATE, "Connect cancelled.", e);
        }
    }

    @Override
    public Optional<GrpcChannel<ReqT, RespT>> remove() {
        CompletableFuture<GrpcChannel<ReqT, RespT>> future = this.channelRef.get();
        if (future != null && this.channelRef.compareAndSet(future, null)) {
            try {
                GrpcChannel<ReqT, RespT> channel = future.getNow(null);
                if (channel != null && logger.isDebugEnabled()) {
                    logger.debug("Remove quotes grpc channel[{}] from pool.", channel.id());
                }
                return Optional.ofNullable(channel);
            } catch (CancellationException | CompletionException e) {
                // ignore
            }
        }
        return Optional.empty();
    }

    @Override
    public void onConnected(GrpcChannel<ReqT, RespT> channel) {
        CompletableFuture<GrpcChannel<ReqT, RespT>> future = this.channelRef.get();
        if (future != null) {
            channel.init(this.requestStreamObserver);
            future.complete(channel);
            if (logger.isDebugEnabled()) {
                logger.debug("Quotes grpc channel[{}] connected.", channel.id());
            }
        }
    }

    @Override
    public void close() {
        if (this.isClosed.compareAndSet(false, true)) {
            CompletableFuture<GrpcChannel<ReqT, RespT>> future = this.channelRef.get();
            if (future != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Closing quotes grpc channel pool...");
                }
                this.channelFutureOnClose(future);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Quotes grpc channel pool closed.");
            }
        }
    }

    private void channelFutureOnClose(CompletableFuture<GrpcChannel<ReqT, RespT>> future) {
        if (future.isDone()) {
            try {
                GrpcChannel<ReqT, RespT> channel = future.getNow(null);
                if (channel != null) {
                    channel.close();
                }
            } catch (Exception e) {
                // do nothing
            }
        } else {
            future.cancel(false);
        }
    }

    private CompletableFuture<GrpcChannel<ReqT, RespT>> tryConnect() {
        if (this.isClosed.get()) {
            throw new UserCancelledException("Channel pool closed.");
        }
        CompletableFuture<GrpcChannel<ReqT, RespT>> newFuture = new CompletableFuture<>();
        CompletableFuture<GrpcChannel<ReqT, RespT>> result = this.channelRef.accumulateAndGet(newFuture,
                (prev, update) -> prev == null ? update : prev);

        if (result == newFuture) {
            if (logger.isDebugEnabled()) {
                logger.debug("A new quotes grpc channel start to connecting...");
            }
            this.requestStreamObserver = this.streamObserverFactory.createRequestObserver();
        }
        return result;
    }
}
