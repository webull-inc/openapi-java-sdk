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

public class ContractPosition {

    private String contractId;

    private String qty;

    private String totalCost;

    private String unrealizedProfitLoss;

    private String marketValue;

    private String marginType;

    private String accountTaxType;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(String unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    @Override
    public String toString() {
        return "ContractPosition{" +
                "contractId='" + contractId + '\'' +
                ", qty='" + qty + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", marginType='" + marginType + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                '}';
    }
}
