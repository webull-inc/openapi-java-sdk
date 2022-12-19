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
package com.webull.openapi.common.dict;

public enum Direction {

    /**
     * Buy(stocks are traded at the first set bid price)
     */
    B,

    /**
     * Sell(stocks are traded at the first set ask price)
     */
    S,

    /**
     * Buy(stocks are traded at a higher price than the first set bid price)
     */
    G,

    /**
     * Sell(stocks are traded at a lower price than the first set ask price)
     */
    L,

    /**
     * Neutral
     */
    N
}
