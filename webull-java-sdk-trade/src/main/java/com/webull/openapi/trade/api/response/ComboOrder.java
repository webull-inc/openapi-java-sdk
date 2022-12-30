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

import java.util.List;

public class ComboOrder implements Order {

    private String accountId;
    private String clientOrderId;
    private String orderId;
    private Boolean extendedHoursTrading;
    private String tif;
    private String comboType;
    private String comboTickerType;
    private List<OrderItem> items;

    @Override
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public Boolean getExtendedHoursTrading() {
        return extendedHoursTrading;
    }

    public void setExtendedHoursTrading(Boolean extendedHoursTrading) {
        this.extendedHoursTrading = extendedHoursTrading;
    }

    @Override
    public String getTif() {
        return tif;
    }

    public void setTif(String tif) {
        this.tif = tif;
    }

    public String getComboType() {
        return comboType;
    }

    public void setComboType(String comboType) {
        this.comboType = comboType;
    }

    public String getComboTickerType() {
        return comboTickerType;
    }

    public void setComboTickerType(String comboTickerType) {
        this.comboTickerType = comboTickerType;
    }

    @Override
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ComboOrder{" +
                "accountId='" + accountId + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", extendedHoursTrading=" + extendedHoursTrading +
                ", tif='" + tif + '\'' +
                ", comboType='" + comboType + '\'' +
                ", comboTickerType='" + comboTickerType + '\'' +
                ", items=" + items +
                '}';
    }
}
