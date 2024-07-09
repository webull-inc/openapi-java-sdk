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

public class ComboOrderResponse implements Serializable, OrderResponse {

    private static final long serialVersionUID = 4370138561577804279L;

    private Integer code;

    private String msg;

    private OrderClientId data;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public OrderClientId getData() {
        return data;
    }

    @Override
    public Boolean isSuccess() {
        return code != null && code == 200;
    }

    @Override
    @Deprecated
    public String getClientOrderId() {
        return null;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(OrderClientId data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ComboOrderResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
