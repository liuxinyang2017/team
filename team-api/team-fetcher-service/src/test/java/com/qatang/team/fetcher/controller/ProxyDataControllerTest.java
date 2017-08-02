package com.qatang.team.fetcher.controller;

import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.config.InitConfig;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testCreate() {
        ProxyData proxyData = new ProxyData();
        proxyData.setHost("test");
        proxyData.setPort(10002);
        proxyData.setPassword("test");
        proxyData.setUsername("test");
        proxyData.setProxyValidateStatus(ProxyValidateStatus.PENDING);
        proxyDataApiService.create(proxyData);
    }

    @Test
    public void testGet() {
        ProxyData p = proxyDataApiService.get(1L);
        Assert.assertEquals(80, p.getPort().longValue());
    }

    @Test
    public void findByHostAndPort() {
        ProxyData proxyData = proxyDataApiService.getByHostAndPort("127.0.0.1", 80);
        logger.info("根据代理地址,代理端口获取代理数据：代理地址[{}], 代理端口[{}]", proxyData.getHost(), proxyData.getPort());
    }
}
