package com.webull.openapi.endpoint;

import com.webull.openapi.common.ApiModule;
import com.webull.openapi.common.DefaultHost;
import com.webull.openapi.common.Region;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class EndpointResolverTest {

    @Test
    public void whenUnknownRegionThenEmptyHost() {
        Optional<String> host = EndpointResolver.getDefault().resolve("unknown", ApiModule.API_INSTITUTION);
        Assert.assertFalse(host.isPresent());

        host = EndpointResolver.getDefault().resolve("unknown", ApiModule.QUOTES_INSTITUTION);
        Assert.assertFalse(host.isPresent());

        host = EndpointResolver.getDefault().resolve("unknown", ApiModule.EVENTS_INSTITUTION);
        Assert.assertFalse(host.isPresent());
    }

    @Test
    public void whenRegionIsUsThenGetUsHost() {
        Optional<String> host = EndpointResolver.getDefault().resolve(Region.us.name(), ApiModule.API_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.API_US, host.get());

        host = EndpointResolver.getDefault().resolve(Region.us.name(), ApiModule.QUOTES_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.QUOTES_US, host.get());

        host = EndpointResolver.getDefault().resolve(Region.us.name(), ApiModule.EVENTS_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.EVENTS_US, host.get());
    }

    @Test
    public void whenRegionIsHkThenGetHkHost() {
        Optional<String> host = EndpointResolver.getDefault().resolve(Region.hk.name(), ApiModule.API_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.API_HK, host.get());

        host = EndpointResolver.getDefault().resolve(Region.hk.name(), ApiModule.QUOTES_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.QUOTES_HK, host.get());

        host = EndpointResolver.getDefault().resolve(Region.hk.name(), ApiModule.EVENTS_INSTITUTION);
        Assert.assertTrue(host.isPresent());
        Assert.assertEquals(DefaultHost.EVENTS_HK, host.get());
    }
}
