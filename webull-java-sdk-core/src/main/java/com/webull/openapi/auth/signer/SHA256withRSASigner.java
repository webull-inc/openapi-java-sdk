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

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SHA256withRSASigner implements Signer {

    private static final String ALGORITHM = "RSA";
    private static final String VERSION = "1.0";

    @Override
    public String getSign(String source, String secret) {
        try {
            Signature rsaSign = Signature.getInstance(SignAlgorithm.SHA256_WITH_RSA.getSignerName());
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            byte[] keySpec = DatatypeConverter.parseBase64Binary(secret);
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(keySpec));
            rsaSign.initSign(privateKey);
            rsaSign.update(source.getBytes(StandardCharsets.UTF_8));
            byte[] sign = rsaSign.sign();
            return DatatypeConverter.printBase64Binary(sign);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            throw new ClientException(ErrorCode.INVALID_CREDENTIAL, e);
        }
    }

    @Override
    public String signerName() {
        return SignAlgorithm.SHA256_WITH_RSA.getSignerName();
    }

    @Override
    public String signerVersion() {
        return VERSION;
    }
}
