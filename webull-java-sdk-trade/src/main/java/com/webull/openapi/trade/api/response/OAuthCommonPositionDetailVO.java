package com.webull.openapi.trade.api.response;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OAuthCommonPositionDetailVO implements Serializable {

    private static final long serialVersionUID = -5374393248772287627L;

    /**
     * 持仓id
     */
    private String id;
    /**
     * Margin交易的标的Symbol
     */
    private String symbol;

    private String accountId;

    private String currency;

    private String accountTaxType;

    private String tickerId;

    /**
     * 持仓的ID
     */
    private String contractId;

    /**
     * 开仓方向，多头为long，空头为short
     */
    private String holdType;

    /**
     * 该持仓的信用类型
     */
    private String marginType;

    /**
     * 剩余持仓股数
     */
    private BigDecimal qty;

    /**
     * 每一股开仓时的本金成本，不含费用
     */
    private BigDecimal averagePrice;

    /**
     * 未实现盈亏或持仓盈亏，正数代表盈利，负数代表损失
     */
    @SerializedName("unrealized_pl")
    private BigDecimal unrealizedPL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountTaxType() {
        return accountTaxType;
    }

    public void setAccountTaxType(String accountTaxType) {
        this.accountTaxType = accountTaxType;
    }

    public String getTickerId() {
        return tickerId;
    }

    public void setTickerId(String tickerId) {
        this.tickerId = tickerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getUnrealizedPL() {
        return unrealizedPL;
    }

    public void setUnrealizedPL(BigDecimal unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    @Override
    public String toString() {
        return "OAuthCommonPositionDetailVO{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", accountId='" + accountId + '\'' +
                ", currency='" + currency + '\'' +
                ", accountTaxType='" + accountTaxType + '\'' +
                ", tickerId='" + tickerId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", holdType='" + holdType + '\'' +
                ", marginType='" + marginType + '\'' +
                ", qty=" + qty +
                ", averagePrice=" + averagePrice +
                ", unrealizedPL=" + unrealizedPL +
                '}';
    }
}
