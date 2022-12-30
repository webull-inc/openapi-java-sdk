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
package com.webull.openapi.http.okhttp;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.http.RuntimeOptions;
import com.webull.openapi.http.common.ProxyType;
import okhttp3.OkHttpClient;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class OkHttpClientPool {

    private OkHttpClientPool() {
    }

    protected static final ConcurrentHashMap<String, OkHttpClient> clients = new ConcurrentHashMap<>();

    public static OkHttpClient get(URL url, RuntimeOptions options) throws MalformedURLException {
        if (options.getProxyType().isPresent()) {
            url = new URL(options.getProxyAddress());
        }
        String key = getClientKey(url);
        return clients.computeIfAbsent(key, ignore -> createClient(options));
    }

    public static OkHttpClient createClient(RuntimeOptions options)  {
        Optional<Proxy.Type> proxyType = options.getProxyType().map(type -> {
            if (ProxyType.HTTP == type || ProxyType.HTTPS == type) {
                return Proxy.Type.HTTP;
            } else if (ProxyType.SOCKS5 == type) {
                return Proxy.Type.SOCKS;
            }
            throw new ClientException(ErrorCode.INVALID_PARAMETER, "Unrecognized proxy type");
        });

        OkHttpClientBuilder builder = OkHttpClientBuilder.builder()
                .connectTimeout(options.getConnectTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS)
                .readTimeout(options.getReadTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS)
                .connectionPool(options.getMaxIdleConn())
                .certificate(options.getIgnoreSSL());

        if (proxyType.isPresent()) {
            String proxyAddress = options.getProxyAddress();
            builder = builder.proxy(proxyType.get(), proxyAddress).proxyAuthenticator(proxyType.get(), proxyAddress);
        }
        return builder.build();
    }

    private static String getClientKey(URL url) {
        return String.format("%s:%d", url.getHost(), url.getPort());
    }
}
