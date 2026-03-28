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

    private String totalAssetCurrency;

    private String totalCashBalance;

    private String totalUnrealizedProfitLoss;

    private String totalMarketValue;

    private String maintenanceMargin;

    private String usedMargin;

    private String usedMarginForOpenOrder;

    private String marginExcess;

    private String marginRatio;

    private List<AccountAssetInfo> accountCurrencyAssets;


    public String getTotalUnrealizedProfitLoss() {
        return totalUnrealizedProfitLoss;
    }

    public void setTotalUnrealizedProfitLoss(String totalUnrealizedProfitLoss) {
        this.totalUnrealizedProfitLoss = totalUnrealizedProfitLoss;
    }

    public String getTotalAssetCurrency() {
        return totalAssetCurrency;
    }

    public void setTotalAssetCurrency(String totalAssetCurrency) {
        this.totalAssetCurrency = totalAssetCurrency;
    }

    public String getTotalCashBalance() {
        return totalCashBalance;
    }

    public void setTotalCashBalance(String totalCashBalance) {
        this.totalCashBalance = totalCashBalance;
    }

    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public String getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(String maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    public String getUsedMargin() {
        return usedMargin;
    }

    public void setUsedMargin(String usedMargin) {
        this.usedMargin = usedMargin;
    }

    public String getUsedMarginForOpenOrder() {
        return usedMarginForOpenOrder;
    }

    public void setUsedMarginForOpenOrder(String usedMarginForOpenOrder) {
        this.usedMarginForOpenOrder = usedMarginForOpenOrder;
    }

    public String getMarginExcess() {
        return marginExcess;
    }

    public void setMarginExcess(String marginExcess) {
        this.marginExcess = marginExcess;
    }

    public String getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(String marginRatio) {
        this.marginRatio = marginRatio;
    }

    public List<AccountAssetInfo> getAccountCurrencyAssets() {
        return accountCurrencyAssets;
    }

    public void setAccountCurrencyAssets(List<AccountAssetInfo> accountCurrencyAssets) {
        this.accountCurrencyAssets = accountCurrencyAssets;
    }

    @Override
    public String toString() {
        return "AccountBalanceInfo{" +
                "totalAssetCurrency='" + totalAssetCurrency + '\'' +
                ", totalCashBalance='" + totalCashBalance + '\'' +
                ", totalUnrealizedProfitLoss='" + totalUnrealizedProfitLoss + '\'' +
                ", totalMarketValue=" + totalMarketValue +
                ", maintenance_margin=" + maintenanceMargin +
                ", usedMargin=" + usedMargin +
                ", usedMarginForOpenOrder=" + usedMarginForOpenOrder +
                ", marginExcess=" + marginExcess +
                ", marginRatio=" + marginRatio +
                ", accountCurrencyAssets=" + accountCurrencyAssets +
                '}';
    }
}
