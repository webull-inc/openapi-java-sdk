package com.webull.openapi.trade.api.request.v2;

public class CloseContract {

    private String contractId;

    private String quantity;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CloseContract{" +
                "contractId='" + contractId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
