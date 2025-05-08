/*
 * Copyright 2022 Webull Technologies Pte. Ltd.
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
package com.webull.openapi.trade.api.http;

import com.google.gson.reflect.TypeToken;
import com.webull.openapi.common.Region;
import com.webull.openapi.common.Versions;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.http.HttpApiClient;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.http.HttpRequest;
import com.webull.openapi.http.common.HttpMethod;
import com.webull.openapi.trade.api.TradeApiV2Service;
import com.webull.openapi.trade.api.request.v2.OptionOrder;
import com.webull.openapi.trade.api.request.v2.TradeOrder;
import com.webull.openapi.trade.api.response.TradeCalendar;
import com.webull.openapi.trade.api.response.v2.Account;
import com.webull.openapi.trade.api.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.api.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.api.response.v2.OrderHistory;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TradeHttpApiV2Service implements TradeApiV2Service {

    private static final String ACCOUNT_ID_ARG = "accountId";
    private static final String TRADE_ORDER_ARG = "tradeOrder";
    private static final String OPTION_ORDER_ARG = "optionOrder";
    private static final String NEW_ORDERS_ARG = "newOrders";
    private static final String MODIFY_ORDERS_ARG = "modifyOrders";
    private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";

    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String START_TIME_PARAM = "start_date";
    private static final String END_TIME_PARAM = "end_date";
    private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
    private static final String ACCOUNT_ID_PARAM = "account_id";
    private static final String CLIENT_ORDER_ID_PARAM = "client_order_id";
    private static final String MARKET_ARG = "market";
    private static final String START_ARG = "start";
    private static final String END_ARG = "end";
    private static final String MARKET_PARAM = MARKET_ARG;
    private static final String START_PARAM = START_ARG;
    private static final String END_PARAM = END_ARG;

    private final Region region;
    private final HttpApiClient apiClient;

    public TradeHttpApiV2Service(HttpApiConfig config) {
        this(new HttpApiClient(config));
    }

    public TradeHttpApiV2Service(HttpApiClient apiClient) {
        this.region = Region.of(apiClient.getConfig().getRegionId())
                .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER,
                        "Must set region id which defined in " + Region.class.getName() + " when using this service."));
        this.apiClient = apiClient;
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public List<Account> listAccount() {
        HttpRequest request = new HttpRequest("/openapi/account/list", Versions.V1, HttpMethod.GET);
        return apiClient.request(request).responseType(new TypeToken<List<Account>>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public AccountBalanceInfo balanceAccount(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/account/balance", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountBalanceInfo.class).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public List<AccountPositionsInfo> positionsAccount(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/account/positions", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        return apiClient.request(request).responseType(List.class).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/account/orders/preview", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(tradeOrder);
        return apiClient.request(request).responseType(new TypeToken<PreviewOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, tradeOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/place", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(tradeOrder);
        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notEmpty(MODIFY_ORDERS_ARG, tradeOrder.getModifyOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/replace", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(tradeOrder);
        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(TRADE_ORDER_ARG, tradeOrder);
        Assert.notBlank(CLIENT_ORDER_ID_ARG, tradeOrder.getClientOrderId());
        HttpRequest request = new HttpRequest("/openapi/account/orders/cancel", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(tradeOrder);
        return apiClient.request(request).responseType(new TypeToken<TradeOrderResponse>() {
        }.getType()).doAction();
    }

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    @Override
    public List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate, String lastClientOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/account/orders/history", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastClientOrderId)) {
            params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            params.put(START_TIME_PARAM, startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            params.put(END_TIME_PARAM, endDate);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();
    }


    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public OrderHistory getOrderDetails(String accountId, String clientOrderId) {
        Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
        HttpRequest request = new HttpRequest("/openapi/account/orders/detail", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        request.setQuery(params);
        return apiClient.request(request).responseType(OrderHistory.class).doAction();
    }

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public PreviewOrderResponse previewOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, optionOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/option/preview", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(optionOrder);
        return apiClient.request(request).responseType(PreviewOrderResponse.class).doAction();
    }

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, optionOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/option/place", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(optionOrder);
        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public TradeOrderResponse replaceOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(MODIFY_ORDERS_ARG, optionOrder.getModifyOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/option/replace", Versions.V1, HttpMethod.POST);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(optionOrder);
        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public TradeOrderResponse cancelOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notBlank(CLIENT_ORDER_ID_ARG, optionOrder.getClientOrderId());
        HttpRequest request = new HttpRequest("/openapi/account/orders/option/cancel", Versions.V1, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, optionOrder.getClientOrderId());
        request.setQuery(queryMap);
        request.setBody(params);
        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    @Override
    public List<TradeCalendar> getTradeCalendar(String market, String start, String end) {
        Assert.notBlank(Arrays.asList(MARKET_ARG, START_ARG, END_ARG), market, start, end);
        HttpRequest request = new HttpRequest("/openapi/trade/calendar", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(MARKET_PARAM, market);
        params.put(START_PARAM, start);
        params.put(END_PARAM, end);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<TradeCalendar>>() {}.getType()).doAction();
    }

}
