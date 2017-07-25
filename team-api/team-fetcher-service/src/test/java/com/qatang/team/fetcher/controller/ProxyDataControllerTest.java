package com.qatang.team.fetcher.controller;

import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.config.InitConfig;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wangzhiliang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
@EnableFeignClients({
        "com.qatang.team.fetcher"
})
public class ProxyDataControllerTest {
    @Autowired
    private ProxyDataApiService proxyDataApiService;

    @Test
    public void testCreate() {
        ProxyData proxyData = new ProxyData();
        proxyData.setHost("test");
        proxyData.setPort(10001);
        proxyData.setPassword("test");
        proxyData.setUsername("test");
        proxyData.setProxyValidateStatus(ProxyValidateStatus.INIT);
        proxyDataApiService.create(proxyData);
    }
}
