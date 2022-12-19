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

public class Order {
    private String accountId;
    private String category;
    private String currency;
    private String clientOrderId;
    private Boolean extendedHoursTrading;
    private String filledPrice;
    private String filledQty;
    private String instrumentId;
    private String lastFilledTime;
    private String limitPrice;
    private String orderId;
    private String orderStatus;
    private String orderType;
    private String placeTime;
    private String qty;
    private String side ;
    private String stopPrice;
    private String symbol;
    private String shortName;
    private String tif;
    private String trailingStopStep;
    private String trailingType;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public Boolean getExtendedHoursTrading() {
        return extendedHoursTrading;
    }

    public void setExtendedHoursTrading(Boolean extendedHoursTrading) {
        this.extendedHoursTrading = extendedHoursTrading;
    }

    public String getFilledPrice() {
        return filledPrice;
    }

    public void setFilledPrice(String filledPrice) {
        this.filledPrice = filledPrice;
    }

    public String getFilledQty() {
        return filledQty;
    }

    public void setFilledQty(String filledQty) {
        this.filledQty = filledQty;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getLastFilledTime() {
        return lastFilledTime;
    }

    public void setLastFilledTime(String lastFilledTime) {
        this.lastFilledTime = lastFilledTime;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(String placeTime) {
        this.placeTime = placeTime;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTif() {
        return tif;
    }

    public void setTif(String tif) {
        this.tif = tif;
    }

    public String getTrailingStopStep() {
        return trailingStopStep;
    }

    public void setTrailingStopStep(String trailingStopStep) {
        this.trailingStopStep = trailingStopStep;
    }

    public String getTrailingType() {
        return trailingType;
    }

    public void setTrailingType(String trailingType) {
        this.trailingType = trailingType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "accountId='" + accountId + '\'' +
                ", category='" + category + '\'' +
                ", currency='" + currency + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", extendedHoursTrading=" + extendedHoursTrading +
                ", filledPrice='" + filledPrice + '\'' +
                ", filledQty='" + filledQty + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", lastFilledTime='" + lastFilledTime + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderType='" + orderType + '\'' +
                ", placeTime='" + placeTime + '\'' +
                ", qty='" + qty + '\'' +
                ", side='" + side + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", symbol='" + symbol + '\'' +
                ", shortName='" + shortName + '\'' +
                ", tif='" + tif + '\'' +
                ", trailingStopStep='" + trailingStopStep + '\'' +
                ", trailingType='" + trailingType + '\'' +
                '}';
    }
}
