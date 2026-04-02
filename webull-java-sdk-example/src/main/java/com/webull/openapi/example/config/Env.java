package com.webull.openapi.example.config;

import java.util.HashMap;
import java.util.Map;

import com.webull.openapi.common.Region;

public final class Env {

    private Env() {
    }

    public static final String APP_KEY = "e5b180ee67844ed24c41cc8c371d1a60";
    public static final String APP_SECRET = "416e12a2abbe3908cbeba554b1ce983b";
    public static final String REGION_ID = Region.us.name(); // your region
}
