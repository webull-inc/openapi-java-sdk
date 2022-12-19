package com.webull.openapi.example.advanced;

import com.webull.openapi.common.Region;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.subsribe.QuotesSubsClient;
import com.webull.openapi.quotes.subsribe.proxy.ProxyConfig;
import com.webull.openapi.quotes.subsribe.proxy.ProxyType;
import com.webull.openapi.retry.backoff.ExponentialBackoffStrategy;

public class AdvancedQuotesSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedQuotesSubscribe.class);

    public static void main(String[] args) {
        try (QuotesSubsClient client = QuotesSubsClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Region.hk.name())

                // Retry setting
                .reconnectBy(new ExponentialBackoffStrategy())

                // Set the connection timeout to 10 seconds and the read timeout to 60 seconds.
                .connectTimeout(10000)
                .readTimeout(60000)

                // Set socks5 proxy to 127.0.0.1:9080
                .proxy(ProxyConfig.builder().protocol(ProxyType.SOCKS5).host("127.0.0.1").port(9080).build())

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
