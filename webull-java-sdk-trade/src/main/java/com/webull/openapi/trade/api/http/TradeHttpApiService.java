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
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.request.StockOrder;
import com.webull.openapi.trade.api.response.Account;
import com.webull.openapi.trade.api.response.AccountBalance;
import com.webull.openapi.trade.api.response.AccountDetail;
import com.webull.openapi.trade.api.response.AccountPositions;
import com.webull.openapi.trade.api.response.ComboOrder;
import com.webull.openapi.trade.api.response.InstrumentInfo;
import com.webull.openapi.trade.api.response.Order;
import com.webull.openapi.trade.api.response.OrderResponse;
import com.webull.openapi.trade.api.response.Orders;
import com.webull.openapi.trade.api.response.SimpleOrder;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeHttpApiService implements TradeApiService {

    private static final String ACCOUNT_ID_ARG = "accountId";
    private static final String STOCK_ORDER_ARG = "stockOrder";
    private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";
    private static final String INSTRUMENT_ID_ARG = "instrumentId";

    private static final String ACCOUNT_ID_PARAM = "account_id";
    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String SUBSCRIPTION_ID_PARAM = "subscription_id";
    private static final String TOTAL_ASSET_CURRENCY_PARAM = "total_asset_currency";
    private static final String LAST_INSTRUMENT_ID_PARAM = "last_instrument_id";
    private static final String STOCK_ORDER_PARAM = "stock_order";
    private static final String CLIENT_ORDER_ID_PARAM = "client_order_id";
    private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
    private static final String INSTRUMENT_ID_PARAM = "instrument_id";

    private final Region region;
    private final HttpApiClient apiClient;

    public TradeHttpApiService(HttpApiConfig config) {
        this(new HttpApiClient(config));
    }

    public TradeHttpApiService(HttpApiClient apiClient) {
        this.region = Region.of(apiClient.getConfig().getRegionId())
                .orElseThrow(() -> new ClientException(ErrorCode.INVALID_PARAMETER,
                        "Must set region id which defined in " + Region.class.getName() + " when using this service."));
        this.apiClient = apiClient;
    }

    @Override
    public List<Account> getAccountList(String subscriptionId) {
        HttpRequest request = new HttpRequest("/app/subscriptions/list", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotEmpty(subscriptionId)) {
            params.put(SUBSCRIPTION_ID_PARAM, subscriptionId);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<Account>>() {}.getType()).doAction();
    }

    @Override
    public AccountDetail getAccountDetail(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/account/profile", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountDetail.class).doAction();
    }

    @Override
    public AccountBalance getAccountBalance(String accountId, String totalAssetCurrency) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/account/balance", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        if (StringUtils.isNotEmpty(totalAssetCurrency)) {
            params.put(TOTAL_ASSET_CURRENCY_PARAM, totalAssetCurrency);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountBalance.class).doAction();
    }

    @Override
    public AccountPositions getAccountPositions(String accountId, Integer pageSize, String lastInstrumentId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/account/positions", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastInstrumentId)) {
            params.put(LAST_INSTRUMENT_ID_PARAM, lastInstrumentId);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountPositions.class).doAction();
    }

    @Override
    public OrderResponse placeOrder(String accountId, StockOrder stockOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(STOCK_ORDER_ARG, stockOrder);
        HttpRequest request = new HttpRequest("/trade/order/place", Versions.V1, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(STOCK_ORDER_PARAM, stockOrder);
        request.setBody(params);
        return apiClient.request(request).responseType(OrderResponse.class).doAction();
    }

    @Override
    public OrderResponse replaceOrder(String accountId, StockOrder stockOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(STOCK_ORDER_ARG, stockOrder);
        HttpRequest request = new HttpRequest("/trade/order/replace", Versions.V1, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(STOCK_ORDER_PARAM, stockOrder);
        request.setBody(params);
        return apiClient.request(request).responseType(OrderResponse.class).doAction();
    }

    @Override
    public OrderResponse cancelOrder(String accountId, String clientOrderId) {
        Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
        HttpRequest request = new HttpRequest("/trade/order/cancel", Versions.V1, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        request.setBody(params);
        return apiClient.request(request).responseType(OrderResponse.class).doAction();
    }


    @Override
    public <T extends Order> Orders<T> getDayOrders(String accountId, Integer pageSize, String lastClientOrderId) {
        return this.getOrders("/trade/orders/list-today", accountId, pageSize, lastClientOrderId);
    }

    @Override
    public <T extends Order> Orders<T> getOpenedOrders(String accountId, Integer pageSize, String lastClientOrderId) {
        return this.getOrders("/trade/orders/list-open", accountId, pageSize, lastClientOrderId);
    }

    private <T extends Order> Orders<T> getOrders(String uri, String accountId, Integer pageSize, String lastClientOrderId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest(uri, Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastClientOrderId)) {
            params.put(LAST_CLIENT_ORDER_ID_PARAM, lastClientOrderId);
        }
        request.setQuery(params);
        Type responseType = region == Region.hk ?
                new TypeToken<Orders<SimpleOrder>>() {}.getType() :
                new TypeToken<Orders<ComboOrder>>() {}.getType();
        return apiClient.request(request).responseType(responseType).doAction();
    }

    @Override
    public <T extends Order> T getOrderDetails(String accountId, String clientOrderId) {
        Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
        HttpRequest request = new HttpRequest("/trade/order/detail", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        request.setQuery(params);
        Type responseType = region == Region.hk ? SimpleOrder.class : ComboOrder.class;
        return apiClient.request(request).responseType(responseType).doAction();
    }

    @Override
    public InstrumentInfo getTradeInstrument(String instrumentId) {
        Assert.notBlank(INSTRUMENT_ID_ARG, instrumentId);
        HttpRequest request = new HttpRequest("/trade/instrument", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(INSTRUMENT_ID_PARAM, instrumentId);
        request.setQuery(params);
        return apiClient.request(request).responseType(InstrumentInfo.class).doAction();
    }
}
