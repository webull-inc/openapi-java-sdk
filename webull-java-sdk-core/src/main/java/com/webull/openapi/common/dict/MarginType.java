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

public enum MarginType {

    /**
     * The customer's open position needs to be closed on the same day.
     * That's the intraday margin.
     */
    ONE_DAY,

    /**
     * Customers can open positions overnight using unlimited margin.
     */
    INDEFINITE
}
