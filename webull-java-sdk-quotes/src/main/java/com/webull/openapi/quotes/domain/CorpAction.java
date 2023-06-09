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
package com.webull.openapi.quotes.domain;

public class CorpAction {

    private Integer instrumentId;

    private String symbol;

    private String isin;

    private String exchangeCode;

    private String cusip;

    private Integer eventType;

    private String eventAction;

    private Integer eventId;

    private String source;

    private String ratioOld;

    private String ratioNew;

    private String eventDate;

    private String updateTime;

    private String createTime;

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventAction() {
        return eventAction;
    }

    public void setEventAction(String eventAction) {
        this.eventAction = eventAction;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRatioOld() {
        return ratioOld;
    }

    public void setRatioOld(String ratioOld) {
        this.ratioOld = ratioOld;
    }

    public String getRatioNew() {
        return ratioNew;
    }

    public void setRatioNew(String ratioNew) {
        this.ratioNew = ratioNew;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CorpAction{" +
                "instrumentId=" + instrumentId +
                ", symbol='" + symbol + '\'' +
                ", isin='" + isin + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", cusip='" + cusip + '\'' +
                ", eventType=" + eventType +
                ", eventAction='" + eventAction + '\'' +
                ", eventId=" + eventId +
                ", source='" + source + '\'' +
                ", ratioOld='" + ratioOld + '\'' +
                ", ratioNew='" + ratioNew + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
