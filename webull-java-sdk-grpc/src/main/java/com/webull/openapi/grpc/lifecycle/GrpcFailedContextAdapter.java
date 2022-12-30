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
package com.webull.openapi.grpc.lifecycle;

import com.webull.openapi.grpc.exception.UserCancelledException;
import com.webull.openapi.utils.ExceptionUtils;
import io.grpc.Status;

public class GrpcFailedContextAdapter implements GrpcFailedContext {

    protected final boolean userCancelled;
    protected final int statusCode;
    protected final Throwable cause;

    protected GrpcFailedContextAdapter(Throwable cause) {
        this.cause = cause;
        this.statusCode = Status.fromThrowable(cause).getCode().value();
        Throwable rootCause = ExceptionUtils.getRootCause(cause);
        this.userCancelled = (rootCause instanceof UserCancelledException);
    }

    public static GrpcFailedContextAdapter of(Throwable cause) {
        return new GrpcFailedContextAdapter(cause);
    }

    @Override
    public boolean userCancelled() {
        return userCancelled;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
