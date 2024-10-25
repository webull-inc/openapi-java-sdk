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

public class HoldingInfo {

    private String id;
    private String instrumentId;
    private String symbol;
    private String instrumentType;
    private String shortName;

    private String currency;
    private String unitCost;

    private String qty;
    private String totalCost;
    private String lastPrice;
    private String marketValue;
    private String unrealizedProfitLoss;
    private String unrealizedProfitLossRate;
    private String holdingProportion;
    private String accountTaxType;

    private List<ContractPosition> positions;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
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

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(String unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public String getUnrealizedProfitLossRate() {
        return unrealizedProfitLossRate;
    }

    public void setUnrealizedProfitLossRate(String unrealizedProfitLossRate) {
        this.unrealizedProfitLossRate = unrealizedProfitLossRate;
    }

    public String getHoldingProportion() {
        return holdingProportion;
    }

    public void setHoldingProportion(String holdingProportion) {
        this.holdingProportion = holdingProportion;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public List<ContractPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<ContractPosition> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "HoldingInfo{" +
                "id='" + id + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", shortName='" + shortName + '\'' +
                ", currency='" + currency + '\'' +
                ", unitCost='" + unitCost + '\'' +
                ", qty=" + qty +
                ", totalCost='" + totalCost + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", unrealizedProfitLossRate='" + unrealizedProfitLossRate + '\'' +
                ", holdingProportion='" + holdingProportion + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", positions=" + positions +
                '}';
    }
}
