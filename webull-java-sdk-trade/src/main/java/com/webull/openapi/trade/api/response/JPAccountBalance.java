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

public class JPAccountBalance implements BalanceBase{

    private String accountId;

    private String totalAssetCurrency;

    private String totalMarketValue;

    private String marginUtilizationRate;

    private String totalCollateralValue;

    private String oneDayMarginPower;

    private String infiniteMarginPower;

    private List<JPAccountAsset> accountCurrencyAssets;

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getTotalAssetCurrency() {
        return totalAssetCurrency;
    }

    public void setTotalAssetCurrency(String totalAssetCurrency) {
        this.totalAssetCurrency = totalAssetCurrency;
    }

    @Override
    public String getTotalMarketValue() {
        return totalMarketValue;
    }

    public void setTotalMarketValue(String totalMarketValue) {
        this.totalMarketValue = totalMarketValue;
    }

    @Override
    public String getMarginUtilizationRate() {
        return marginUtilizationRate;
    }

    public void setMarginUtilizationRate(String marginUtilizationRate) {
        this.marginUtilizationRate = marginUtilizationRate;
    }

    public String getTotalCollateralValue() {
        return totalCollateralValue;
    }

    public void setTotalCollateralValue(String totalCollateralValue) {
        this.totalCollateralValue = totalCollateralValue;
    }

    public String getOneDayMarginPower() {
        return oneDayMarginPower;
    }

    public void setOneDayMarginPower(String oneDayMarginPower) {
        this.oneDayMarginPower = oneDayMarginPower;
    }

    public String getInfiniteMarginPower() {
        return infiniteMarginPower;
    }

    public void setInfiniteMarginPower(String infiniteMarginPower) {
        this.infiniteMarginPower = infiniteMarginPower;
    }

    public List<JPAccountAsset> getAccountCurrencyAssets() {
        return accountCurrencyAssets;
    }

    public void setAccountCurrencyAssets(List<JPAccountAsset> accountCurrencyAssets) {
        this.accountCurrencyAssets = accountCurrencyAssets;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "accountId='" + accountId + '\'' +
                ", totalAssetCurrency='" + totalAssetCurrency + '\'' +
                ", totalMarketValue='" + totalMarketValue + '\'' +
                ", marginUtilizationRate='" + marginUtilizationRate + '\'' +
                ", totalCollateralValue='" + totalCollateralValue + '\'' +
                ", oneDayMarginPower='" + oneDayMarginPower + '\'' +
                ", infiniteMarginPower='" + infiniteMarginPower + '\'' +
                ", accountCurrencyAssets=" + accountCurrencyAssets +
                '}';
    }
}
