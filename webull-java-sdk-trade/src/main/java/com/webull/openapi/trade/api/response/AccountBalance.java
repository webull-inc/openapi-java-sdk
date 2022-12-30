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

import java.util.List;

public class AccountBalance {

    private String accountId;

    private String totalAssetCurrency;

    private String totalAsset;

    private String totalMarketValue;

    private String totalCashBalance;

    private String marginUtilizationRate;

    private List<AccountAsset> accountCurrencyAssets;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTotalAssetCurrency() {
        return totalAssetCurrency;
    }

    public void setTotalAssetCurrency(String totalAssetCurrency) {
        this.totalAssetCurrency = totalAssetCurrency;
    }

    public String getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(String totalAsset) {
        this.totalAsset = totalAsset;
    }

    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    public String getTotalCashBalance() {
        return totalCashBalance;
    }

    public void setTotalCashBalance(String totalCashBalance) {
        this.totalCashBalance = totalCashBalance;
    }

    public String getMarginUtilizationRate() {
        return marginUtilizationRate;
    }

    public void setMarginUtilizationRate(String marginUtilizationRate) {
        this.marginUtilizationRate = marginUtilizationRate;
    }

    public List<AccountAsset> getAccountCurrencyAssets() {
        return accountCurrencyAssets;
    }

    public void setAccountCurrencyAssets(List<AccountAsset> accountCurrencyAssets) {
        this.accountCurrencyAssets = accountCurrencyAssets;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountId='" + accountId + '\'' +
                ", totalAssetCurrency='" + totalAssetCurrency + '\'' +
                ", totalAsset='" + totalAsset + '\'' +
                ", totalMarketValue='" + totalMarketValue + '\'' +
                ", totalCashBalance='" + totalCashBalance + '\'' +
                ", marginUtilizationRate='" + marginUtilizationRate + '\'' +
                ", accountCurrencyAssets=" + accountCurrencyAssets +
                '}';
    }
}
