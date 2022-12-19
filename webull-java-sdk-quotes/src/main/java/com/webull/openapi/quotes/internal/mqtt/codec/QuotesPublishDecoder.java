package com.webull.openapi.quotes.internal.mqtt.codec;

import com.webull.openapi.quotes.domain.QuotesBasic;
import com.webull.openapi.quotes.subsribe.codec.Decoder;
import com.webull.openapi.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public interface QuotesPublishDecoder<T extends QuotesBasic> extends Decoder<ByteBuffer, QuotesPublish<T>> {
}
