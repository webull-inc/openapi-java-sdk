package com.webull.openapi.trade.api.response;


import java.util.List;

public class OAuthCommonPositionContractVO {

    private String lastInstrumentId;

    private List<OAuthCommonPositionDetailVO> positionDetails;

    public String getLastInstrumentId() {
        return lastInstrumentId;
    }

    public void setLastInstrumentId(String lastInstrumentId) {
        this.lastInstrumentId = lastInstrumentId;
    }

    public List<OAuthCommonPositionDetailVO> getPositionDetails() {
        return positionDetails;
    }

    public void setPositionDetails(List<OAuthCommonPositionDetailVO> positionDetails) {
        this.positionDetails = positionDetails;
    }

    @Override
    public String toString() {
        return "OAuthCommonPositionContractVO{" +
                "lastInstrumentId='" + lastInstrumentId + '\'' +
                ", positionDetails=" + positionDetails +
                '}';
    }
}
