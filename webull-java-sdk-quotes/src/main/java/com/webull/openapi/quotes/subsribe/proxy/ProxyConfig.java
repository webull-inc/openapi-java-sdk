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
package com.webull.openapi.quotes.subsribe.proxy;

import com.webull.openapi.utils.Assert;

public class ProxyConfig {

    private final ProxyType proxyType;
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    ProxyConfig(ProxyType proxyType, String host, int port, String username, String password) {
        Assert.notNull("proxyType", proxyType);
        Assert.notBlank("host", host);
        Assert.inRange("port", port, 0, 65535);
        this.proxyType = proxyType;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static ProxyConfigBuilder builder() {
        return new ProxyConfigBuilder();
    }

    public ProxyType getProtocol() {
        return proxyType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
