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
package com.webull.openapi.trade.api.response;



public class CommonPositionInfo {

    /**
     * 单腿ID
     */
    private String itemId;


    /**
     * 方向：买为BUY，卖为SELL
     */
    private String side;

    /**
     * 数量
     */
    private String quantity;

    /**
     * symbol
     */
    private String symbol;

    /**
     * 成本价
     */
    private String costPrice;

    /**
     * 持仓盈亏
     */
    private String unrealizedProfitLoss;

    /**
     * 税收类型，非通用，日本地区，枚举类型【GENERAL】
     * JP
     */
    private String accountTaxType;
}
