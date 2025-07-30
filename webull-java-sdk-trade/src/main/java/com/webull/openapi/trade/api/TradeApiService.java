/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.trade.api;

import com.webull.openapi.trade.api.request.StockOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrder;
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

import java.util.List;
import java.util.Map;

public interface TradeApiService {

    List<Account> getAccountList(String subscriptionId);

    AccountDetail getAccountDetail(String accountId);

    <T extends BalanceBase> T getAccountBalance(String accountId, String totalAssetCurrency);

    AccountPositions getAccountPositions(String accountId, Integer pageSize, String lastId);

    OrderResponse placeOrder(String accountId, StockOrder stockOrder);

    OrderResponse replaceOrder(String accountId, StockOrder stockOrder);

    OrderResponse cancelOrder(String accountId, String clientOrderId);

    <T extends Order> Orders<T> getDayOrders(String accountId, Integer pageSize, String lastClientOrderId);

    <T extends Order> Orders<T> getOpenedOrders(String accountId, Integer pageSize, String lastClientOrderId);

    <T extends Order> T getOrderDetails(String accountId, String clientOrderId);

    InstrumentInfo getTradeInstrument(String instrumentId);

    /**
     * Only for Webull JP
     */
    TradableInstruments getTradeableInstruments(String lastSecurityId, Integer pageSize);

    List<TradeCalendar> getTradeCalendar(String market, String start, String end);

    InstrumentInfo getSecurityInfo(String symbol, String market, String instrumentSuperType, String instrumentType, String strikePrice, String initExpDate);

    /**
     * This interface is exclusively available for Webull Hong Kong brokerage clients.
     * Currently, it does not support Webull Japan or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    PreviewOrderResponse previewOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is exclusively available for Webull Hong Kong brokerage clients.
     * Currently, it does not support Webull Japan or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is exclusively available for Webull Hong Kong brokerage clients.
     * Currently, it does not support Webull Japan or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse replaceOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is exclusively available for Webull Hong Kong brokerage clients.
     * Currently, it does not support Webull Japan or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse cancelOption(String accountId, OptionOrder optionOrder);

    /**
     * This is an optional feature; you can still make a request without setting it.
     * If set, you can specify certain headers to perform specific operations.
     * Note: If you set a header, call removeCustomHeaders to clean up the header after the request is completed.
     *
     * Currently supported header keys and functions:
     *      Key：category {@link com.webull.openapi.common.dict.Category}
     *      Function: Frequency limit rules, please refer to the document for details. currently only supports Hong Kong
     *
     * @param headersMap
     */
    void addCustomHeaders(Map<String, String> headersMap);

    /**
     * Clearing headers after the request is completed.
     */
    void removeCustomHeaders();
}
