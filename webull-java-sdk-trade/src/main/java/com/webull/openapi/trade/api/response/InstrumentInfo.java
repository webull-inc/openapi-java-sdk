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

public class InstrumentInfo extends InstrumentBasic{
    private String instrumentType;
    private String instrumentSuperType;
    private String shortName;
    private String buyUnit;
    private String tradePolicy;
    private String marginRatio;
    private String market;
    private String cusip;
    private String exchangeCode;
    private String listStatus;
    private String currency;
    private String marginable;
    private String shortable;
    private String fractionalTrading;

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getInstrumentSuperType() {
        return instrumentSuperType;
    }

    public void setInstrumentSuperType(String instrumentSuperType) {
        this.instrumentSuperType = instrumentSuperType;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBuyUnit() {
        return buyUnit;
    }

    public void setBuyUnit(String buyUnit) {
        this.buyUnit = buyUnit;
    }

    public String getTradePolicy() {
        return tradePolicy;
    }

    public void setTradePolicy(String tradePolicy) {
        this.tradePolicy = tradePolicy;
    }

    public String getMarginRatio() {
        return marginRatio;
    }

    public void setMarginRatio(String marginRatio) {
        this.marginRatio = marginRatio;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getListStatus() {
        return listStatus;
    }

    public void setListStatus(String listStatus) {
        this.listStatus = listStatus;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMarginable() {
        return marginable;
    }

    public void setMarginable(String marginable) {
        this.marginable = marginable;
    }

    public String getShortable() {
        return shortable;
    }

    public void setShortable(String shortable) {
        this.shortable = shortable;
    }

    public String getFractionalTrading() {
        return fractionalTrading;
    }

    public void setFractionalTrading(String fractionalTrading) {
        this.fractionalTrading = fractionalTrading;
    }

    @Override
    public String toString() {
        return "InstrumentInfo{" +
                "instrumentId='" + instrumentId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", instrumentType='" + instrumentType + '\'' +
                ", instrumentSuperType='" + instrumentSuperType + '\'' +
                ", shortName='" + shortName + '\'' +
                ", buyUnit='" + buyUnit + '\'' +
                ", tradePolicy='" + tradePolicy + '\'' +
                ", marginRatio='" + marginRatio + '\'' +
                ", market='" + market + '\'' +
                ", cusip='" + cusip + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", listStatus='" + listStatus + '\'' +
                ", currency='" + currency + '\'' +
                ", marginable='" + marginable + '\'' +
                ", shortable='" + shortable + '\'' +
                ", fractionalTrading='" + fractionalTrading + '\'' +
                '}';
    }
}
