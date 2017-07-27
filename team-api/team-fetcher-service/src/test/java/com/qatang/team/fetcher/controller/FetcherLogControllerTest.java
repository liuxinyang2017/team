package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.service.FetcherLogApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/27
 */
public class FetcherLogControllerTest extends AbstractControllerTest {

    @Autowired
    private FetcherLogApiService fetcherLogApiService;

    @Test
    public void testSave() {
        FetcherLog fetcherLog = new FetcherLog();
        fetcherLog.setBeginTestTime(LocalDateTime.now());
        fetcherLog.setEndTestTime(LocalDateTime.now());
        fetcherLog.setFetcherType(FetcherType.F_AICAI);
        fetcherLog.setHost("127.0.0.3");
        fetcherLog.setPort(80);
        fetcherLog.setMessage("测试用例");
        fetcherLog.setProxyId(111L);
        fetcherLog.setSpentMills(1000L);
        fetcherLog.setSuccess(YesNoStatus.YES);

        FetcherLog fetcherLogResult = fetcherLogApiService.create(fetcherLog);
        Assert.assertNotNull(fetcherLogResult);
    }

    @Test
    public void testUpdate() {
        FetcherLog fetcherLog = new FetcherLog();
        fetcherLog.setId(1L);
        fetcherLog.setPort(81);
        fetcherLogApiService.update(fetcherLog);
    }

    @Test
    public void testGet() {
        FetcherLog fetcherLog = fetcherLogApiService.get(1L);
        Assert.assertNotNull(fetcherLog);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<FetcherLog> numberLotteryFetchResultDataApiResponse = fetcherLogApiService.findAll(pageableWrapper);
        List<FetcherLog> list = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        list.forEach(fetcherLog -> {
            System.out.println(fetcherLog.getId());
        });
    }
}
