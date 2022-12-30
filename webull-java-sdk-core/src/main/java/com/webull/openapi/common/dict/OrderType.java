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
package com.webull.openapi.common.dict;

public enum OrderType {

    /**
     * Limit Order (U.S. Stock, China Connect)
     */
    LIMIT,

    /**
     * Market Order (U.S. Stock)
     */
    MARKET,

    /**
     * Stop Order (U.S. Stock)
     */
    STOP_LOSS,

    /**
     * Stop Limit Order (U.S. Stock)
     */
    STOP_LOSS_LIMIT,

    /**
     * Trailing Stop Order (U.S. Stock)
     */
    TRAILING_STOP_LOSS,

    /**
     * Enhanced Limit Order (Hong Kong stocks)
     */
    ENHANCED_LIMIT,

    /**
     * Auction order (Hong Kong stocks)
     */
    AT_AUCTION,

    /**
     * At-auction limit order (Hong Kong stocks)
     */
    AT_AUCTION_LIMIT,
}
