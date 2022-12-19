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

/**
 * A handler to handle mqtt connection session.
 */
public interface QuotesSubsSessionHandler extends QuotesSubsHandler {

    /**
     * The connection is now connected.
     * @param context connected context.
     */
    void onConnected(QuotesSubsConnectedContext context);

    /**
     * The connection is now disconnected.
     * @param context disconnected context.
     */
    void onDisconnected(QuotesSubsFailedContext context);
}
