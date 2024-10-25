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

import com.webull.openapi.trade.api.request.CloseContract;

import java.util.Collections;
import java.util.List;

public class SimpleOrder extends OrderItem implements Order {

    private String accountId;
    private String clientOrderId;
    private String orderId;
    private Boolean extendedHoursTrading;
    private String tif;
    private String accountTaxType;
    private String marginType;
    private List<CloseContract> closeContracts;

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

    @Override
    public List<OrderItem> getItems() {
        return Collections.singletonList(this);
    }

    public void setTif(String tif) {
        this.tif = tif;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
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

    @Override
    public String toString() {
        return "SimpleOrder{" +
                "accountId='" + accountId + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", extendedHoursTrading=" + extendedHoursTrading +
                ", tif='" + tif + '\'' +
                ", category='" + category + '\'' +
                ", filledPrice='" + filledPrice + '\'' +
                ", filledQty='" + filledQty + '\'' +
                ", lastFilledTime='" + lastFilledTime + '\'' +
                ", limitPrice='" + limitPrice + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderType='" + orderType + '\'' +
                ", placeTime='" + placeTime + '\'' +
                ", qty='" + qty + '\'' +
                ", side='" + side + '\'' +
                ", stopPrice='" + stopPrice + '\'' +
                ", shortName='" + shortName + '\'' +
                ", trailingStopStep='" + trailingStopStep + '\'' +
                ", trailingType='" + trailingType + '\'' +
                ", instrumentId='" + instrumentId + '\'' +
                ", currency='" + currency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", marginType='" + marginType + '\'' +
                ", closeContracts=" + closeContracts +
                '}';
    }
}
