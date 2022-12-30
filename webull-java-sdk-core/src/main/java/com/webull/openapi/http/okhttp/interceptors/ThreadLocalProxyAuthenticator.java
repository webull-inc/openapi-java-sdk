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
package com.webull.openapi.http.okhttp.interceptors;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class ThreadLocalProxyAuthenticator extends Authenticator {
    private final ThreadLocal<PasswordAuthentication> credentials = new ThreadLocal<>();

    private ThreadLocalProxyAuthenticator(){}

    private static class SingletonHolder {
        private static final ThreadLocalProxyAuthenticator instance = new ThreadLocalProxyAuthenticator();
    }

    public static ThreadLocalProxyAuthenticator getInstance() {
        return SingletonHolder.instance;
    }

    public void setCredentials(String user, String password) {
        ThreadLocalProxyAuthenticator authenticator = ThreadLocalProxyAuthenticator.getInstance();
        Authenticator.setDefault(authenticator);
        authenticator.credentials.set(new PasswordAuthentication(user, password.toCharArray()));
    }

    public static void clearCredentials() {
        ThreadLocalProxyAuthenticator authenticator = ThreadLocalProxyAuthenticator.getInstance();
        Authenticator.setDefault(authenticator);
        authenticator.credentials.remove();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return credentials.get();
    }
}
