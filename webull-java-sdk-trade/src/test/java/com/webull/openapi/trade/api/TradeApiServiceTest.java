package com.webull.openapi.trade.api;

import com.webull.openapi.execption.ClientException;
import com.webull.openapi.http.HttpApiConfig;
import com.webull.openapi.trade.api.http.TradeHttpApiService;
import org.junit.Assert;
import org.junit.Test;

public class TradeApiServiceTest {

    @Test
    public void whenRegionUnsetThenBuildServiceFailed() {
        HttpApiConfig apiConfig = HttpApiConfig.builder()
                .appKey("appKey")
                .appSecret("appSecret")
                .endpoint("127.0.0.1")
                .build();
        Assert.assertThrows(ClientException.class, () -> new TradeHttpApiService(apiConfig));
    }
}
