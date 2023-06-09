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

import java.util.Set;

public class CorpActionRequest {

    private Integer pageNumber;

    private Integer pageSize;

    private Set<String> instrumentIds;

    private String startDate;

    private String endDate;

    private Set<String> eventTypes;

    private String lastUpdateTime;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Set<String> getInstrumentIds() {
        return instrumentIds;
    }

    public void setInstrumentIds(Set<String> instrumentIds) {
        this.instrumentIds = instrumentIds;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Set<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "CorpActionRequest{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", instrumentIds=" + instrumentIds +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", eventTypes=" + eventTypes +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}
