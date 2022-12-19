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
package com.webull.openapi.http;

import com.webull.openapi.http.common.ProxyType;
import com.webull.openapi.utils.Assert;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RuntimeOptions {

    private static final RuntimeOptions ROOT_DEFAULT = new RuntimeOptions(true)
            .connectTimeout(5000L, TimeUnit.MILLISECONDS)
            .readTimeout(10000L, TimeUnit.MILLISECONDS)
            .maxIdleConn(5)
            .ignoreSSL(false);

    private RuntimeOptions parent;

    private Long connectTimeoutMillis;
    private Long readTimeoutMillis;
    private Integer maxIdleConn;
    private Boolean ignoreSSL;
    private ProxyType proxyType;
    private String proxyAddress;

    public RuntimeOptions() {
        this(false);
    }

    private RuntimeOptions(boolean isRoot) {
        this.parent = isRoot ? null : ROOT_DEFAULT;
    }

    RuntimeOptions parent(RuntimeOptions parent) {
        Assert.notNull("parent", parent);
        this.parent = parent;
        return this;
    }

    public RuntimeOptions connectTimeout(long connectTimeout, TimeUnit timeUnit) {
        Assert.nonnegative("connectTimeout", connectTimeout);
        Assert.notNull("timeUnit", timeUnit);
        this.connectTimeoutMillis = timeUnit.toMillis(connectTimeout);
        return this;
    }

    public RuntimeOptions readTimeout(long readTimeout, TimeUnit timeUnit) {
        Assert.nonnegative("readTimeout", readTimeout);
        Assert.notNull("timeUnit", timeUnit);
        this.readTimeoutMillis = timeUnit.toMillis(readTimeout);
        return this;
    }

    public RuntimeOptions maxIdleConn(int maxIdleConn) {
        Assert.nonnegative("maxIdleConn", maxIdleConn);
        this.maxIdleConn = maxIdleConn;
        return this;
    }

    public RuntimeOptions ignoreSSL(boolean ignoreSSL) {
        this.ignoreSSL = ignoreSSL;
        return this;
    }

    public RuntimeOptions proxy(ProxyType proxyType, String proxyAddress) {
        Assert.notNull("proxyType", proxyType);
        Assert.notBlank("proxyAddress", proxyAddress);
        this.proxyType = proxyType;
        this.proxyAddress = proxyAddress;
        return this;
    }

    public long getConnectTimeout(TimeUnit timeUnit) {
        long timeout = connectTimeoutMillis != null ? connectTimeoutMillis : parent.getConnectTimeout(timeUnit);
        return timeUnit.convert(timeout, TimeUnit.MILLISECONDS);
    }

    public long getReadTimeout(TimeUnit timeUnit) {
        long timeout = readTimeoutMillis != null ? readTimeoutMillis : parent.getReadTimeout(timeUnit);
        return timeUnit.convert(timeout, TimeUnit.MILLISECONDS);
    }

    public int getMaxIdleConn() {
        return maxIdleConn != null ? maxIdleConn : parent.getMaxIdleConn();
    }

    public boolean getIgnoreSSL() {
        return ignoreSSL != null ? ignoreSSL : parent.getIgnoreSSL();
    }

    public Optional<ProxyType> getProxyType() {
        return proxyType != null || parent == null ? Optional.ofNullable(proxyType) : parent.getProxyType();
    }

    public String getProxyAddress() {
        return proxyAddress != null || parent == null ? proxyAddress : parent.getProxyAddress();
    }

    @Override
    public String toString() {
        return "RuntimeOptions{" +
                "parent=" + parent +
                ", connectTimeoutMillis=" + connectTimeoutMillis +
                ", readTimeoutMillis=" + readTimeoutMillis +
                ", maxIdleConn=" + maxIdleConn +
                ", ignoreSSL=" + ignoreSSL +
                ", proxyType=" + proxyType +
                ", proxyAddress='" + proxyAddress + '\'' +
                '}';
    }
}
