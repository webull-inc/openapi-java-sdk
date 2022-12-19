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
package com.webull.openapi.trade.api.request;

public class StockOrder {

    private String clientOrderId;
    private String instrumentId;
    private String qty;
    private String side;
    private String tif;
    private String extendedHoursTrading;
    private String orderType;
    private String limitPrice;
    private String stopPrice;
    private String trailingType;
    private String trailingStopStep;

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
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

    public String getTif() {
        return tif;
    }

    public void setTif(String tif) {
        this.tif = tif;
    }

    public String getExtendedHoursTrading() {
        return extendedHoursTrading;
    }

    public void setExtendedHoursTrading(String extendedHoursTrading) {
        this.extendedHoursTrading = extendedHoursTrading;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(String stopPrice) {
        this.stopPrice = stopPrice;
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

    @Override
    public String toString() {
        return "StockOrder{" +
                "clientOrderId='" + clientOrderId + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", qty='" + qty + '\'' +
                ", side='" + side + '\'' +
                ", tif='" + tif + '\'' +
                ", extendedHoursTrading='" + extendedHoursTrading + '\'' +
                ", orderType='" + orderType + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", trailingType='" + trailingType + '\'' +
                ", trailingStopStep='" + trailingStopStep + '\'' +
                '}';
    }
}
