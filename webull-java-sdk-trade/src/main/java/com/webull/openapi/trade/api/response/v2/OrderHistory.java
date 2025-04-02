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
package com.webull.openapi.trade.api.response.v2;

import com.webull.openapi.trade.api.response.NOrderItem;

import java.util.List;

public class OrderHistory {

    private String clientOrderId;
    private String orderId;
    private String side;
    private String orderType;
    private String timeInForce;
    private String stopPrice;
    private String limitPrice;
    private String quantity;
    private String filledQuantity;
    private String status;
    private String supportTradingSession;
    private String optionStrategy;
    private String comboInstrumentType;
    private String comboOrderId;
    private String comboType;
    private List<NOrderItem> items;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
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

    public String getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupportTradingSession() {
        return supportTradingSession;
    }

    public void setSupportTradingSession(String supportTradingSession) {
        this.supportTradingSession = supportTradingSession;
    }

    public String getOptionStrategy() {
        return optionStrategy;
    }

    public void setOptionStrategy(String optionStrategy) {
        this.optionStrategy = optionStrategy;
    }

    public String getComboInstrumentType() {
        return comboInstrumentType;
    }

    public void setComboInstrumentType(String comboInstrumentType) {
        this.comboInstrumentType = comboInstrumentType;
    }

    public String getComboOrderId() {
        return comboOrderId;
    }

    public void setComboOrderId(String comboOrderId) {
        this.comboOrderId = comboOrderId;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public List<NOrderItem> getItems() {
        return items;
    }

    public void setItems(List<NOrderItem> items) {
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", status='" + status + '\'' +
                ", supportTradingSession='" + supportTradingSession + '\'' +
                ", optionStrategy='" + optionStrategy + '\'' +
                ", comboInstrumentType='" + comboInstrumentType + '\'' +
                ", comboOrderId='" + comboOrderId + '\'' +
                ", comboType='" + comboType + '\'' +
                ", items=" + items +
                '}';
    }
}
