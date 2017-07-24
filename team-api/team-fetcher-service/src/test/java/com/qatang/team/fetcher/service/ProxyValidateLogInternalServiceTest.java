package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.config.InitConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * @author wp
 * @since 2017/7/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
public class ProxyValidateLogInternalServiceTest {

    @Autowired
    private ProxyValidateLogInternalService proxyValidateLogInternalService;

    @Test
    public void testSave() throws Exception {
        ProxyValidateLog proxyValidateLog = new ProxyValidateLog();
        proxyValidateLog.setBeginTestTime(LocalDateTime.now());
        proxyValidateLog.setEndTestTime(LocalDateTime.now());
        proxyValidateLog.setHost("127.0.0.1");
        proxyValidateLog.setPort(80);
        proxyValidateLog.setMessage("测试");
        proxyValidateLog.setProxyId(1L);
        proxyValidateLog.setProxyValidatorType(ProxyValidatorType.V_BAIDU);
        proxyValidateLog.setSpentMills(1000L);
        proxyValidateLog.setSuccess(YesNoStatus.YES);

        proxyValidateLog = proxyValidateLogInternalService.save(proxyValidateLog);
        Assert.assertNotNull(proxyValidateLog.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        ProxyValidateLog proxyValidateLog = new ProxyValidateLog();
        proxyValidateLog.setId(1L);
        proxyValidateLog.setSuccess(YesNoStatus.NO);
        proxyValidateLog = proxyValidateLogInternalService.update(proxyValidateLog);
    }

    @Test
    public void testGet() throws Exception {
        ProxyValidateLog proxyValidateLog = proxyValidateLogInternalService.get(1L);
        Assert.assertNotNull(proxyValidateLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("id", 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        ApiResponse<ProxyValidateLog> list = proxyValidateLogInternalService.findAll(apiRequest, apiRequestPage);
        Assert.assertTrue(list.getPagedData().size() > 0);
    }
}
