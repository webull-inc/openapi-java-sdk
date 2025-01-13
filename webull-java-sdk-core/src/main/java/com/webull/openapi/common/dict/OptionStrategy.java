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
package com.webull.openapi.common.dict;

public enum OptionStrategy {

    /** Single option **/
    SINGLE,

    /**  Buy writes  **/
    COVERED_STOCK,

    /**  Straddle  **/
    STRADDLE,

    /** Strangle **/
    STRANGLE,

    /** Vertical **/
    VERTICAL,

    /** Calendar **/
    CALENDAR,

    /**  Butterfly  **/
    BUTTERFLY,

    /** Condor **/
    CONDOR,

    /**  Collar with stock  **/
    COLLAR_WITH_STOCK,

    /**  Iron butterfly  **/
    IRON_BUTTERFLY,

    /**  Iron condor  **/
    IRON_CONDOR,

    /**  Diagonal  **/
    DIAGONAL

}
