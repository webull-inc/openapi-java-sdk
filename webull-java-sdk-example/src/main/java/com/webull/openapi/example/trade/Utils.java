package com.webull.openapi.example.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class Utils {
    private Utils() {
    }

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
        if (decimalPlaces < 0) throw new IllegalArgumentException("Decimal places must be >= 0");
        
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
                new TypeReference<Map<String, List<Position>>>() {});
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
}