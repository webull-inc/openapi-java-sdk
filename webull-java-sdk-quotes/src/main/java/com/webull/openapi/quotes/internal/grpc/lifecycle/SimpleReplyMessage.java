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
package com.webull.openapi.quotes.internal.grpc.lifecycle;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.quotes.subsribe.lifecycle.ReplyMessage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

public class SimpleReplyMessage<RespT> implements ReplyMessage<RespT> {

    private final CompletableFuture<RespT> future = new CompletableFuture<>();

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public RespT get() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(ErrorCode.INVALID_STATE, "Request interrupted.", e);
        } catch (ExecutionException e) {
            throw new ClientException("Request error.", e);
        }
    }

    @Override
    public RespT get(long timeout, TimeUnit timeUnit) {
        try {
            return future.get(timeout, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ClientException(ErrorCode.INVALID_STATE, "Request interrupted.", e);
        } catch (ExecutionException e) {
            throw new ClientException("Request error.", e);
        } catch (TimeoutException e) {
            throw new ClientException(ErrorCode.TIMEOUT, "Read timeout.", e);
        }
    }

    @Override
    public boolean receive(RespT receive) {
        return future.complete(receive);
    }

    @Override
    public boolean completeExceptionally(Throwable ex) {
        return future.completeExceptionally(ex);
    }


    public SimpleReplyMessage<RespT> whenComplete(BiConsumer<? super RespT, ? super Throwable> completeAction) {
        future.whenComplete(completeAction);
        return this;
    }

    @Override
    public void close() {
        if (!this.future.isDone()) {
            this.future.cancel(false);
        }
    }
}
