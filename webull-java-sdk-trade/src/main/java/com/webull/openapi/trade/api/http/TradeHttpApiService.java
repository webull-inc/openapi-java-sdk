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
import com.webull.openapi.common.Headers;
import com.webull.openapi.common.Region;
import com.webull.openapi.common.Versions;
import com.webull.openapi.common.dict.InstrumentSuperType;
import com.webull.openapi.common.dict.OptionType;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ErrorCode;
import com.webull.openapi.http.HttpApiClient;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.http.HttpRequest;
import com.webull.openapi.http.common.HttpMethod;
import com.webull.openapi.trade.api.TradeApiService;
import com.webull.openapi.trade.api.request.StockOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrder;
import com.webull.openapi.trade.api.request.v2.OptionOrderItemLeg;
import com.webull.openapi.trade.api.response.Account;
import com.webull.openapi.trade.api.response.AccountBalance;
import com.webull.openapi.trade.api.response.AccountDetail;
import com.webull.openapi.trade.api.response.AccountPositions;
import com.webull.openapi.trade.api.response.BalanceBase;
import com.webull.openapi.trade.api.response.ComboOrder;
import com.webull.openapi.trade.api.response.ComboOrderResponse;
import com.webull.openapi.trade.api.response.InstrumentInfo;
import com.webull.openapi.trade.api.response.JPAccountBalance;
import com.webull.openapi.trade.api.response.Order;
import com.webull.openapi.trade.api.response.OrderResponse;
import com.webull.openapi.trade.api.response.Orders;
import com.webull.openapi.trade.api.response.SimpleOrder;
import com.webull.openapi.trade.api.response.SimpleOrderResponse;
import com.webull.openapi.trade.api.response.TradableInstruments;
import com.webull.openapi.trade.api.response.TradeCalendar;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TradeHttpApiService implements TradeApiService {

    private static final String ACCOUNT_ID_ARG = "accountId";
    private static final String STOCK_ORDER_ARG = "stockOrder";
    private static final String TRADE_ORDER_ARG = "tradeOrder";
    private static final String OPTION_ORDER_ARG = "optionOrder";
    private static final String NEW_ORDERS_ARG = "newOrders";
    private static final String MODIFY_ORDERS_ARG = "modifyOrders";
    private static final String CLIENT_ORDER_ID_ARG = "clientOrderId";
    private static final String INSTRUMENT_ID_ARG = "instrumentId";
    private static final String MARKET_ARG = "market";
    private static final String START_ARG = "start";
    private static final String END_ARG = "end";
    private static final String SYMBOL_ARG = "symbol";
    private static final String INSTRUMENT_SUPER_TYPE_ARG = "instrumentSuperType";
    private static final String INSTRUMENT_TYPE_ARG = "instrumentType";
    private static final String STRIKE_PRICE_ARG = "strikePrice";
    private static final String INIT_EXP_DATE_ARG = "initExpDate";

    private static final String ACCOUNT_ID_PARAM = "account_id";
    private static final String PAGE_SIZE_PARAM = "page_size";
    private static final String SUBSCRIPTION_ID_PARAM = "subscription_id";
    private static final String TOTAL_ASSET_CURRENCY_PARAM = "total_asset_currency";
    private static final String LAST_INSTRUMENT_ID_PARAM = "last_instrument_id";
    private static final String STOCK_ORDER_PARAM = "stock_order";
    private static final String CLIENT_ORDER_ID_PARAM = "client_order_id";
    private static final String LAST_CLIENT_ORDER_ID_PARAM = "last_client_order_id";
    private static final String INSTRUMENT_ID_PARAM = "instrument_id";
    private static final String LAST_SECURITY_ID = "last_security_id";
    private static final String MARKET_PARAM = MARKET_ARG;
    private static final String START_PARAM = START_ARG;
    private static final String END_PARAM = END_ARG;
    private static final String SYMBOL_PARAM = SYMBOL_ARG;
    private static final String INSTRUMENT_SUPER_TYPE_PARAM = "instrument_super_type";
    private static final String INSTRUMENT_TYPE_PARAM = "instrument_type";
    private static final String STRIKE_PRICE_PARAM = "strike_price";
    private static final String INIT_EXP_DATE_PARAM = "init_exp_date";
    private static final String START_TIME_PARAM = "start_date";

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

    @Deprecated
    @Override
    public <T extends BalanceBase> T getAccountBalance(String accountId, String totalAssetCurrency) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/account/balance", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        if (StringUtils.isNotEmpty(totalAssetCurrency)) {
            params.put(TOTAL_ASSET_CURRENCY_PARAM, totalAssetCurrency);
        }
        request.setQuery(params);
        Type responseType = region == Region.jp ? JPAccountBalance.class : AccountBalance.class;
        return apiClient.request(request).responseType(responseType).doAction();
    }

    @Override
    public AccountPositions getAccountPositions(String accountId, Integer pageSize, String lastId) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        HttpRequest request = new HttpRequest("/account/positions", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        if (StringUtils.isNotEmpty(lastId)) {
            params.put(LAST_INSTRUMENT_ID_PARAM, lastId);
        }
        request.setQuery(params);
        return apiClient.request(request).responseType(AccountPositions.class).doAction();
    }

    @Override
    public OrderResponse placeOrder(String accountId, StockOrder stockOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(STOCK_ORDER_ARG, stockOrder);
        HttpRequest request = new HttpRequest("/trade/order/place", Versions.V1, HttpMethod.POST);
        addCustomHeaders(request, stockOrder);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(STOCK_ORDER_PARAM, stockOrder);
        request.setBody(params);
        return apiClient.request(request).responseType(getTradeOrderResponseType()).doAction();
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
        return apiClient.request(request).responseType(getTradeOrderResponseType()).doAction();
    }

    @Override
    public OrderResponse cancelOrder(String accountId, String clientOrderId) {
        Assert.notBlank(Arrays.asList(ACCOUNT_ID_ARG, CLIENT_ORDER_ID_ARG), accountId, clientOrderId);
        HttpRequest request = new HttpRequest("/trade/order/cancel", Versions.V1, HttpMethod.POST);
        Map<String, Object> params = new HashMap<>();
        params.put(ACCOUNT_ID_PARAM, accountId);
        params.put(CLIENT_ORDER_ID_PARAM, clientOrderId);
        request.setBody(params);
        return apiClient.request(request).responseType(getTradeOrderResponseType()).doAction();
    }

    private Type getTradeOrderResponseType(){
        return region == Region.us ?
                new TypeToken<SimpleOrderResponse>() {}.getType() :
                new TypeToken<ComboOrderResponse>() {}.getType();
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
        Type responseType = region == Region.us ?
                new TypeToken<Orders<ComboOrder>>() {}.getType() :
                new TypeToken<Orders<SimpleOrder>>() {}.getType();
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
        Type responseType = region == Region.us ? ComboOrder.class : SimpleOrder.class;
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

    @Override
    public TradableInstruments getTradeableInstruments(String lastSecurityId, Integer pageSize){
        HttpRequest request = new HttpRequest("/trade/instrument/tradable/list", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(lastSecurityId)){
            params.put(LAST_SECURITY_ID, lastSecurityId);
        }
        params.put(PAGE_SIZE_PARAM, pageSize == null ? 10 : pageSize);
        request.setQuery(params);
        return apiClient.request(request).responseType(TradableInstruments.class).doAction();
    }

    @Override
    public List<TradeCalendar> getTradeCalendar(String market, String start, String end) {
        Assert.notBlank(Arrays.asList(MARKET_ARG, START_ARG, END_ARG), market, start, end);
        HttpRequest request = new HttpRequest("/trade/calendar", Versions.V1, HttpMethod.GET);
        Map<String, Object> params = new HashMap<>();
        params.put(MARKET_PARAM, market);
        params.put(START_PARAM, start);
        params.put(END_PARAM, end);
        request.setQuery(params);
        return apiClient.request(request).responseType(new TypeToken<List<TradeCalendar>>() {}.getType()).doAction();
    }

    @Override
    public InstrumentInfo getSecurityInfo(String symbol, String market, String instrumentSuperType, String instrumentType, String strikePrice, String initExpDate) {
        Assert.notBlank(Arrays.asList(SYMBOL_ARG, MARKET_ARG, INSTRUMENT_SUPER_TYPE_ARG) , symbol, market, instrumentSuperType);
        Map<String, Object> params = new HashMap<>();
        if (instrumentSuperType.equals(InstrumentSuperType.OPTION.name())) {
            Assert.notBlank(Arrays.asList(INSTRUMENT_TYPE_ARG, STRIKE_PRICE_ARG, INIT_EXP_DATE_ARG), instrumentType, strikePrice, initExpDate);
            params.put(INSTRUMENT_TYPE_PARAM, instrumentType);
            params.put(STRIKE_PRICE_PARAM, strikePrice);
            params.put(INIT_EXP_DATE_PARAM, initExpDate);
        }
        HttpRequest request = new HttpRequest("/trade/security", Versions.V1, HttpMethod.GET);
        params.put(SYMBOL_PARAM, symbol);
        params.put(MARKET_PARAM, market);
        params.put(INSTRUMENT_SUPER_TYPE_PARAM, instrumentSuperType);
        request.setQuery(params);
        return apiClient.request(request).responseType(InstrumentInfo.class).doAction();
    }

    private void addCustomHeaders(HttpRequest request, StockOrder stockOrder) {
        if (StringUtils.isNotBlank(stockOrder.getCategory())) {
            request.getHeaders().put(Headers.CATEGORY_KEY, stockOrder.getCategory());
        }
    }

    private void addCustomHeaders(HttpRequest request, OptionOrder optionOrder) {
        if(CollectionUtils.isEmpty(optionOrder.getNewOrders())
                || Objects.isNull(optionOrder.getNewOrders().get(0))
                || CollectionUtils.isEmpty(optionOrder.getNewOrders().get(0).getOrders())){
            return;
        }
        OptionOrderItemLeg item = optionOrder.getNewOrders().get(0).getOrders().stream().
                filter(v-> Objects.nonNull(v) && Objects.equals(InstrumentSuperType.OPTION.name(), v.getInstrumentType()))
                .findFirst().orElse(null);
        if(Objects.isNull(item)){
            return;
        }
        List<String> categoryList = Arrays.asList( item.getMarket(), InstrumentSuperType.EQUITY.name(), OptionType.CALL.name(), InstrumentSuperType.OPTION.name());
        String category = StringUtils.join(categoryList, "_");
        if (StringUtils.isNotBlank(category)) {
            request.getHeaders().put(Headers.CATEGORY_KEY, category);
        }
    }

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

    @Override
    public TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder) {
        Assert.notBlank(ACCOUNT_ID_ARG, accountId);
        Assert.notNull(OPTION_ORDER_ARG, optionOrder);
        Assert.notEmpty(NEW_ORDERS_ARG, optionOrder.getNewOrders());
        HttpRequest request = new HttpRequest("/openapi/account/orders/option/place", Versions.V1, HttpMethod.POST);
        addCustomHeaders(request, optionOrder);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(ACCOUNT_ID_PARAM, accountId);
        request.setQuery(queryMap);
        request.setBody(optionOrder);
        return apiClient.request(request).responseType(TradeOrderResponse.class).doAction();
    }

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

}
