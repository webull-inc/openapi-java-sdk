package com.webull.openapi.example.quotes;

import com.google.api.client.util.Lists;
import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.Timespan;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.domain.Bar;
import com.webull.openapi.quotes.domain.BatchBarResponse;
import com.webull.openapi.quotes.domain.Instrument;
import com.webull.openapi.quotes.domain.Quote;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.domain.Tick;
import com.webull.openapi.serialize.JsonSerializer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuotesGrpcApi {

    private static final Logger logger = LoggerFactory.getLogger(QuotesGrpcApi.class);

    public static void main(String[] args) {
        String symbol = "AAPL";

        try (QuotesApiClient quotesApiClient = QuotesApiClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
//                .userId("<your_webull_user_id>")
                .regionId(Env.REGION_ID)
                .build()) {
            // get instruments
            Set<String> symbols = new HashSet<>();
            symbols.add("AAPL");
            symbols.add("TSLA");

            List<Instrument> instruments = quotesApiClient.getInstruments(symbols, Category.US_STOCK.name());
            logger.info("Instruments: {}", JsonSerializer.toJson(instruments));

            // get bars
            List<Bar> bars = quotesApiClient.getBars(symbol, Category.US_STOCK.name(), Timespan.D.name(), 10);
            logger.info("Bars: {}", JsonSerializer.toJson(bars));

            // get bars with symbols
            BatchBarResponse batchBars = quotesApiClient.getBatchBars(Lists.newArrayList(symbols), Category.US_STOCK.name(), Timespan.M1.name(), 2);
            logger.info("Batch bars: {}", JsonSerializer.toJson(batchBars));

            // get quote
            Quote quote = quotesApiClient.getQuote(symbol, Category.US_STOCK.name());
            logger.info("Quote: {}", JsonSerializer.toJson(quote));

            // get snapshots
            List<Snapshot> snapshots = quotesApiClient.getSnapshots(symbols, Category.US_STOCK.name());
            logger.info("Snapshots: {}", JsonSerializer.toJson(snapshots));

            // get ticks
            List<Tick> ticks = quotesApiClient.getTicks(symbol, Category.US_STOCK.name(), 10);
            logger.info("Ticks: {}", JsonSerializer.toJson(ticks));

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
