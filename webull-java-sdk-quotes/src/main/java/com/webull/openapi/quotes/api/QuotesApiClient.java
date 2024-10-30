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
package com.webull.openapi.quotes.api;

import com.webull.openapi.quotes.domain.Bar;
import com.webull.openapi.quotes.domain.Instrument;
import com.webull.openapi.quotes.domain.Quote;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.domain.Tick;
import com.webull.openapi.quotes.domain.EodBars;
import com.webull.openapi.quotes.domain.CorpAction;
import com.webull.openapi.quotes.domain.CorpActionRequest;
import com.webull.openapi.quotes.internal.grpc.GrpcQuotesApiClientBuilder;

import java.io.Closeable;
import java.util.List;
import java.util.Set;

public interface QuotesApiClient extends Closeable {

    static QuotesApiClientBuilder builder() {
        return new GrpcQuotesApiClientBuilder();
    }

    List<Instrument> getInstruments(Set<String> symbols, String category);

    default List<Bar> getBars(String symbol, String category, String timespan) {
        return getBars(symbol, category, timespan, 200);
    }

    List<Bar> getBars(String symbol, String category, String timespan, int count);

    /**
     * Only for Webull JP
     */
    List<EodBars> getEodBars(Set<String> instrumentIds, String date, Integer count);

    /**
     * Only for Webull JP
     */
    List<CorpAction> getCorpAction(CorpActionRequest action);

    Quote getQuote(String symbol, String category);

    List<Snapshot> getSnapshots(Set<String> symbols, String category);

    default List<Tick> getTicks(String symbol, String category) {
        return getTicks(symbol, category, 30);
    }

    List<Tick> getTicks(String symbol, String category, int count);

    String getToken();

    void subscribe(String token, Set<String> symbols, String category, Set<String> subTypes);

    void unsubscribe(String token, Set<String> symbols, String category, Set<String> subTypes, Boolean unsubscribeAll);

    @Override
    default void close() {
        // cover IOException
    }
}
