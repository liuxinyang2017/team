package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
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
 * @since 2017/7/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
public class NumberLotteryFetchResultDataInternalServiceTest {

    @Autowired
    private  NumberLotteryFetchResultDataInternalService numberLotteryFetchResultDataInternalService;

    @Test
    public void testSave() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setLotteryType(LotteryType.FC_SSQ);
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setFetcherType(FetcherType.F_500W);
        numberLotteryFetchResultData.setPhase("20170801");
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setPoolAmount(10000L);
        numberLotteryFetchResultData.setResult("1,12,34");
        numberLotteryFetchResultData.setSaleAmount(100L);
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataInternalService.save(numberLotteryFetchResultData);
        Assert.assertNotNull(numberLotteryFetchResultDataResult);
    }

    @Test
    public void testUpdate() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setId(1L);
        numberLotteryFetchResultData.setSaleAmount(10L);
        numberLotteryFetchResultDataInternalService.update(numberLotteryFetchResultData);
    }

    @Test
    public void testGet() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataInternalService.get(1L);
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("id", 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        ApiResponse<NumberLotteryFetchResultData> list = numberLotteryFetchResultDataInternalService.findAll(apiRequest, apiRequestPage);
        Assert.assertTrue(list.getPagedData().size() > 0);
    }
}
