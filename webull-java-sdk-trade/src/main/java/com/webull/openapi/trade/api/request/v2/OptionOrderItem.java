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

import java.util.List;

public class OptionOrderItem {

    private String clientOrderId;
    private String comboType;
    private String optionStrategy;
    private String side;
    private String orderType;
    private String timeInForce;
    private String stopPrice;
    private String limitPrice;
    private String quantity;
    private String entrustType;
    private String currentAsk;
    private String currentBid;
    private List<OptionOrderItemLeg> orders;

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

    public String getOptionStrategy() {
        return optionStrategy;
    }

    public void setOptionStrategy(String optionStrategy) {
        this.optionStrategy = optionStrategy;
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

    public List<OptionOrderItemLeg> getOrders() {
        return orders;
    }

    public void setOrders(List<OptionOrderItemLeg> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OptionOrderItem{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", comboType='" + comboType + '\'' +
                ", optionStrategy='" + optionStrategy + '\'' +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", timeInForce=" + timeInForce +
                ", stopPrice='" + stopPrice + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", entrustType='" + entrustType + '\'' +
                ", currentAsk='" + currentAsk + '\'' +
                ", currentBid='" + currentBid + '\'' +
                ", orders='" + orders + '\'' +
                '}';
    }

}
