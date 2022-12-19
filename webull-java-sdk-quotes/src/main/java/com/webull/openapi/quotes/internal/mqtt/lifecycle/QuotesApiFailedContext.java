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

import com.webull.openapi.quotes.subsribe.lifecycle.ClientStateMachine;
import com.webull.openapi.quotes.subsribe.lifecycle.QuotesSubsFailedContext;

import java.util.Optional;

public class QuotesApiFailedContext implements QuotesSubsFailedContext {

    private final ClientStateMachine state;
    private final int retries;
    private final Throwable cause;

    public QuotesApiFailedContext(ClientStateMachine state, int retries, Throwable cause) {
        this.state = state;
        this.retries = retries;
        this.cause = cause;
    }

    @Override
    public int getRetriesAttempted() {
        return retries;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public boolean throttled() {
        return false;
    }

    @Override
    public void setThrottled(boolean throttled) {
        // do nothing
    }

    @Override
    public Optional<Integer> getConnAck() {
        return Optional.empty();
    }

    @Override
    public boolean userDisconnect() {
        return state.userCalledDisconnect();
    }

    @Override
    public boolean clientDisconnect() {
        return false;
    }

    @Override
    public boolean serverDisconnect() {
        return false;
    }

    @Override
    public ClientStateMachine getState() {
        return state;
    }
}
