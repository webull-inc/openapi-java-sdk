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

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TradableInstruments {

    @SerializedName("hasNext")
    private Boolean hasNext;

    private List<TradableInstrument> instruments;

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<TradableInstrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<TradableInstrument> instruments) {
        this.instruments = instruments;
    }

    @Override
    public String toString() {
        return "TradableInstruments{" +
                "hasNext=" + hasNext +
                ", instruments=" + instruments +
                '}';
    }
}