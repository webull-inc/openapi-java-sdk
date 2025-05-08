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
package com.webull.openapi.trade.api.request.v2;

import java.io.Serializable;
import java.util.List;

public class TradeOrderItem implements Serializable {

    private static final long serialVersionUID = -8882046247562010802L;

    private String clientOrderId;
    private String comboType;
    private String market;
    private String instrumentType;
    private String symbol;
    private String side;
    private String orderType;
    private String timeInForce;
    private String stopPrice;
    private String limitPrice;
    private String quantity;
    private String entrustType;
    private String supportTradingSession;
    private String trailingType;
    private String trailingStopStep;
    private String currentAsk;
    private String currentBid;
    private String accountTaxType;
    private String tradeCurrency;
    private String marginType;
    private List<CloseContract> closeContracts;
    private String senderSubId;
    private List<NoPartyId> noPartyIds;
    private String totalCashAmount;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(String timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getSupportTradingSession() {
        return supportTradingSession;
    }

    public void setSupportTradingSession(String supportTradingSession) {
        this.supportTradingSession = supportTradingSession;
    }

    public String getTrailingType() {
        return trailingType;
    }

    public void setTrailingType(String trailingType) {
        this.trailingType = trailingType;
    }

    public String getTrailingStopStep() {
        return trailingStopStep;
    }

    public void setTrailingStopStep(String trailingStopStep) {
        this.trailingStopStep = trailingStopStep;
    }

    public String getCurrentAsk() {
        return currentAsk;
    }

    public void setCurrentAsk(String currentAsk) {
        this.currentAsk = currentAsk;
    }

    public String getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(String currentBid) {
        this.currentBid = currentBid;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public List<CloseContract> getCloseContracts() {
        return closeContracts;
    }

    public void setCloseContracts(List<CloseContract> closeContracts) {
        this.closeContracts = closeContracts;
    }

    public String getSenderSubId() {
        return senderSubId;
    }

    public void setSenderSubId(String senderSubId) {
        this.senderSubId = senderSubId;
    }

    public List<NoPartyId> getNoPartyIds() {
        return noPartyIds;
    }

    public void setNoPartyIds(List<NoPartyId> noPartyIds) {
        this.noPartyIds = noPartyIds;
    }

    public String getTotalCashAmount() {
        return totalCashAmount;
    }

    public void setTotalCashAmount(String totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

    @Override
    public String toString() {
        return "TradeOrderItem{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", comboType='" + comboType + '\'' +
                ", market='" + market + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", symbol='" + symbol + '\'' +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", entrustType='" + entrustType + '\'' +
                ", supportTradingSession='" + supportTradingSession + '\'' +
                ", trailingType='" + trailingType + '\'' +
                ", trailingStopStep='" + trailingStopStep + '\'' +
                ", currentAsk='" + currentAsk + '\'' +
                ", currentBid='" + currentBid + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", tradeCurrency='" + tradeCurrency + '\'' +
                ", marginType='" + marginType + '\'' +
                ", closeContracts=" + closeContracts +
                ", senderSubId='" + senderSubId + '\'' +
                ", noPartyIds=" + noPartyIds +
                ", totalCashAmount='" + totalCashAmount + '\'' +
                '}';
    }
}
