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
package com.webull.openapi.quotes.internal.mqtt.codec;

import com.google.protobuf.InvalidProtocolBufferException;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.subsribe.exception.DecoderException;
import com.webull.openapi.quotes.subsribe.message.QuotesPublish;

import java.nio.ByteBuffer;

public class SnapshotDecoder implements QuotesPublishDecoder<Snapshot> {

    @Override
    public QuotesPublish<Snapshot> decode(ByteBuffer in) {
        try {
            Quotes.Snapshot from = Quotes.Snapshot.parseFrom(in);
            Snapshot snapshot = new Snapshot();
            snapshot.setSymbol(from.getBasic().getSymbol());
            snapshot.setInstrumentId(from.getBasic().getInstrumentId());
            snapshot.setTradeTime(from.getTradeTime());
            snapshot.setPrice(from.getPrice());
            snapshot.setOpen(from.getOpen());
            snapshot.setHigh(from.getHigh());
            snapshot.setLow(from.getLow());
            snapshot.setPreClose(from.getPreClose());
            snapshot.setVolume(from.getVolume());
            snapshot.setChange(from.getChange());
            snapshot.setChangeRatio(from.getChangeRatio());
            return new QuotesPublish<>(from.getBasic().getTimestamp(), snapshot);
        } catch (InvalidProtocolBufferException e) {
            throw new DecoderException("Decode snapshot data error", e);
        }
    }
}
