package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
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

import java.util.List;

import java.time.LocalDateTime;

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

    @Test
    public void testUpdateBeginTestTime() throws Exception {
        ProxyData proxyData = proxyDataApiService.updateBeginTestTime(1L, LocalDateTime.now());
        logger.info("修改后的开始时间：{}", CoreDateUtils.formatLocalDateTime(proxyData.getBeginTestTime()));
        Assert.assertNotNull(proxyData.getBeginTestTime());
    }

    @Test
    public void testFind() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QProxyData.id, 1L);
        apiRequest.filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.WAITING_TEST);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QProxyData.createdTime);
        apiRequestPage.addOrder(QProxyData.id);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<ProxyData> numberLotteryFetchResultDataApiResponse = proxyDataApiService.findAll(pageableWrapper);
        logger.info("查询总数为：{}", numberLotteryFetchResultDataApiResponse.getPageTotal());

        List<ProxyData> proxyDatas = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        proxyDatas.forEach(proxyData -> {
            logger.info("代理数据，代理验证状态：[{}], 失败次数:[{}]", proxyData.getProxyValidateStatus().getName(), proxyData.getFailedCount());
        });
    }

    @Test
    public void testIncreaseScore() {
        Long id = 2L;
        proxyDataApiService.increaseScore(id);
    }

    @Test
    public void testDecreaseScore() {
        Long id = 15L;
        proxyDataApiService.decreaseScore(id);
    }
}
