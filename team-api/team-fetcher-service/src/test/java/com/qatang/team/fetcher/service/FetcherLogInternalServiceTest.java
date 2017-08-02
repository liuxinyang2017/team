package com.qatang.team.fetcher.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.bean.QFetcherLog;
import com.qatang.team.fetcher.config.InitConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/21
 */
public class FetcherLogInternalServiceTest extends BaseTest {

    @Autowired
    private FetcherLogInternalService fetcherLogInternalService;

    @Test
    public void testSave() {
        FetcherLog fetcherLog = new FetcherLog();
        fetcherLog.setBeginTestTime(LocalDateTime.now());
        fetcherLog.setEndTestTime(LocalDateTime.now());
        fetcherLog.setFetcherType(FetcherType.F_500W);
        fetcherLog.setHost("127.0.0.1");
        fetcherLog.setPort(80);
        fetcherLog.setMessage("测试用例");
        fetcherLog.setProxyId(111L);
        fetcherLog.setSpentMills(1000L);
        fetcherLog.setSuccess(YesNoStatus.YES);

        FetcherLog fetcherLogResult = fetcherLogInternalService.save(fetcherLog);
        Assert.assertNotNull(fetcherLogResult);
    }

    @Test
    public void testUpdate() {
        FetcherLog fetcherLog = new FetcherLog();
        fetcherLog.setId(1L);
        fetcherLog.setSuccess(YesNoStatus.NO);
        fetcherLog = fetcherLogInternalService.update(fetcherLog);
        Assert.assertTrue(YesNoStatus.NO.equals(fetcherLog.getSuccess()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        FetcherLog fetcherLog = fetcherLogInternalService.get(id);
        logger.info("根据id[{}]获取抓取日志，代理地址为：{}，代理端口为：{}", id, fetcherLog.getHost(), fetcherLog.getPort());
        Assert.assertNotNull(fetcherLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QFetcherLog.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QFetcherLog.createdTime);
        apiRequestPage.addOrder(QFetcherLog.id);
        ApiResponse<FetcherLog> numberLotteryFetchResultDataApiResponse = fetcherLogInternalService.findAll(apiRequest, apiRequestPage);
        logger.info("抓取日志总数：{}", numberLotteryFetchResultDataApiResponse.getTotal());

        List<FetcherLog> list = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        list.forEach(fetcherLog -> {
            logger.info("抓取日志代理地址[{}],代理端口：[{}]", fetcherLog.getHost(), fetcherLog.getPort());
        });
    }
}
