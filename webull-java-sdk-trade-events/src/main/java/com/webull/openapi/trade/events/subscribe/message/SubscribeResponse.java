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
package com.webull.openapi.trade.events.subscribe.message;

import com.webull.openapi.grpc.message.GrpcConnAck;
import com.webull.openapi.grpc.message.GrpcMessage;

public final class SubscribeResponse implements GrpcMessage, GrpcConnAck {

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_TEXT = "text/plain";

    private final int eventType;
    private final int subscribeType;
    private final String contentType;
    private final String payload;
    private final String requestId;
    private final long timestamp;

    public SubscribeResponse(int eventType,
                             int subscribeType,
                             String contentType,
                             String payload,
                             String requestId,
                             long timestamp) {
        this.eventType = eventType;
        this.subscribeType = subscribeType;
        this.contentType = contentType;
        this.payload = payload;
        this.requestId = requestId;
        this.timestamp = timestamp;
    }

    public int getEventType() {
        return eventType;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPayload() {
        return payload;
    }

    public String getRequestId() {
        return requestId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "SubscribeResponse{" +
                "eventType=" + eventType +
                ", subscribeType=" + subscribeType +
                ", contentType='" + contentType + '\'' +
                ", payload='" + payload + '\'' +
                ", requestId='" + requestId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
