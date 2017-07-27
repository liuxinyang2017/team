package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetailData;
import com.qatang.team.fetcher.service.NumberLotteryFetchDetailDataApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/27
 */
public class NumberLotteryFetchDetailDataControllerTest extends AbstractControllerTest {

    @Autowired
    private NumberLotteryFetchDetailDataApiService numberLotteryFetchDetailDataApiService;

    @Test
    public void testSave() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = new NumberLotteryFetchDetailData();
        numberLotteryFetchDetailData.setLotteryType(LotteryType.FC_SSQ);
        numberLotteryFetchDetailData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchDetailData.setFetcherType(FetcherType.F_500W);
        numberLotteryFetchDetailData.setFetchResultId(1L);
        numberLotteryFetchDetailData.setPhase("20170701");
        numberLotteryFetchDetailData.setPrizeAmount(1000L);
        numberLotteryFetchDetailData.setPrizeCount(1L);
        numberLotteryFetchDetailData.setPrizeKey("prize1");
        numberLotteryFetchDetailData.setPrizeName("一等奖");

        NumberLotteryFetchDetailData numberLotteryFetchDetailDataResult = numberLotteryFetchDetailDataApiService.create(numberLotteryFetchDetailData);
        Assert.assertNotNull(numberLotteryFetchDetailDataResult);
    }

    @Test
    public void testUpdate() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = new NumberLotteryFetchDetailData();
        numberLotteryFetchDetailData.setId(1L);
        numberLotteryFetchDetailData.setPrizeName("二等奖");
        numberLotteryFetchDetailDataApiService.update(numberLotteryFetchDetailData);
    }

    @Test
    public void testGet() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = numberLotteryFetchDetailDataApiService.get(1L);
        Assert.assertNotNull(numberLotteryFetchDetailData);
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = numberLotteryFetchDetailDataApiService.getByLotteryTypeAndPhase(LotteryType.FC_SSQ, "20170701");
        Assert.assertNotNull(numberLotteryFetchDetailData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryFetchDetailData> numberLotteryFetchResultDataApiResponse = numberLotteryFetchDetailDataApiService.findAll(pageableWrapper);
        List<NumberLotteryFetchDetailData> list = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        list.forEach(numberLotteryFetchDetailData -> {
            System.out.println(numberLotteryFetchDetailData.getPhase());
        });
    }
}
