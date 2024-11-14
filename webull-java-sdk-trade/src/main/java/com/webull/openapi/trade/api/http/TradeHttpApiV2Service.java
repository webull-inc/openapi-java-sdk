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
import com.webull.openapi.trade.api.request.v2.TradeOrder;
import com.webull.openapi.trade.api.response.AccountBalance;
import com.webull.openapi.trade.api.response.v2.OrderHistory;
import com.webull.openapi.trade.api.response.v2.Account;
import com.webull.openapi.trade.api.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.api.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This v2 interface is exclusively available for Webull Japan brokerage clients.
 * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
 * but support will be gradually introduced in the future.
 */
public class TradeHttpApiV2Service implements TradeApiV2Service {

    private static final String ACCOUNT_ID_ARG = "accountId";
    private static final String TRADE_ORDER_ARG = "tradeOrder";
    private static final String NEW_ORDERS_ARG = "newOrders";
    private static final String MODIFY_ORDERS_ARG = "modifyOrders";
    private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";
    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String START_TIME_PARAM = "start_date";
    private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
    private static final String ACCOUNT_ID_PARAM = "account_id";

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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    @Override
    public List<Account> listAccount() {
        HttpRequest request = new HttpRequest("/openapi/account/list", Versions.V1, HttpMethod.GET);
        return apiClient.request(request).responseType(new TypeToken<List<Account>>() {
        }.getType()).doAction();
    }

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    @Override
    public AccountBalanceInfo balanceAccount(String accountId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/openapi/account/balance", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(params);
        Type responseType = region == Region.jp ? AccountBalanceInfo.class : AccountBalance.class;
        return apiClient.request(request).responseType(responseType).doAction();
    }

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
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
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    @Override
    public List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String lastClientOrderId) {
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
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<OrderHistory>>() {}.getType()).doAction();    }
}
