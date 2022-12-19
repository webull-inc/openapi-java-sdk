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
package com.webull.openapi.grpc.lifecycle.proxy;

import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.lifecycle.GrpcSessionHandler;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;

import java.util.LinkedList;
import java.util.List;

public class DefaultHandlerProxyFactory<RespT> implements HandlerProxyFactory<RespT> {

    @Override
    public List<SubStreamObserver<RespT>> create(GrpcHandler handler) {
        List<SubStreamObserver<RespT>> result = new LinkedList<>();
        if (handler instanceof GrpcSessionHandler) {
            result.add(new GrpcSessionHandlerProxy<>((GrpcSessionHandler) handler));
        }
        return result;
    }
}
