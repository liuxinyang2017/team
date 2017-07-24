package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetailData;
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
public class NumberLotteryFetchDetailDataInternalServiceTest {

    @Autowired
    private NumberLotteryFetchDetailDataInternalService numberLotteryFetchDetailDataInternalService;

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

        NumberLotteryFetchDetailData numberLotteryFetchDetailDataResult = numberLotteryFetchDetailDataInternalService.save(numberLotteryFetchDetailData);
        Assert.assertNotNull(numberLotteryFetchDetailDataResult);
    }

    @Test
    public void testUpdate() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = new NumberLotteryFetchDetailData();
        numberLotteryFetchDetailData.setId(1L);
        numberLotteryFetchDetailData.setPrizeName("二等奖");
        numberLotteryFetchDetailDataInternalService.update(numberLotteryFetchDetailData);
    }

    @Test
    public void testGet() {
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = numberLotteryFetchDetailDataInternalService.get(1L);
        Assert.assertNotNull(numberLotteryFetchDetailData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("id", 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        ApiResponse<NumberLotteryFetchDetailData> list = numberLotteryFetchDetailDataInternalService.findAll(apiRequest, apiRequestPage);
        Assert.assertTrue(list.getPagedData().size() > 0);
    }
}
