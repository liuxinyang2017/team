package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.service.NumberLotteryFetchResultDataApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/26
 */
public class NumberLotteryFetchResultControllerTest extends AbstractControllerTest {

    @Autowired
    private NumberLotteryFetchResultDataApiService numberLotteryFetchResultDataApiService;

    @Test
    public void testSave() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setLotteryType(LotteryType.TC_DLT);
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setFetcherType(FetcherType.F_500W);
        numberLotteryFetchResultData.setPhase("20170802");
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setPoolAmount(10000L);
        numberLotteryFetchResultData.setResult("1,12,34");
        numberLotteryFetchResultData.setSaleAmount(100L);

        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataApiService.create(numberLotteryFetchResultData);
        Assert.assertNotNull(numberLotteryFetchResultDataResult);
    }

    @Test
    public void testUpdate() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setId(1L);
        numberLotteryFetchResultData.setResult("1,2,3,4");
        numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.update(numberLotteryFetchResultData);
        System.out.print(numberLotteryFetchResultData.getResult());
    }

    @Test
    public void testGet() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.get(1L);
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.getByLotteryTypeAndPhase(LotteryType.TC_DLT, "20170802");
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testFind() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryFetchResultData> numberLotteryFetchResultDataApiResponse = numberLotteryFetchResultDataApiService.findAll(pageableWrapper);
        List<NumberLotteryFetchResultData> numberLotteryFetchResultDatas = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        numberLotteryFetchResultDatas.forEach(numberLotteryFetchResultData -> {
            System.out.println(numberLotteryFetchResultData.getPhase());
            System.out.println(numberLotteryFetchResultData.getResult());
        });
    }
}
