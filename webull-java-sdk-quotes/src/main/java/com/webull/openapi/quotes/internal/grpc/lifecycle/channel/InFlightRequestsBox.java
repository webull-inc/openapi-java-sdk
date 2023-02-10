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

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.quotes.api.lifecycle.Mailbox;
import com.webull.openapi.quotes.api.lifecycle.ReplyMessage;
import com.webull.openapi.quotes.internal.grpc.lifecycle.SimpleReplyMessage;
import com.webull.openapi.quotes.internal.grpc.proto.Gateway;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class InFlightRequestsBox implements Mailbox<Gateway.ClientRequest, Gateway.ClientResponse>, Closeable {

    private final int maxInflightSize;
    private final ConcurrentHashMap<String, SimpleReplyMessage<Gateway.ClientResponse>> results;

    public InFlightRequestsBox(int maxInflightSize) {
        this.maxInflightSize = maxInflightSize;
        this.results = new ConcurrentHashMap<>(32);
    }

    @Override
    public void send(Gateway.ClientRequest value) {
        // do nothing
    }

    @Override
    public ReplyMessage<Gateway.ClientResponse> exchange(Gateway.ClientRequest request) {
        Assert.notBlank("requestId", request.getRequestId());
        if (this.results.size() >= this.maxInflightSize) {
            throw new ClientException(ErrorCode.TOO_MANY_REQUESTS, "To many in flight requests, exceeded max=" + this.maxInflightSize);
        }
        return this.results.computeIfAbsent(request.getRequestId(), requestId -> {
            SimpleReplyMessage<Gateway.ClientResponse> replyMessage = new SimpleReplyMessage<>();
            replyMessage.whenComplete((response, error) -> this.results.remove(requestId).close());
            return replyMessage;
        });
    }

    @Override
    public void receive(Gateway.ClientResponse response) {
        if (StringUtils.isEmpty(response.getRequestId())) {
            return;
        }
        ReplyMessage<Gateway.ClientResponse> replyMessage = this.results.get(response.getRequestId());
        if (replyMessage != null) {
            replyMessage.receive(response);
        }
    }

    @Override
    public void close() {
        this.results.values().forEach(replyMessage -> replyMessage.completeExceptionally(new IOException("Grpc channel closed.")));
    }
}
