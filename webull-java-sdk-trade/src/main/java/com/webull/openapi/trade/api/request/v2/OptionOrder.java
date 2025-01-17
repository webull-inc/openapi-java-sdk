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

import java.util.ArrayList;
import java.util.List;

public class OptionOrder {

    private String clientComboOrderId;
    private List<OptionOrderItem> newOrders = new ArrayList<>();
    private List<OptionOrderItem> modifyOrders = new ArrayList<>();
    private String clientOrderId;

    public String getClientComboOrderId() {
        return clientComboOrderId;
    }

    public void setClientComboOrderId(String clientComboOrderId) {
        this.clientComboOrderId = clientComboOrderId;
    }

    public List<OptionOrderItem> getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(List<OptionOrderItem> newOrders) {
        this.newOrders = newOrders;
    }

    public List<OptionOrderItem> getModifyOrders() {
        return modifyOrders;
    }

    public void setModifyOrders(List<OptionOrderItem> modifyOrders) {
        this.modifyOrders = modifyOrders;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    @Override
    public String toString() {
        return "OptionOrder{" +
                "clientComboOrderId='" + clientComboOrderId + '\'' +
                ", newOrders='" + newOrders + '\'' +
                ", modifyOrders='" + modifyOrders + '\'' +
                ", clientOrderId='" + clientOrderId + '\'' +
                '}';
    }


}
