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

public enum EventSource {

    /**
     * IHS
     */
    IHS,

    /**
     * EDI
     */
    EDI,

    /**
     * ICE
     */
    ICE,

    /**
     * Webull artificial
     */
    WEBULL_ARTIFICIAL,

    /**
     * OTC official web
     */
    OTC_OFFICIAL_WEB,

    /**
     * CCASS
     */
    SOURCE,

    /**
     * Webull auditor
     */
    WEBULL_AUDITOR,

    /**
     * JYDB
     */
    SOURCE_NAME_JY_DB,

    /**
     * Other
     */
    WEB
}
