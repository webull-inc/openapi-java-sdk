package com.webull.openapi.example.quotes;

import com.webull.openapi.common.CustomerType;
import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.EventType;
import com.webull.openapi.common.dict.Timespan;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.domain.*;
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

        Set<String> instrumentIds = new HashSet<>();
        instrumentIds.add("913303964");
        instrumentIds.add("913256135");

        Set<String> eventTypes = new HashSet<>();
        eventTypes.add(EventType.Reverse_Stock_Split.getCode());
        eventTypes.add(EventType.Stock_Split.getCode());

        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
//                .customerType(CustomerType.INSTITUTION)
//                .userId("<your_webull_user_id>")
                .regionId(Env.REGION_ID)
                .build();

        try (QuotesApiClient quotesApiClient = new HttpQuotesApiClient(apiConfig)) {
            // get bars
            List<Bar> bars = quotesApiClient.getBars("AAPL", Category.US_STOCK.name(), Timespan.D.name(), 10);
            logger.info("Bars: {}", bars);

            // get snapshots
            List<Snapshot> snapshots = quotesApiClient.getSnapshots(symbols, Category.US_STOCK.name());
            logger.info("Snapshots: {}", snapshots);

            // get instruments
            List<Instrument> instruments = quotesApiClient.getInstruments(symbols, Category.US_STOCK.name());
            logger.info("Instruments: {}", instruments);

            // get end of day market
            List<EodBars> eodBars = quotesApiClient.getEodBars(instrumentIds, "2023-01-01", 10);
            logger.info("Eod bars: {}", eodBars);

            // get corp action
            CorpActionRequest corpActionReq = new CorpActionRequest();
            corpActionReq.setEventTypes(eventTypes);
            corpActionReq.setInstrumentIds(instrumentIds);
            List<CorpAction> corpAction = quotesApiClient.getCorpAction(corpActionReq);
            logger.info("Corp action: {}", corpAction);
            
        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
