package com.webull.openapi.example.config;

public class TradingAccount {
    private final String friendlyName;
    private final String accountId;
    private final String accountNumber;
    private final int code;          // Your additional integer ID/code

    public TradingAccount(String friendlyName, String accountId, String accountNumber, int code) {
        this.friendlyName = friendlyName;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.code = code;
    }

    public String getFriendlyName() { return friendlyName; }
    public String getAccountId()    { return accountId; }
    public String getAccountNumber() { return accountNumber; }
    public int getCode()            { return code; }

    @Override
    public String toString() {
        return friendlyName + " (" + accountId + ") - Code: " + code;
    }
}
