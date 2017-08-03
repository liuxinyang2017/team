package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchResultData;
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
        String result = "1,2,3,4";
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setId(1L);
        numberLotteryFetchResultData.setResult(result);
        numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.update(numberLotteryFetchResultData);
        Assert.assertTrue(result.equals(numberLotteryFetchResultData.getResult()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.get(id);
        logger.info("根据id[{}]获取开奖结果抓取数据：抓取结果：[{}], 彩期：[{}]", id, numberLotteryFetchResultData.getResult(), numberLotteryFetchResultData.getPhase());
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        String phase = "20170802";
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataApiService.getByLotteryTypeAndPhase(LotteryType.TC_DLT, phase);
        logger.info("根据彩种：[{}], 彩期：[{}]获取开奖结果抓取数据，抓取结果：[{}]", LotteryType.TC_DLT, phase, numberLotteryFetchResultData.getResult());
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testFind() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QNumberLotteryFetchResultData.id, 1L);
        apiRequest.filterEqual(QNumberLotteryFetchDetailData.lotteryType, LotteryType.TC_DLT);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QNumberLotteryFetchResultData.createdTime);
        apiRequestPage.addOrder(QNumberLotteryFetchResultData.id);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryFetchResultData> numberLotteryFetchResultDataApiResponse = numberLotteryFetchResultDataApiService.findAll(pageableWrapper);
        logger.info("查询总数为：{}", numberLotteryFetchResultDataApiResponse.getPageTotal());

        List<NumberLotteryFetchResultData> numberLotteryFetchResultDatas = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        numberLotteryFetchResultDatas.forEach(numberLotteryFetchResultData -> {
            logger.info("开奖结果抓取彩期：[{}], 彩种:[{}]", numberLotteryFetchResultData.getPhase(), numberLotteryFetchResultData.getLotteryType().getName());
        });
    }
}
