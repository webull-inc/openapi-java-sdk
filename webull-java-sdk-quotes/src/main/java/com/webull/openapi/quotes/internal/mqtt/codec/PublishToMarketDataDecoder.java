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

import com.webull.openapi.common.dict.SubscribeType;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.domain.QuotesBasic;
import com.webull.openapi.quotes.internal.mqtt.message.MqttPublish;
import com.webull.openapi.quotes.subsribe.codec.AbstractInboundDecoder;
import com.webull.openapi.quotes.subsribe.message.MarketData;
import com.webull.openapi.quotes.subsribe.message.Metadata;
import com.webull.openapi.quotes.subsribe.message.QuotesPublish;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class PublishToMarketDataDecoder extends AbstractInboundDecoder<MqttPublish, MarketData> {

    private static final Logger logger = LoggerFactory.getLogger(PublishToMarketDataDecoder.class);

    private static final String SPLITER = "-";

    private final Map<SubscribeType, QuotesPublishDecoder<? extends QuotesBasic>> delegates = new EnumMap<>(SubscribeType.class);

    public PublishToMarketDataDecoder() {
        delegates.put(SubscribeType.QUOTE, new QuoteDecoder());
        delegates.put(SubscribeType.SNAPSHOT, new SnapshotDecoder());
        delegates.put(SubscribeType.TICK, new TickDecoder());
    }

    @Override
    public MarketData decode(MqttPublish in) {
        String topic = in.getTopic();
        String[] metaArray = topic.split(SPLITER);
        if (metaArray.length < 3) {
            logger.warn("Unrecognized topic={}.", topic);
            return null;
        }
        String instrumentId = metaArray[0];
        SubscribeType subscribeType;
        int interval;
        try {
            Optional<SubscribeType> subscribeTypeOpt = SubscribeType.fromCode(Integer.parseInt(metaArray[1]));
            if (!subscribeTypeOpt.isPresent()) {
                logger.warn("Unrecognized data type={}.", metaArray[1]);
                return null;
            }
            subscribeType = subscribeTypeOpt.get();
            interval = Integer.parseInt(metaArray[2]);
        } catch (NumberFormatException e) {
            logger.error("Unrecognized topic={}.", topic, e);
            return null;
        }
        Metadata metadata = new Metadata(instrumentId, subscribeType, interval);
        QuotesPublishDecoder<?> delegate = delegates.get(subscribeType);
        QuotesPublish<?> delegateOut = delegate.decode(in.getPayload());
        return new MarketData(metadata, delegateOut);
    }
}
