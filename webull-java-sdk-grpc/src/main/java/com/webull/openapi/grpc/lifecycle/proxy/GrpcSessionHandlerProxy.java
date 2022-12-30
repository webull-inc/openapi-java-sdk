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
package com.webull.openapi.grpc.lifecycle.proxy;

import com.webull.openapi.grpc.lifecycle.GrpcFailedContextAdapter;
import com.webull.openapi.grpc.lifecycle.GrpcSessionHandler;
import com.webull.openapi.grpc.lifecycle.GrpcSuccessContextAdapter;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;

public class GrpcSessionHandlerProxy<RespT> implements SubStreamObserver<RespT> {

    private final GrpcSessionHandler sessionHandler;

    public GrpcSessionHandlerProxy(GrpcSessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    @Override
    public void onReady() {
        this.sessionHandler.onSuccess(GrpcSuccessContextAdapter.of(null));
    }

    @Override
    public void onError(Throwable cause) {
        this.sessionHandler.onFailed(GrpcFailedContextAdapter.of(cause));
    }

    @Override
    public void onCompleted() {
        this.sessionHandler.onCompleted();
    }
}
