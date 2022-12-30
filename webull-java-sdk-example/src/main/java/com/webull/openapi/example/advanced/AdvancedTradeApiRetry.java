package com.webull.openapi.example.advanced;

import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.http.RuntimeOptions;
import com.webull.openapi.http.common.ProxyType;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.http.TradeHttpApiService;

import java.util.concurrent.TimeUnit;

public class AdvancedTradeApiRetry {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedTradeApiRetry.class);

    public static void main(String[] args) {
        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Env.REGION_ID)

                    // Set the maximum number of retries to 3
                    .autoRetry(true)
                    .maxRetryNum(3)

                    // Set the connection timeout to 5 seconds and the read timeout to 10 seconds.
                    .runtimeOptions(new RuntimeOptions().connectTimeout(5, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS))

                    // Set the proxy for HTTPS to 127.0.0.1:8888; Set ignoreSSL to true,
                    // which means that the certificate provided by the proxy will not be verified
                    .runtimeOptions(new RuntimeOptions().proxy(ProxyType.HTTPS, "https://127.0.0.1:8888").ignoreSSL(true))

                    .build();
            TradeApiService apiService = new TradeHttpApiService(apiConfig);

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
