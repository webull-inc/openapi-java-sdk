package com.webull.openapi.example.trade;

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
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.http.TradeHttpApiService;
import com.webull.openapi.trade.api.request.StockOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrderItem;
import com.webull.openapi.trade.api.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.api.response.Account;
import com.webull.openapi.trade.api.response.AccountDetail;
import com.webull.openapi.trade.api.response.AccountPositions;
import com.webull.openapi.trade.api.response.BalanceBase;
import com.webull.openapi.trade.api.response.InstrumentInfo;
import com.webull.openapi.trade.api.response.Order;
import com.webull.openapi.trade.api.response.OrderResponse;
import com.webull.openapi.trade.api.response.Orders;
import com.webull.openapi.trade.api.response.TradableInstruments;
import com.webull.openapi.trade.api.response.TradeCalendar;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.GUID;
import com.webull.openapi.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TradeApi {

    private static final Logger logger = LoggerFactory.getLogger(TradeApi.class);

    public static void main(String[] args) {
        try {
            HttpApiConfig apiConfig = HttpApiConfig.builder()
                    .appKey(Env.APP_KEY)
                    .appSecret(Env.APP_SECRET)
                    .regionId(Env.REGION_ID)
                    .build();
            TradeApiService apiService = new TradeHttpApiService(apiConfig);

            // get account list
            List<Account> accounts = apiService.getAccountList("");
            logger.info("Accounts: {}", accounts);

            String accountId = null;
            if (CollectionUtils.isNotEmpty(accounts)) {
                accountId = accounts.get(0).getAccountId();
            }
            if (StringUtils.isBlank(accountId)) {
                logger.info("Account id is empty.");
                return;
            }

            // get account balance
            BalanceBase accountBalance = apiService.getAccountBalance(accountId, "");
            logger.info("Account balance: {}", accountBalance);

            AccountDetail accountDetail = apiService.getAccountDetail(accountId);
            logger.info("Account detail: {}", accountDetail);

            // get account positions
            AccountPositions accountPositions = apiService.getAccountPositions(accountId, 10, "");
            logger.info("Account positions: {}", accountPositions);

            // place order & replace order
            String clientOrderId = GUID.get();
            StockOrder stockOrder = new StockOrder();
            stockOrder.setClientOrderId(clientOrderId);
            stockOrder.setInstrumentId("913256135");
            stockOrder.setSide(OrderSide.BUY.name());
            stockOrder.setTif(OrderTIF.DAY.name());
            stockOrder.setOrderType(OrderType.MARKET.name());
            stockOrder.setQty("100");
            stockOrder.setExtendedHoursTrading(false);

            OrderResponse placeOrderResponse = apiService.placeOrder(accountId, stockOrder);
            logger.info("Place order: {}", placeOrderResponse);

            OrderResponse replaceOrderResponse = apiService.replaceOrder(accountId, stockOrder);
            logger.info("Replace order: {}", replaceOrderResponse);

            // cancel order
            OrderResponse cancelOrderResponse = apiService.cancelOrder(accountId, clientOrderId);
            logger.info("Cancel order: {}", cancelOrderResponse);

            // day orders
            Orders<? extends Order> dayOrders = apiService.getDayOrders(accountId, 10, "");
            logger.info("Day orders: {}", dayOrders);

            // opened orders
            Orders<? extends Order> openedOrders = apiService.getOpenedOrders(accountId, 10, "");
            logger.info("Opened orders: {}", openedOrders);

            // order detail
            Order orderDetail = apiService.getOrderDetails(accountId, clientOrderId);
            logger.info("Order detail: {}", orderDetail);

            // instrument info
            InstrumentInfo instrumentInfo = apiService.getTradeInstrument("913256135");
            logger.info("Instrument info: {}", instrumentInfo);

            // trade calendar
            List<TradeCalendar> tradeCalendars = apiService.getTradeCalendar(Markets.US.name(), "2023-01-01", "2023-01-10");
            logger.info("Trade calendars: {}", tradeCalendars);

            // security info
            InstrumentInfo securityInfo = apiService.getSecurityInfo("SPX", Markets.US.name(), InstrumentSuperType.OPTION.name(), "CALL_OPTION", "3400", "2024-12-20" );
            logger.info("Security info: {}", securityInfo);

            // tradeable instruments
            TradableInstruments tradeableInstruments = apiService.getTradeableInstruments("", 10);
            logger.info("Tradeable instruments info: {}", tradeableInstruments);



            // Options
            // For option order inquiries, please use the V2 query interface: TradeHttpApiV2Service.getOrderDetails(accountId, clientOrderId).
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
            TradeOrderResponse placeOptionResponse = apiService.placeOption(accountId, optionOrder);
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
