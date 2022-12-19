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
package com.webull.openapi.utils;

import com.webull.openapi.execption.ClientException;

import java.security.MessageDigest;

public final class MD5Utils {

    private static final String MD5 = "MD5";

    private MD5Utils() {
    }

    public static String md5(byte[] buff) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest(buff);
            return ByteUtils.bytesToHex(messageDigest);
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }
}
