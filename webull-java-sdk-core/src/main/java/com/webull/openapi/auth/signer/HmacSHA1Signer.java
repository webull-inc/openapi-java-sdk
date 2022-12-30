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
package com.webull.openapi.auth.signer;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSHA1Signer implements Signer {

    private static final String ALGORITHM = "HmacSHA1";
    private static final String VERSION = "1.0";

    @Override
    public String getSign(String source, String secret) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM));
            byte[] signData = mac.doFinal(source.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printBase64Binary(signData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ClientException(ErrorCode.INVALID_CREDENTIAL, e);
        }
    }

    @Override
    public String signerName() {
        return SignAlgorithm.HMAC_SHA1.getSignerName();
    }

    @Override
    public String signerVersion() {
        return VERSION;
    }
}
