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

public class AccountAsset {

    private String currency;
    private String netLiquidationValue;
    private String positionsMarketValue;
    private String cashBalance;
    private String marginPower;
    private String cashPower;
    private String pendingIncoming;
    private String cashFrozen;
    private String availableWithdrawal;
    private String interestsUnpaid;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNetLiquidationValue() {
        return netLiquidationValue;
    }

    public void setNetLiquidationValue(String netLiquidationValue) {
        this.netLiquidationValue = netLiquidationValue;
    }

    public String getPositionsMarketValue() {
        return positionsMarketValue;
    }

    public void setPositionsMarketValue(String positionsMarketValue) {
        this.positionsMarketValue = positionsMarketValue;
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getMarginPower() {
        return marginPower;
    }

    public void setMarginPower(String marginPower) {
        this.marginPower = marginPower;
    }

    public String getCashPower() {
        return cashPower;
    }

    public void setCashPower(String cashPower) {
        this.cashPower = cashPower;
    }

    public String getPendingIncoming() {
        return pendingIncoming;
    }

    public void setPendingIncoming(String pendingIncoming) {
        this.pendingIncoming = pendingIncoming;
    }

    public String getCashFrozen() {
        return cashFrozen;
    }

    public void setCashFrozen(String cashFrozen) {
        this.cashFrozen = cashFrozen;
    }

    public String getAvailableWithdrawal() {
        return availableWithdrawal;
    }

    public void setAvailableWithdrawal(String availableWithdrawal) {
        this.availableWithdrawal = availableWithdrawal;
    }

    public String getInterestsUnpaid() {
        return interestsUnpaid;
    }

    public void setInterestsUnpaid(String interestsUnpaid) {
        this.interestsUnpaid = interestsUnpaid;
    }

    @Override
    public String toString() {
        return "AccountAsset{" +
                "currency='" + currency + '\'' +
                ", netLiquidationValue='" + netLiquidationValue + '\'' +
                ", positionsMarketValue='" + positionsMarketValue + '\'' +
                ", cashBalance='" + cashBalance + '\'' +
                ", marginPower='" + marginPower + '\'' +
                ", cashPower='" + cashPower + '\'' +
                ", pendingIncoming='" + pendingIncoming + '\'' +
                ", cashFrozen='" + cashFrozen + '\'' +
                ", availableWithdrawal='" + availableWithdrawal + '\'' +
                ", interestsUnpaid='" + interestsUnpaid + '\'' +
                '}';
    }
}
