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

public class JPAccountAssetInfo {

    /**
     * 币种，取值参考字典值 CurrencyEnum
     */
    private String currency;

    /**
     * 现金余额，币种为currency字段值，单位元，精确到小数点后2位
     */
    private String cashBalance;

    /**
     * unsettledCash，币种为currency字段值，单位元，精确到小数点后2位
     */
    private String unsettledCash;

    /**
     * 购买力，币种为currency字段值，单位元，精确到小数点后2位
     */
    private String buyingPower;

    /**
     * 持仓盈亏
     */
    private String unrealizedProfitLoss;


    @Override
    public String toString() {
        return "JPAccountAssetInfo{" +
                "currency='" + currency + '\'' +
                ", cashBalance='" + cashBalance + '\'' +
                ", unsettledCash='" + unsettledCash + '\'' +
                ", buyingPower='" + buyingPower + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                '}';
    }
}
