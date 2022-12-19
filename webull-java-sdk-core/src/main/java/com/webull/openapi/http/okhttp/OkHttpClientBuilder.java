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
package com.webull.openapi.http.okhttp;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.http.certificate.IgnoreHostnameVerifier;
import com.webull.openapi.http.certificate.IgnoreX509TrustManager;
import com.webull.openapi.http.okhttp.interceptors.SocksProxyAuthInterceptor;
import okhttp3.Authenticator;
import okhttp3.ConnectionPool;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class OkHttpClientBuilder {

    private final OkHttpClient.Builder builder;

    public static OkHttpClientBuilder builder() {
        return new OkHttpClientBuilder();
    }

    private OkHttpClientBuilder() {
        this.builder = new OkHttpClient().newBuilder();
    }

    public OkHttpClientBuilder connectTimeout(long connectTimeout, TimeUnit timeUnit) {
        this.builder.connectTimeout(connectTimeout, timeUnit);
        return this;
    }

    public OkHttpClientBuilder readTimeout(long readTimeout, TimeUnit timeUnit) {
        this.builder.readTimeout(readTimeout, timeUnit);
        return this;
    }

    public OkHttpClientBuilder connectionPool(int maxIdleConnections) {
        ConnectionPool connectionPool = new ConnectionPool(maxIdleConnections, 10000L, TimeUnit.MILLISECONDS);
        this.builder.connectionPool(connectionPool);
        return this;
    }

    public OkHttpClientBuilder certificate(boolean ignoreSSL) {
        try {
            if (ignoreSSL) {
                X509TrustManager ignoreX509TrustManager = new IgnoreX509TrustManager();
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{ignoreX509TrustManager}, new SecureRandom());
                this.builder.sslSocketFactory(sslContext.getSocketFactory(), ignoreX509TrustManager).
                        hostnameVerifier(new IgnoreHostnameVerifier());
            }
            return this;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }

    public OkHttpClientBuilder proxy(Proxy.Type type, String proxyAddress) {
        try {
            URI url = new URI(proxyAddress);
            this.builder.proxy(new Proxy(type, new InetSocketAddress(url.getHost(), url.getPort())));
            return this;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }

    }

    public OkHttpClientBuilder proxyAuthenticator(Proxy.Type type, String proxyAddress) {
        try {
            if (Proxy.Type.HTTP == type) {
                URL proxyUrl = new URL(proxyAddress);
                String userInfo = proxyUrl.getUserInfo();
                if (null != userInfo) {
                    final String[] userMessage = userInfo.split(":");
                    final String credential = Credentials.basic(userMessage[0], userMessage[1]);
                    Authenticator authenticator = (route, response) -> response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                    this.builder.proxyAuthenticator(authenticator);
                }
            } else if (Proxy.Type.SOCKS == type) {
                URI proxyUrl = new URI(proxyAddress);
                String userInfo = proxyUrl.getUserInfo();
                if (null != userInfo) {
                    final String[] userMessage = userInfo.split(":");
                    this.builder.addInterceptor(new SocksProxyAuthInterceptor(userMessage[0], userMessage[1]));
                }
            }
            return this;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }

    public OkHttpClient build() {
        return this.builder.build();
    }
}
