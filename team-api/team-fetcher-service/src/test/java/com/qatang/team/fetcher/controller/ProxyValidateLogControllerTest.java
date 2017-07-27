package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.service.ProxyValidateLogApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/27
 */
public class ProxyValidateLogControllerTest extends AbstractControllerTest {

    @Autowired
    private ProxyValidateLogApiService proxyValidateLogApiService;

    @Test
    public void testSave() throws Exception {
        ProxyValidateLog proxyValidateLog = new ProxyValidateLog();
        proxyValidateLog.setBeginTestTime(LocalDateTime.now());
        proxyValidateLog.setEndTestTime(LocalDateTime.now());
        proxyValidateLog.setHost("127.0.0.5");
        proxyValidateLog.setPort(80);
        proxyValidateLog.setMessage("测试");
        proxyValidateLog.setProxyId(1L);
        proxyValidateLog.setProxyValidatorType(ProxyValidatorType.V_BAIDU);
        proxyValidateLog.setSpentMills(1000L);
        proxyValidateLog.setSuccess(YesNoStatus.YES);

        proxyValidateLog = proxyValidateLogApiService.create(proxyValidateLog);
        Assert.assertNotNull(proxyValidateLog.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        ProxyValidateLog proxyValidateLog = new ProxyValidateLog();
        proxyValidateLog.setId(1L);
        proxyValidateLog.setSuccess(YesNoStatus.NO);
        proxyValidateLog = proxyValidateLogApiService.update(proxyValidateLog);
    }

    @Test
    public void testGet() throws Exception {
        ProxyValidateLog proxyValidateLog = proxyValidateLogApiService.get(1L);
        Assert.assertNotNull(proxyValidateLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<ProxyValidateLog> proxyValidateLogApiResponse = proxyValidateLogApiService.findAll(pageableWrapper);
        List<ProxyValidateLog> list = Lists.newArrayList(proxyValidateLogApiResponse.getPagedData());
        list.forEach(proxyValidateLog -> {
            System.out.println(proxyValidateLog.getHost());
            System.out.println(proxyValidateLog.getPort());
        });
    }
}
