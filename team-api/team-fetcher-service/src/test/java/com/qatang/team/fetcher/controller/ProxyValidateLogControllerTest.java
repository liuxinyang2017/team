package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.bean.QProxyValidateLog;
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
        Assert.assertTrue(proxyValidateLog.getSuccess().equals(YesNoStatus.NO));
    }

    @Test
    public void testGet() throws Exception {
        Long id = 1L;
        ProxyValidateLog proxyValidateLog = proxyValidateLogApiService.get(id);
        logger.info("根据id[{}]获取代理验证日志：代理地址：[{}], 代理端口：[{}]", id, proxyValidateLog.getHost(), proxyValidateLog.getPort());
        Assert.assertNotNull(proxyValidateLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QProxyValidateLog.id, 1L);
        apiRequest.filterEqual(QProxyValidateLog.proxyValidatorType, ProxyValidatorType.V_BAIDU);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QProxyValidateLog.createdTime);
        apiRequestPage.addOrder(QProxyValidateLog.id);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<ProxyValidateLog> proxyValidateLogApiResponse = proxyValidateLogApiService.findAll(pageableWrapper);
        logger.info("查询总数：{}", proxyValidateLogApiResponse.getPageTotal());

        List<ProxyValidateLog> list = Lists.newArrayList(proxyValidateLogApiResponse.getPagedData());
        list.forEach(proxyValidateLog -> {
           logger.info("查询代理验证日志：代理地址：[{}], 代理端口：[{}]", proxyValidateLog.getHost(), proxyValidateLog.getPort());
        });
    }
}
