package com.webull.openapi.example.trade;

import com.webull.openapi.common.Region;
import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.ComboType;
import com.webull.openapi.common.dict.EntrustType;
import com.webull.openapi.common.dict.InstrumentSuperType;
import com.webull.openapi.common.dict.Markets;
import com.webull.openapi.common.dict.OptionStrategy;
import com.webull.openapi.common.dict.OptionType;
import com.webull.openapi.common.dict.OrderSide;
import com.webull.openapi.common.dict.OrderTIF;
import com.webull.openapi.common.dict.OrderType;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.trade.api.http.TradeHttpApiV2Service;
import com.webull.openapi.trade.api.request.v2.OptionOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrderItem;
import com.webull.openapi.trade.api.request.v2.OptionOrderItemLeg;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webull.openapi.common.Headers.CATEGORY_KEY;

public class TradeV2Api {
    private static final Logger logger = LoggerFactory.getLogger(TradeV2Api.class);

    public static void main(String[] args) {
        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Env.REGION_ID)
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
            // This is an optional feature; you can still make a request without setting it.
            Map<String,String> customHeadersMap = new HashMap<>();
            customHeadersMap.put(CATEGORY_KEY, Category.US_STOCK.name());
            apiService.addCustomHeaders(customHeadersMap);
            TradeOrderResponse tradePlaceOrderResponse = apiService.placeOrder(accountId, tradeOrder);
            apiService.removeCustomHeaders();
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




            // Options
            OptionOrderItemLeg optionOrderItemLeg = new OptionOrderItemLeg();
            optionOrderItemLeg.setSide(OrderSide.BUY.name());
            optionOrderItemLeg.setQuantity("1");
            optionOrderItemLeg.setSymbol("AAPL");
            optionOrderItemLeg.setStrikePrice("250");
            optionOrderItemLeg.setInitExpDate("2025-08-15");
            optionOrderItemLeg.setInstrumentType(InstrumentSuperType.OPTION.name());
            optionOrderItemLeg.setOptionType(OptionType.CALL.name());
            optionOrderItemLeg.setMarket(Markets.US.name());
            List<OptionOrderItemLeg> optionOrderItemLegList = new ArrayList<>();
            optionOrderItemLegList.add(optionOrderItemLeg);
            OptionOrderItem optionOrderItem = new OptionOrderItem();
            optionOrderItem.setClientOrderId(GUID.get());
            optionOrderItem.setComboType(ComboType.NORMAL.name());
            optionOrderItem.setOptionStrategy(OptionStrategy.SINGLE.name());
            optionOrderItem.setSide(OrderSide.BUY.name());
            optionOrderItem.setOrderType(OrderType.LIMIT.name());
            optionOrderItem.setTimeInForce(OrderTIF.GTC.name());
            optionOrderItem.setLimitPrice("2");
            optionOrderItem.setQuantity("1");
            optionOrderItem.setEntrustType(EntrustType.QTY.name());
            optionOrderItem.setOrders(optionOrderItemLegList);
            List<OptionOrderItem> optionOrderItemList = new ArrayList<>();
            optionOrderItemList.add(optionOrderItem);
            OptionOrder optionOrder = new OptionOrder();
            optionOrder.setNewOrders(optionOrderItemList);

            logger.info("previewOptionRequest: {}", optionOrder);
            PreviewOrderResponse previewOptionResponse = apiService.previewOption(accountId, optionOrder);
            logger.info("previewOptionResponse: {}", previewOptionResponse);

            logger.info("placeOptionRequest: {}", optionOrder);
            // This is an optional feature; you can still make a request without setting it.
            Map<String,String> optionCustomHeadersMap = new HashMap<>();
            optionCustomHeadersMap.put(CATEGORY_KEY, Category.US_OPTION.name());
            apiService.addCustomHeaders(optionCustomHeadersMap);
            TradeOrderResponse placeOptionResponse = apiService.placeOption(accountId, optionOrder);
            apiService.removeCustomHeaders();
            logger.info("placeOptionResponse: {}", placeOptionResponse);
            Thread.sleep(5000L);

            // This code is only applicable for single-leg options modification operations.
            OptionOrderItemLeg optionReplaceItemLeg = new OptionOrderItemLeg();
            optionReplaceItemLeg.setQuantity("2");
            optionReplaceItemLeg.setClientOrderId(optionOrderItem.getClientOrderId());
            List<OptionOrderItemLeg> optionReplaceItemLegList = new ArrayList<>();
            optionReplaceItemLegList.add(optionReplaceItemLeg);
            OptionOrderItem optionReplaceItem = new OptionOrderItem();
            optionReplaceItem.setClientOrderId(optionOrderItem.getClientOrderId());
            optionReplaceItem.setLimitPrice("3");
            optionReplaceItem.setQuantity("2");
            optionReplaceItem.setOrders(optionReplaceItemLegList);
            List<OptionOrderItem> optionReplaceItemList = new ArrayList<>();
            optionReplaceItemList.add(optionReplaceItem);
            OptionOrder optionReplace = new OptionOrder();
            optionReplace.setModifyOrders(optionReplaceItemList);

            logger.info("replaceOptionRequest: {}", optionReplace);
            TradeOrderResponse replaceOptionResponse = apiService.replaceOption(accountId, optionReplace);
            logger.info("replaceOptionResponse: {}", replaceOptionResponse);
            Thread.sleep(5000L);

            OptionOrder cancelTradeOption = new OptionOrder();
            cancelTradeOption.setClientOrderId(optionOrderItem.getClientOrderId());

            logger.info("cancelOptionRequest: {}", cancelTradeOption);
            TradeOrderResponse cancelOptionResponse = apiService.cancelOption(accountId, cancelTradeOption);
            logger.info("cancelOptionResponse: {}", cancelOptionResponse);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
