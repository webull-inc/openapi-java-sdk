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

import com.webull.openapi.grpc.message.GrpcMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class SubscribeRequest implements GrpcMessage {

    private final int subscribeType;
    private final long timestamp;
    private final Set<String> accounts;

    public SubscribeRequest(String... accounts) {
        this(new HashSet<>(Arrays.asList(accounts)));
    }

    public SubscribeRequest(Set<String> accounts) {
        this.subscribeType = 1;
        this.timestamp = System.currentTimeMillis();
        this.accounts = accounts;
    }

    public int getSubscribeType() {
        return subscribeType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Set<String> getAccounts() {
        return accounts;
    }
}
