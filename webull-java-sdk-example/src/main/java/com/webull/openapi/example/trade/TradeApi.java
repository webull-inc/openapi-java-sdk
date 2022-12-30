package com.webull.openapi.example.trade;

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
import com.webull.openapi.trade.api.response.Account;
import com.webull.openapi.trade.api.response.AccountBalance;
import com.webull.openapi.trade.api.response.AccountDetail;
import com.webull.openapi.trade.api.response.AccountPositions;
import com.webull.openapi.trade.api.response.InstrumentInfo;
import com.webull.openapi.trade.api.response.Order;
import com.webull.openapi.trade.api.response.OrderResponse;
import com.webull.openapi.trade.api.response.Orders;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.GUID;
import com.webull.openapi.utils.StringUtils;

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
            AccountBalance accountBalance = apiService.getAccountBalance(accountId, "");
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

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }
}
