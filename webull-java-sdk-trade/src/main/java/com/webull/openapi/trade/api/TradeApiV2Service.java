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
package com.webull.openapi.trade.api;

import com.webull.openapi.trade.api.request.v2.OptionOrder;
import com.webull.openapi.trade.api.request.v2.TradeOrder;
import com.webull.openapi.trade.api.response.OAuthCommonPositionContractVO;
import com.webull.openapi.trade.api.response.OAuthCommonPositionDetailVO;
import com.webull.openapi.trade.api.response.TradeCalendar;
import com.webull.openapi.trade.api.response.v2.Account;
import com.webull.openapi.trade.api.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.api.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.api.response.v2.OrderHistory;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;

import java.util.List;
import java.util.Map;

public interface TradeApiV2Service {

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    List<Account> listAccount();

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    AccountBalanceInfo balanceAccount(String accountId);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    List<AccountPositionsInfo> positionsAccount(String accountId);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */
    TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is currently available only to individual brokerage customers in Webull Japan
     * and institutional brokerage clients in Webull Hong Kong. It is not yet available to
     * Webull US brokerage customers, but support will be introduced progressively in the future.
     */

    List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String endDate, String lastClientOrderId);

    /**
     * This interface is exclusively available for Webull Japan brokerage and Webull Hong Kong brokerage clients.
     * Currently, it does not support Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    OrderHistory getOrderDetails(String accountId, String clientOrderId);

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    PreviewOrderResponse previewOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    TradeOrderResponse placeOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    TradeOrderResponse replaceOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    TradeOrderResponse cancelOption(String accountId, OptionOrder optionOrder);

    /**
     * This interface is currently available only to individual and institutional clients
     * of Webull Hong Kong brokerages. It is not yet supported for clients of Webull US
     * and Webull Japan brokerages, but support will be gradually introduced in the future.
     */
    List<TradeCalendar> getTradeCalendar(String market, String start, String end);

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
     * Clearing headers
     */
    void removeCustomHeaders();

    /**
     * get Position details
     * @param accountId
     * @param tickerId
     * @param startId the last positionId of response, default 0
     * @param size get position numbers each request
     * @return
     */
    OAuthCommonPositionContractVO getCommonPositionDetail(String accountId, String tickerId, String startId, Integer size);

}
