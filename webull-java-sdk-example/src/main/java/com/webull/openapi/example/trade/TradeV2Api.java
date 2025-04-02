package com.webull.openapi.example.trade;

import com.webull.openapi.common.CustomerType;
import com.webull.openapi.common.Region;
import com.webull.openapi.common.dict.AccountTaxType;
import com.webull.openapi.common.dict.EntrustType;
import com.webull.openapi.common.dict.InstrumentSuperType;
import com.webull.openapi.common.dict.OrderSide;
import com.webull.openapi.common.dict.OrderTIF;
import com.webull.openapi.common.dict.OrderType;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.http.RuntimeOptions;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.http.TradeHttpApiV2Service;
import com.webull.openapi.trade.api.request.v2.NoPartyId;
import com.webull.openapi.trade.api.request.v2.TradeOrder;
import com.webull.openapi.trade.api.request.v2.TradeOrderItem;
import com.webull.openapi.trade.api.response.v2.Account;
import com.webull.openapi.trade.api.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.api.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.api.response.v2.OrderHistory;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.GUID;

import java.util.ArrayList;
import java.util.List;

public class TradeV2Api {
    private static final Logger logger = LoggerFactory.getLogger(TradeV2Api.class);

    public static void main(String[] args) {
        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Env.REGION_ID)
//                    .customerType(CustomerType.INSTITUTION)
                    .build();
            TradeHttpApiV2Service apiService = new TradeHttpApiV2Service(apiConfig);

            // get account list
            List<Account> accounts = apiService.listAccount();
            logger.info("Accounts: {}", accounts);

            String accountId = null;
            if (CollectionUtils.isNotEmpty(accounts)) {
                accountId = accounts.get(0).getAccountId();
            }

            AccountBalanceInfo accountBalanceInfo = apiService.balanceAccount(accountId);
            logger.info("balanceBase: {}", accountBalanceInfo);

            List<AccountPositionsInfo> accountPositionsInfos = apiService.positionsAccount(accountId);
            logger.info("accountPositions: {}", accountPositionsInfos);

            TradeOrder tradeOrder = new TradeOrder();
            List<TradeOrderItem> newOrders = new ArrayList<>();
            TradeOrderItem placeOne = new TradeOrderItem();
            newOrders.add(placeOne);
            placeOne.setSymbol("AAPL");
            placeOne.setInstrumentType(InstrumentSuperType.EQUITY.name());
            placeOne.setMarket(Region.us.name().toUpperCase());
            placeOne.setOrderType(OrderType.MARKET.name());
            placeOne.setQuantity("100");
            placeOne.setLimitPrice("400");
            placeOne.setTotalCashAmount("100");
            placeOne.setSupportTradingSession("N");
            placeOne.setSide(OrderSide.BUY.name());
            placeOne.setTimeInForce(OrderTIF.DAY.name());
            placeOne.setEntrustType(EntrustType.QTY.name());
//            placeOne.setSenderSubId("123456");
//            List<NoPartyId> noPartyIds = new ArrayList<>();
//            NoPartyId partyId = new NoPartyId();
//            partyId.setPartyId("BNG144.666555");
//            partyId.setPartyIdSource("D");
//            partyId.setPartyRole("3");
//            noPartyIds.add(partyId);
//            placeOne.setNoPartyIds(noPartyIds);

            // only jp need input accountTaxType
            // placeOne.setAccountTaxType(AccountTaxType.GENERAL.name());
            tradeOrder.setNewOrders(newOrders);

            PreviewOrderResponse previewOrderResponse = apiService.previewOrder(accountId, tradeOrder);
            logger.info("previewOrderResponse: {}", previewOrderResponse);
            String clientOrderId = GUID.get();
            placeOne.setClientOrderId(clientOrderId);
            TradeOrderResponse tradePlaceOrderResponse = apiService.placeOrder(accountId, tradeOrder);
            logger.info("tradePlaceOrderResponse: {}", tradePlaceOrderResponse);
            Thread.sleep(1000L);
            TradeOrder modifyTradeOrder = new TradeOrder();
            List<TradeOrderItem> modifyOrders = new ArrayList<>();
            TradeOrderItem modifyOne = new TradeOrderItem();
            modifyOne.setClientOrderId(tradePlaceOrderResponse.getClientOrderId());
            modifyOne.setLimitPrice("45");
            modifyOne.setQuantity("100");
            modifyOrders.add(modifyOne);
            modifyTradeOrder.setModifyOrders(modifyOrders);
            TradeOrderResponse tradeReplaceOrderResponse = apiService.replaceOrder(accountId, modifyTradeOrder);
            logger.info("tradeReplaceOrderResponse: {}", tradeReplaceOrderResponse);
            Thread.sleep(1000L);
            TradeOrder cancelTradeOrder = new TradeOrder();
            cancelTradeOrder.setClientOrderId(tradePlaceOrderResponse.getClientOrderId());
            TradeOrderResponse tradeCancelOrderResponse = apiService.cancelOrder(accountId, cancelTradeOrder);
            logger.info("tradeCancelOrderResponse: {}", tradeCancelOrderResponse);

            List<OrderHistory> tradeOrderItems = apiService.listOrders(accountId, 10, "2024-09-25", null, null);
            logger.info("tradeOrderItems: {}", tradeOrderItems);

            // Replace with the client_order_id to be queried.
            OrderHistory orderDetailResponse = apiService.getOrderDetails(accountId, clientOrderId);
            logger.info("orderDetailResponse: {}", orderDetailResponse);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
