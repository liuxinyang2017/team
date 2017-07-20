package com.qatang.team.fetcher.service;

import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * @author qatang
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyDataInternalServiceTest {

    @Autowired
    private ProxyDataInternalService proxyDataInternalService;

    @Test
    public void testSave() throws Exception {
        ProxyData proxyData = new ProxyData();
        proxyData.setHost("127.0.0.1");
        proxyData.setPort(80);
        proxyData.setUsername("proxy");
        proxyData.setPassword("proxy123");
        proxyData.setProxyValidateStatus(ProxyValidateStatus.INIT);
        proxyData.setBeginTestTime(LocalDateTime.now());
        proxyData.setEndTestTime(LocalDateTime.now());
        proxyData.setFailedCount(0);
        proxyData.setScore(0);

        proxyDataInternalService.save(proxyData);
        Assert.assertNotNull(proxyData.getId());
    }
}
