package com.webull.openapi.config;

import org.junit.Assert;
import org.junit.Test;

public class ProjectReaderHelperTest {

    @Test
    public void whenGetSDKInfoThenNotEmpty() {
        String info = ProjectReaderHelper.getClientSDKInfo();
        System.out.println("SDK info: " + info);
        Assert.assertTrue(info != null && info.length() > 0);
    }
}
