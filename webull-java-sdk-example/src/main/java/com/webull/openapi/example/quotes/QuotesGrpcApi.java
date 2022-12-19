package com.webull.openapi.example.quotes;

import com.webull.openapi.common.Region;
import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.Timespan;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.domain.Bar;
import com.webull.openapi.quotes.domain.Instrument;
import com.webull.openapi.quotes.domain.Quote;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.domain.Tick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuotesGrpcApi {

    private static final Logger logger = LoggerFactory.getLogger(QuotesGrpcApi.class);

    public static void main(String[] args) {
        String symbol = "00700";

        try (QuotesApiClient quotesApiClient = QuotesApiClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Region.hk.name())
                .build()) {

            // get instruments
            Set<String> symbols = new HashSet<>();
            symbols.add(symbol);

            List<Instrument> instruments = quotesApiClient.getInstruments(symbols, Category.HK_STOCK.name());
            logger.info("Instruments: {}", instruments);

            // get bars
            List<Bar> bars = quotesApiClient.getBars(symbol, Category.HK_STOCK.name(), Timespan.D.name(), 10);
            logger.info("Bars: {}", bars);

            // get quote
            Quote quote = quotesApiClient.getQuote(symbol, Category.HK_STOCK.name());
            logger.info("Quote: {}", quote);

            // get snapshots
            List<Snapshot> snapshots = quotesApiClient.getSnapshots(symbols, Category.HK_STOCK.name());
            logger.info("Snapshots: {}", snapshots);

            // get ticks
            List<Tick> ticks = quotesApiClient.getTicks(symbol, Category.HK_STOCK.name(), 10);
            logger.info("Ticks: {}", ticks);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
