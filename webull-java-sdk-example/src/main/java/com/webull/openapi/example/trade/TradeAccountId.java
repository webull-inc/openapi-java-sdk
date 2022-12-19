package com.webull.openapi.example.trade;

import com.webull.openapi.common.Region;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.http.TradeHttpApiService;
import com.webull.openapi.trade.api.response.Account;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.StringUtils;

import java.util.List;

public class TradeAccountId {

    private static final Logger logger = LoggerFactory.getLogger(TradeAccountId.class);

    public static void main(String[] args) {
        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Region.hk.name())
                    .build();
            TradeApiService apiService = new TradeHttpApiService(apiConfig);

            List<Account> accounts = apiService.getAccountList("");

            if (CollectionUtils.isNotEmpty(accounts)) {
                String accountId = accounts.get(0).getAccountId();
                if (StringUtils.isNotBlank(accountId)) {
                    logger.info("Account id: {}", accountId);
                    return;
                }
            }
            logger.info("Account id is empty.");
        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
