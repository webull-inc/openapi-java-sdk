package com.webull.openapi.example.quotes;

import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.Timespan;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.domain.Bar;
import com.webull.openapi.quotes.domain.Instrument;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.internal.http.HttpQuotesApiClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuotesHttpApi {

    private static final Logger logger = LoggerFactory.getLogger(QuotesHttpApi.class);

    public static void main(String[] args) {
        Set<String> symbols = new HashSet<>();
        symbols.add("AAPL");
        symbols.add("TSLA");

        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                .build();

        try (QuotesApiClient quotesApiClient = new HttpQuotesApiClient(apiConfig)) {
            // get bars
            List<Bar> bars = quotesApiClient.getBars("AAPL", Category.US_STOCK.name(), Timespan.D.name(), 200);
            logger.info("Bars: {}", bars);

            // get snapshots
            List<Snapshot> snapshots = quotesApiClient.getSnapshots(symbols, Category.US_STOCK.name());
            logger.info("Snapshots: {}", snapshots);

            // get instruments
            List<Instrument> instruments = quotesApiClient.getInstruments(symbols, Category.US_STOCK.name());
            logger.info("Instruments: {}", instruments);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
