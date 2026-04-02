package com.webull.openapi.example.trade;

public class Position {
    private String ticker;
    private double shares;

    public Position() {}                    // required for Jackson
    public Position(String ticker, double shares) {
        this.ticker = ticker;
        this.shares = shares;
    }

    // getters and setters
    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }
    public double getShares() { return shares; }
    public void setShares(double shares) { this.shares = shares; }
}