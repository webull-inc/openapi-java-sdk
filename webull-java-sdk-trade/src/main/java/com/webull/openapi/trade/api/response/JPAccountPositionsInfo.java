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
package com.webull.openapi.trade.api.response;

import java.util.List;


public class JPAccountPositionsInfo {

    /**
     * 持仓id
     */
    private String positionId;


    /**
     * 数量
     */
    private String quantity;

    /**
     * 成本价
     */
    private String costPrice;

    /**
     * 持仓盈亏
     */
    private String unrealizedProfitLoss;

    /**
     * 浮动盈亏率 scale = 4
     */
    private String unrealizedProfitLossRate;

    /**
     * 持仓明细
     */
    private List<CommonPositionInfo> items;
}
