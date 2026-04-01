package com.webull.openapi.example.trade;

public class HoldingAction {
    private final String ticker;
    private final double shares;
    private final ActionType action;   // BUY or SELL

    // Enum to clearly define Buy or Sell
    public enum ActionType {
        BUY,
        SELL
    }

    /**
     * Constructor
     * @param ticker Stock symbol (e.g. "AAPL")
     * @param shares Number of shares to buy or sell
     * @param action Whether to BUY or SELL
     */
    public HoldingAction(String ticker, double shares, ActionType action) {
        if (ticker == null || ticker.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticker cannot be null or empty");
        }
        if (shares <= 0) {
            throw new IllegalArgumentException("Shares must be greater than 0");
        }
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }

        this.ticker = ticker.toUpperCase().trim();
        this.shares = shares;
        this.action = action;
    }

    // Getters
    public String getTicker() {
        return ticker;
    }

    public double getShares() {
        return shares;
    }

    public ActionType getAction() {
        return action;
    }

    // Helper methods
    public boolean isBuy() {
        return action == ActionType.BUY;
    }

    public boolean isSell() {
        return action == ActionType.SELL;
    }

    @Override
    public String toString() {
        return action + " " + shares + " shares of " + ticker;
    }
}
