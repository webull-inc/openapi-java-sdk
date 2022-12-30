package com.webull.openapi.example.advanced;

import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.retry.backoff.ExponentialBackoffStrategy;
import com.webull.openapi.trade.events.subscribe.EventClient;

public class AdvancedTradeEventsRetry {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedTradeEventsRetry.class);

    public static void main(String[] args) {
        try (EventClient client = EventClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                // Retry setting
                .reconnectBy(new ExponentialBackoffStrategy())

                .build()) {

            // your code...

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
