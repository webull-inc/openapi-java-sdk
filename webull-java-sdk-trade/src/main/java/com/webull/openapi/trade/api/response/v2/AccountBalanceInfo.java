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
package com.webull.openapi.trade.api.response.v2;

import java.util.List;


public class AccountBalanceInfo {

    /**
     * 总资产计价币种
     */
    private String totalAssetCurrency;

    /**
     * 现金总额，币种为totalAssetCurrency字段值，单位元。
     * totalCashBalance=sum(cashBalance*汇率)
     */
    private String totalCashBalance;

    /**
     * 持仓盈亏
     */
    private String totalUnrealizedProfitLoss;

    /**
     * 资产明细
     */
    private List<JPAccountAssetInfo> accountCurrencyAssets;

    @Override
    public String toString() {
        return "JPAccountBalanceInfo{" +
                "totalAssetCurrency='" + totalAssetCurrency + '\'' +
                ", totalCashBalance='" + totalCashBalance + '\'' +
                ", totalUnrealizedProfitLoss='" + totalUnrealizedProfitLoss + '\'' +
                ", accountCurrencyAssets=" + accountCurrencyAssets +
                '}';
    }
}
