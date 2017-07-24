package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.bean.FetcherLog;
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
 * @since 2017/7/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
public class FetcherLogInternalServiceTest {

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
        fetcherLog.setPort(81);
        fetcherLogInternalService.update(fetcherLog);
    }

    @Test
    public void testGet() {
        FetcherLog fetcherLog = fetcherLogInternalService.get(1L);
        Assert.assertNotNull(fetcherLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("id", 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        ApiResponse<FetcherLog> list = fetcherLogInternalService.findAll(apiRequest, apiRequestPage);
        Assert.assertTrue(list.getPagedData().size() > 0);
    }
}
