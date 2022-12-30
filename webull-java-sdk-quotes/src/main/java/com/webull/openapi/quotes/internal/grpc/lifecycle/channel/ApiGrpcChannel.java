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

import com.webull.openapi.quotes.internal.grpc.lifecycle.IdleStateHandler;
import com.webull.openapi.quotes.internal.grpc.proto.Gateway;
import com.webull.openapi.quotes.subsribe.lifecycle.ReplyMessage;
import com.webull.openapi.utils.GUID;
import io.grpc.stub.StreamObserver;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ApiGrpcChannel implements GrpcChannel<Gateway.ClientRequest, Gateway.ClientResponse> {

    private static final String CLOSED_MSG = "Channel already closed.";

    private final String channelId;

    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final InFlightRequestsBox inFlightRequestsBox = new InFlightRequestsBox(10000);
    private final IdleStateHandler idleStateHandler = new IdleStateHandler(10, TimeUnit.SECONDS, this::doHeartbeat);

    private StreamObserver<Gateway.ClientRequest> requestStreamObserver;

    public ApiGrpcChannel() {
        this.channelId = GUID.get();
    }

    @Override
    public void send(Gateway.ClientRequest value) {
        if (this.isClosed.get()) {
            throw new IllegalStateException(CLOSED_MSG);
        }
        this.sendByObserver(value);
        this.idleStateHandler.onSend();
    }

    @Override
    public ReplyMessage<Gateway.ClientResponse> exchange(Gateway.ClientRequest value) {
        if (this.isClosed.get()) {
            throw new IllegalStateException(CLOSED_MSG);
        }
        ReplyMessage<Gateway.ClientResponse> replyMessage = this.inFlightRequestsBox.exchange(value);
        this.sendByObserver(value);
        this.idleStateHandler.onSend();
        return replyMessage;
    }

    @Override
    public void receive(Gateway.ClientResponse response) {
        if (this.isClosed.get()) {
            throw new IllegalStateException(CLOSED_MSG);
        }
        this.inFlightRequestsBox.receive(response);
    }

    private void doHeartbeat() {
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Pong)
                .setRequestId(UUID.randomUUID().toString())
                .build();
        this.send(request);
    }

    @Override
    public void init(StreamObserver<Gateway.ClientRequest> requestStreamObserver) {
        if (this.isClosed.get()) {
            throw new IllegalStateException(CLOSED_MSG);
        }
        this.requestStreamObserver = requestStreamObserver;
        this.idleStateHandler.start();
    }

    @Override
    public String id() {
        return this.channelId;
    }

    @Override
    public void close() {
        if (this.isClosed.compareAndSet(false, true)) {
            this.idleStateHandler.close();
            this.closeObserver();
            this.inFlightRequestsBox.close();
        }
    }

    // StreamObserver is not thread-safe!!!
    private synchronized void sendByObserver(Gateway.ClientRequest request) {
        this.requestStreamObserver.onNext(request);
    }

    private synchronized void closeObserver() {
        if (this.requestStreamObserver != null) {
            this.requestStreamObserver.onCompleted();
        }
    }
}
