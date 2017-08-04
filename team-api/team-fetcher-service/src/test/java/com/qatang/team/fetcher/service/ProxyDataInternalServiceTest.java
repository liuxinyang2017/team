package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author qatang
 */
public class ProxyDataInternalServiceTest extends BaseTest {

    @Autowired
    private ProxyDataInternalService proxyDataInternalService;

    @Test
    public void testSave() throws Exception {
        ProxyData proxyData = new ProxyData();
        proxyData.setHost("127.0.0.1");
        proxyData.setPort(80);
        proxyData.setUsername("proxy");
        proxyData.setPassword("proxy123");
        proxyData.setProxyValidateStatus(ProxyValidateStatus.PENDING);
        proxyData.setBeginTestTime(LocalDateTime.now());
        proxyData.setEndTestTime(LocalDateTime.now());
        proxyData.setFailedCount(0);
        proxyData.setScore(0);

        proxyData = proxyDataInternalService.save(proxyData);
        Assert.assertNotNull(proxyData.getId());
    }

    @Test
    public void testFindAll() throws Exception {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QProxyData.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        ApiResponse<ProxyData> list = proxyDataInternalService.findAll(apiRequest, apiRequestPage);
        Assert.assertTrue(list.getPagedData().size() > 0);
        logger.info("查询结果数量：[{}]", list.getPagedData().size());
    }

    @Test
    public void testGet() throws Exception {
        ProxyData proxyData = proxyDataInternalService.get(1L);
        Assert.assertTrue(!proxyData.getHost().isEmpty());
    }

    @Test
    public void testUpdateWaitingTestStatus() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateWaitingTestStatus(1L);
        logger.info("修改后的状态：{}", proxyData.getProxyValidateStatus().getName());
        Assert.assertTrue(proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.WAITING_TEST));
    }

    @Test
    public void testUpdateTestingStatus() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateTestingStatus(1L);
        logger.info("修改后的状态：{}", proxyData.getProxyValidateStatus().getName());
        Assert.assertTrue(proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.TESTING));
    }

    @Test
    public void testUpdatePassStatus() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updatePassStatus(1L);
        logger.info("修改后的状态：{}", proxyData.getProxyValidateStatus().getName());
        Assert.assertTrue(proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.PASS));
    }

    @Test
    public void testUpdateFailedStatus() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateFailedStatus(1L);
        logger.info("修改后的状态：{}", proxyData.getProxyValidateStatus().getName());
        Assert.assertTrue(proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.FAILED));
    }

    @Test
    public void testUpdateObsoletedStatus() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateObsoletedStatus(1L);
        logger.info("修改后的状态：{}", proxyData.getProxyValidateStatus().getName());
        Assert.assertTrue(proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.OBSOLETED));
    }

    @Test
    public void testUpdateBeginTestTime() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateBeginTestTime(1L, LocalDateTime.now());
        logger.info("修改后的开始时间：{}", CoreDateUtils.formatLocalDateTime(proxyData.getBeginTestTime()));
        Assert.assertNotNull(proxyData.getBeginTestTime());
    }

    @Test
    public void testUpdateEndTestTime() throws Exception {
        ProxyData proxyData = proxyDataInternalService.updateEndTestTime(1L, LocalDateTime.now());
        logger.info("修改后的结束时间：{}", CoreDateUtils.formatLocalDateTime(proxyData.getEndTestTime()));
        Assert.assertNotNull(proxyData.getEndTestTime());
    }

    @Test
    public void findByHostAndPort() {
        ProxyData proxyData = proxyDataInternalService.getByHostAndPort("60.214.236.132", 8118);
        logger.info("根据代理地址,代理端口获取代理数据：代理地址[{}], 代理端口[{}]", proxyData.getHost(), proxyData.getPort());
    }

    @Test
    public void testIncreaseScore() {
        Long id = 3L;
        int score = 1;
        proxyDataInternalService.increaseScore(id, score);
    }

    @Test
    public void testDecreaseScore() {
        Long id = 129L;
        int score = 1;
        proxyDataInternalService.decreaseScore(id, score);
    }
}
