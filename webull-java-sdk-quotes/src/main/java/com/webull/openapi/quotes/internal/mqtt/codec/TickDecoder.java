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
package com.webull.openapi.quotes.internal.mqtt.codec;

import com.google.protobuf.InvalidProtocolBufferException;
import com.webull.openapi.quotes.domain.Tick;
import com.webull.openapi.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public class TickDecoder implements QuotesPublishDecoder<Tick> {

    @Override
    public QuotesPublish<Tick> decode(ByteBuffer in) throws DecoderException {
        try {
            Quotes.Tick from = Quotes.Tick.parseFrom(in);
            Tick tick = new Tick();
            tick.setSymbol(from.getBasic().getSymbol());
            tick.setInstrumentId(from.getBasic().getInstrumentId());
            tick.setTime(from.getTime());
            tick.setPrice(from.getPrice());
            tick.setVolume(from.getVolume());
            tick.setSide(from.getSide());
            return new QuotesPublish<>(from.getBasic().getTimestamp(), tick);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode tick data error", e);
        }
    }
}
