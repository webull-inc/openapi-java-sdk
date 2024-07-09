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
package com.webull.openapi.trade.api.response;

import java.io.Serializable;

public class SimpleOrderResponse implements Serializable, OrderResponse {

    private static final long serialVersionUID = 6695412497588538012L;

    private String clientOrderId;

    @Override
    @Deprecated
    public Integer getCode() {
        return null;
    }

    @Override
    @Deprecated
    public String getMsg() {
        return null;
    }

    @Override
    @Deprecated
    public OrderClientId getData() {
        return null;
    }

    @Override
    @Deprecated
    public Boolean isSuccess() {
        return null;
    }

    @Override
    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    @Override
    public String toString() {
        return "SimpleOrderResponse{" +
                "clientOrderId='" + clientOrderId + '\'' +
                '}';
    }
}
