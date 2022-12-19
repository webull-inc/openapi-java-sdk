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
package com.webull.openapi.quotes.subsribe.lifecycle;

import com.webull.openapi.retry.RetryContext;

import java.util.Optional;

/**
 * Quotes subscribe connection failed context.
 */
public interface QuotesSubsFailedContext extends RetryContext {

    /**
     * The ConnAck message if contained an error code.
     * @return The ConnAck message if contained an error code.
     */
    Optional<Integer> getConnAck();

    /**
     * @return if the user explicitly called disconnect.
     */
    boolean userDisconnect();

    /**
     * @return if the client closed the connection.
     */
    boolean clientDisconnect();

    /**
     * @return if the server sent a disconnect message or closed the connection without a disconnect message.
     */
    boolean serverDisconnect();

    /**
     * @return Client state machine
     */
    ClientStateMachine getState();
}
