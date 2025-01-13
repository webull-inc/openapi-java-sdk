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
package com.webull.openapi.trade.api.request.v2;

public class OptionOrderItemLeg {

    private String clientOrderId;
    private String orderId;
    private String side;
    private String quantity;
    private String market;
    private String instrumentType;
    private String symbol;
    private String strikePrice;
    private String initExpDate;
    private String optionType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getInitExpDate() {
        return initExpDate;
    }

    public void setInitExpDate(String initExpDate) {
        this.initExpDate = initExpDate;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    @Override
    public String toString() {
        return "OptionOrderItemLeg{" +
                "clientOrderId='" + clientOrderId + '\'' +
                "orderId='" + orderId + '\'' +
                ", side='" + side + '\'' +
                ", quantity='" + quantity + '\'' +
                ", market='" + market + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", symbol=" + symbol +
                ", strikePrice='" + strikePrice + '\'' +
                ", initExpDate='" + initExpDate + '\'' +
                ", optionType='" + optionType + '\'' +
                '}';
    }

}
