package com.webull.openapi.example.config;

import java.util.HashMap;
import java.util.Map;

public final class Acct {
    private Acct() {
    }

    // Named accounts with their IDs
    public static final Map<String, String> TRADING_ACCOUNTS = new HashMap<String, String>() {{
        put("CUZ9WNZ4","Cash");
        put("CVS7VE93", "Rollover");
        put("CVT5UWB8", "ROTH");
    }};

    //public static final String ACCOUNT = "CVS7VE93";

    /**
     * Get friendly name for an account.
     * Falls back gracefully if not found.
     */
    public static String GetFriendlyName(String accountKey) {
        if (accountKey == null || accountKey.isEmpty()) {
            return "Unknown Account";
        }
        return TRADING_ACCOUNTS.getOrDefault(accountKey, "Account " + accountKey);
    }
}
