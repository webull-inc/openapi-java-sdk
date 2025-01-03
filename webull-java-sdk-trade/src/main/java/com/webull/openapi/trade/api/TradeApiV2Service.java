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

import com.webull.openapi.trade.api.request.v2.TradeOrder;
import com.webull.openapi.trade.api.response.v2.OrderHistory;
import com.webull.openapi.trade.api.response.v2.Account;
import com.webull.openapi.trade.api.response.v2.AccountBalanceInfo;
import com.webull.openapi.trade.api.response.v2.AccountPositionsInfo;
import com.webull.openapi.trade.api.response.v2.PreviewOrderResponse;
import com.webull.openapi.trade.api.response.v2.TradeOrderResponse;

import java.util.List;

/**
 * This v2 interface is exclusively available for Webull Japan brokerage clients.
 * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
 * but support will be gradually introduced in the future.
 */
public interface TradeApiV2Service {

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    List<Account> listAccount();

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    AccountBalanceInfo balanceAccount(String accountId);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    List<AccountPositionsInfo> positionsAccount(String accountId);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    PreviewOrderResponse previewOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse placeOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse replaceOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    TradeOrderResponse cancelOrder(String accountId, TradeOrder tradeOrder);

    /**
     * This interface is exclusively available for Webull Japan brokerage clients.
     * Currently, it does not support Webull Hong Kong or Webull U.S. clients,
     * but support will be gradually introduced in the future.
     */
    List<OrderHistory> listOrders(String accountId, Integer pageSize, String startDate, String lastClientOrderId);

}
