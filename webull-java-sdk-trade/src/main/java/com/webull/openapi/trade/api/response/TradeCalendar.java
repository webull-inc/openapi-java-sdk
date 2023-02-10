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
package com.webull.openapi.trade.api.response;

public class TradeCalendar {

    /**
     * Trade day in yyyy-mm-dd format
     */
    private String tradeDay;

    /**
     * Trade date type
     * @see com.webull.openapi.common.dict.TradingDateType
     */
    private String tradeDateType;

    public String getTradeDay() {
        return tradeDay;
    }

    public void setTradeDay(String tradeDay) {
        this.tradeDay = tradeDay;
    }

    public String getTradeDateType() {
        return tradeDateType;
    }

    public void setTradeDateType(String tradeDateType) {
        this.tradeDateType = tradeDateType;
    }

    @Override
    public String toString() {
        return "TradeCalendar{" +
                "tradeDay='" + tradeDay + '\'' +
                ", tradeDateType='" + tradeDateType + '\'' +
                '}';
    }
}
