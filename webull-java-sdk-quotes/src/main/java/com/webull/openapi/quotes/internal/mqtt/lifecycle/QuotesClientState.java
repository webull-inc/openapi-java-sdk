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

import com.hivemq.client.internal.rx.RxFutureConverter;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import io.reactivex.Completable;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class QuotesClientState implements ClientStateMachine {

    private static final Logger logger = LoggerFactory.getLogger(QuotesClientState.class);

    private static final CompletableFuture<Boolean> DISCONNECTED = CompletableFuture.completedFuture(true);

    private final AtomicReference<CompletableFuture<Boolean>> state = new AtomicReference<>(DISCONNECTED);
    private final AtomicBoolean userCalledDisconnect = new AtomicBoolean(false);
    private volatile boolean isClosed = false;

    @Override
    public boolean callConnect() {
        if (this.isClosed) {
            throw new ClientException(ErrorCode.INVALID_STATE, "Quotes client closed.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("User call connect.");
        }
        if (this.state.compareAndSet(DISCONNECTED, new CompletableFuture<>())) {
            this.userCalledDisconnect.set(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean connected() {
        CompletableFuture<Boolean> connectFuture = this.state.get();
        if (connectFuture != DISCONNECTED) {
            if (logger.isDebugEnabled()) {
                logger.debug("Quotes client connected.");
            }
            connectFuture.complete(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean connectFailed() {
        CompletableFuture<Boolean> connectFuture = this.state.get();
        if (connectFuture != DISCONNECTED) {
            if (logger.isDebugEnabled()) {
                logger.debug("Quotes client connect failed.");
            }
            connectFuture.complete(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Completable callDisconnect(@Nullable Runnable doOnDisconnect) {
        if (logger.isDebugEnabled()) {
            logger.debug("User call disconnect.");
        }
        if (!this.userCalledDisconnect.compareAndSet(false, true)) {
            return Completable.complete();
        }
        CompletableFuture<Boolean> connectFuture = this.state.get();
        if (connectFuture != DISCONNECTED) {
            connectFuture.thenAccept(ignore -> {
                if (doOnDisconnect != null) {
                    try {
                        doOnDisconnect.run();
                    } catch (Exception e) {
                        logger.error("Do on disconnect error", e);
                    }
                }
                this.state.set(DISCONNECTED);
                if (logger.isDebugEnabled()) {
                    logger.debug("Quotes client disconnected.");
                }
            });
            return RxFutureConverter.toCompletable(connectFuture);
        } else {
            return Completable.complete();
        }
    }

    @Override
    public boolean userCalledDisconnect() {
        return this.isClosed || this.userCalledDisconnect.get();
    }

    @Override
    public boolean isDisconnected() {
        return DISCONNECTED == this.state.get();
    }

    @Override
    public void close() {
        this.isClosed = true;
    }
}
