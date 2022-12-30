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
package com.webull.openapi.quotes.api.message;

import java.util.Arrays;

public class ApiResponse {

    private int type;
    private String requestId;
    private int code;
    private String msg;
    private String path;
    private byte[] payload;

    public ApiResponse() {
    }

    public ApiResponse(int type, String requestId, int code, String msg, String path, byte[] payload) {
        this.type = type;
        this.requestId = requestId;
        this.code = code;
        this.msg = msg;
        this.path = path;
        this.payload = payload;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "type='" + type + '\'' +
                ", requestId='" + requestId + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", path='" + path + '\'' +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }
}
