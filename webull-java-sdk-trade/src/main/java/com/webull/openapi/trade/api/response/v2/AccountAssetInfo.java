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

public class AccountAssetInfo {

    private String currency;

    private String cashBalance;

    private String marketValue;

    private String settledCash;

    private String unsettledCash;

    private String buyingPower;

    private String unrealizedProfitLoss;

    private String availableWithdrawal;

    private String heldAmount;

    private String frozenAmount;

    private String interestsUnpaid;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

    public String getSettledCash() {
        return settledCash;
    }

    public void setSettledCash(String settledCash) {
        this.settledCash = settledCash;
    }

    public String getUnsettledCash() {
        return unsettledCash;
    }

    public void setUnsettledCash(String unsettledCash) {
        this.unsettledCash = unsettledCash;
    }

    public String getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(String buyingPower) {
        this.buyingPower = buyingPower;
    }

    public String getUnrealizedProfitLoss() {
        return unrealizedProfitLoss;
    }

    public void setUnrealizedProfitLoss(String unrealizedProfitLoss) {
        this.unrealizedProfitLoss = unrealizedProfitLoss;
    }

    public String getAvailableWithdrawal() {
        return availableWithdrawal;
    }

    public void setAvailableWithdrawal(String availableWithdrawal) {
        this.availableWithdrawal = availableWithdrawal;
    }

    public String getHeldAmount() {
        return heldAmount;
    }

    public void setHeldAmount(String heldAmount) {
        this.heldAmount = heldAmount;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getInterestsUnpaid() {
        return interestsUnpaid;
    }

    public void setInterestsUnpaid(String interestsUnpaid) {
        this.interestsUnpaid = interestsUnpaid;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    @Override
    public String toString() {
        return "AccountAssetInfo{" +
                "currency='" + currency + '\'' +
                ", cashBalance='" + cashBalance + '\'' +
                ", marketValue='" + marketValue + '\'' +
                ", settledCash='" + settledCash + '\'' +
                ", unsettledCash='" + unsettledCash + '\'' +
                ", buyingPower='" + buyingPower + '\'' +
                ", unrealizedProfitLoss='" + unrealizedProfitLoss + '\'' +
                ", availableWithdrawal='" + availableWithdrawal + '\'' +
                ", heldAmount='" + heldAmount + '\'' +
                ", frozenAmount='" + frozenAmount + '\'' +
                ", interestsUnpaid='" + interestsUnpaid + '\'' +
                '}';
    }
}
