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

public class JPAccountAsset {

    private String currency;

    private String positionsMarketValue;

    private String totalCollateralValue;

    private String balance;

    private String totalCash;

    private String unsettledCash;

    private String settledCash;

    private String frozenCash;

    private String availableToWithdraw;

    private String availableToExchange;

    private String stockPower;

    private String overnightPower;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPositionsMarketValue() {
        return positionsMarketValue;
    }

    public void setPositionsMarketValue(String positionsMarketValue) {
        this.positionsMarketValue = positionsMarketValue;
    }

    public String getTotalCollateralValue() {
        return totalCollateralValue;
    }

    public void setTotalCollateralValue(String totalCollateralValue) {
        this.totalCollateralValue = totalCollateralValue;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public String getUnsettledCash() {
        return unsettledCash;
    }

    public void setUnsettledCash(String unsettledCash) {
        this.unsettledCash = unsettledCash;
    }

    public String getSettledCash() {
        return settledCash;
    }

    public void setSettledCash(String settledCash) {
        this.settledCash = settledCash;
    }

    public String getFrozenCash() {
        return frozenCash;
    }

    public void setFrozenCash(String frozenCash) {
        this.frozenCash = frozenCash;
    }

    public String getAvailableToWithdraw() {
        return availableToWithdraw;
    }

    public void setAvailableToWithdraw(String availableToWithdraw) {
        this.availableToWithdraw = availableToWithdraw;
    }

    public String getAvailableToExchange() {
        return availableToExchange;
    }

    public void setAvailableToExchange(String availableToExchange) {
        this.availableToExchange = availableToExchange;
    }

    public String getStockPower() {
        return stockPower;
    }

    public void setStockPower(String stockPower) {
        this.stockPower = stockPower;
    }

    public String getOvernightPower() {
        return overnightPower;
    }

    public void setOvernightPower(String overnightPower) {
        this.overnightPower = overnightPower;
    }

    @Override
    public String toString() {
        return "AccountAsset{" +
                "currency='" + currency + '\'' +
                ", positionsMarketValue='" + positionsMarketValue + '\'' +
                ", totalCollateralValue='" + totalCollateralValue + '\'' +
                ", balance='" + balance + '\'' +
                ", totalCash='" + totalCash + '\'' +
                ", unsettledCash='" + unsettledCash + '\'' +
                ", settledCash='" + settledCash + '\'' +
                ", frozenCash='" + frozenCash + '\'' +
                ", availableToWithdraw='" + availableToWithdraw + '\'' +
                ", availableToExchange='" + availableToExchange + '\'' +
                ", stockPower='" + stockPower + '\'' +
                ", overnightPower='" + overnightPower + '\'' +
                '}';
    }
}
