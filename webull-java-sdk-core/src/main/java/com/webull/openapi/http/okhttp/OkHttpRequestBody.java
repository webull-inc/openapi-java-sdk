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

import com.webull.openapi.common.Headers;
import com.webull.openapi.http.HttpRequest;
import com.webull.openapi.utils.StringUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OkHttpRequestBody extends RequestBody {

    private static final String DEFAULT_CONTENT_TYPE = "application/json; charset=UTF-8;";

    private final InputStream inputStream;
    private final String contentType;

    public OkHttpRequestBody(HttpRequest request) {
        this.inputStream = new ByteArrayInputStream(request.getBodyString().getBytes(StandardCharsets.UTF_8));
        this.contentType = request.getHeaders().get(Headers.NATIVE_CONTENT_TYPE);
    }


    @Override
    public MediaType contentType() {
        MediaType type;
        if (StringUtils.isEmpty(contentType)) {
            if (null == inputStream) {
                return null;
            }
            type = MediaType.parse(DEFAULT_CONTENT_TYPE);
            return type;
        }
        return MediaType.parse(contentType);
    }

    @Override
    public long contentLength() throws IOException {
        if (null != inputStream && inputStream.available() > 0) {
            return inputStream.available();
        }
        return super.contentLength();
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if (null == inputStream) {
            return;
        }
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            bufferedSink.write(buffer, 0, bytesRead);
        }
    }
}
