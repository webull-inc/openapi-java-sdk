package com.webull.openapi.example.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonParser;
import java.io.File;

public class Utils {
    private Utils() {
    }

    private static final OkHttpClient client = new OkHttpClient();

    public static Double StringToDouble(String val) {
        double cur = 0;
        if (val != null && !val.trim().isEmpty()) {
            try {
                cur = Double.parseDouble(val.trim());
                return cur;
            } catch (NumberFormatException e) {
                System.err.println("❌ Failed to convert cash string to number: " + val);
            }
        }
        return cur;
    }

    public static double RoundDown(double value, int decimalPlaces) {
        if (decimalPlaces < 0)
            throw new IllegalArgumentException("Decimal places must be >= 0");

        BigDecimal bd = new BigDecimal(Double.toString(value));
        return bd.setScale(decimalPlaces, RoundingMode.DOWN).doubleValue();
    }

    public static String GetStringValue(Object obj, String methodName) {
        try {
            java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            return result == null ? "null" : result.toString();
        } catch (Exception e) {
            return "No such method: " + methodName;
        }
    }

    public static Double GetTotalAccountValue(String totalMarketValue, String totalCashBalance) {
        double tav = 0;

        double mrktVal = Utils.StringToDouble(totalMarketValue);
        double ttlCash = Utils.StringToDouble(totalCashBalance);

        tav = mrktVal + ttlCash;

        return tav;
    }

    private static final String POSITIONS_FILE = "positions_by_account.json";
    private final ObjectMapper mapper = new ObjectMapper();

    // Save
    public void savePositions(Map<String, List<Position>> positionsByAccount) {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(POSITIONS_FILE), positionsByAccount);
            System.out.println("✅ Saved positions to " + POSITIONS_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save positions: " + e.getMessage());
        }
    }

    // Load
    public Map<String, List<Position>> loadPositions() {
        File file = new File(POSITIONS_FILE);
        if (!file.exists()) {
            System.out.println("No previous positions file found. Starting with empty data./n/n");
            return new HashMap<>();
        }

        try {
            Map<String, List<Position>> data = mapper.readValue(file,
                    new TypeReference<Map<String, List<Position>>>() {
                    });
            System.out.println("✅ Loaded positions for " + data.size() + " account(s)/n/n");
            return data;
        } catch (IOException e) {
            System.err.println("Failed to load positions: " + e.getMessage() + "/n/n");
            return new HashMap<>();
        }
    }

    public static String GetSafeString(Object obj, String methodName) {
        try {
            java.lang.reflect.Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            return (result != null) ? result.toString() : "null";
        } catch (Exception e) {
            return "No such method";
        }
    }

    public static double getCurrentPrice(String symbol) throws Exception {
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol + "?interval=1m&range=1d";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RuntimeException("Failed to fetch price");

            String json = response.body().string();
            JsonObject data = JsonParser.parseString(json).getAsJsonObject();

            // Navigate to current/last price (regularMarketPrice or last close)
            JsonObject quote = data.getAsJsonObject("chart")
                    .getAsJsonArray("result")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("meta");

            double price = quote.get("regularMarketPrice").getAsDouble();
            //System.out.println("Current price of " + symbol + ": " + price);
            return price;
        }
    }
}