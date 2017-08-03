package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetailData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
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
        Long id = 1L;
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = new NumberLotteryFetchDetailData();
        numberLotteryFetchDetailData.setId(id);
        numberLotteryFetchDetailData.setPrizeName("二等奖");
        numberLotteryFetchDetailData = numberLotteryFetchDetailDataApiService.update(numberLotteryFetchDetailData);
        Assert.assertTrue("二等奖".equals(numberLotteryFetchDetailData.getPrizeName()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = numberLotteryFetchDetailDataApiService.get(id);
        logger.info("根据id[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", id, numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        Assert.assertNotNull(numberLotteryFetchDetailData);
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170701";
        NumberLotteryFetchDetailData numberLotteryFetchDetailData = numberLotteryFetchDetailDataApiService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("根据彩种[{}]，彩期[{}],抓取器[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", LotteryType.FC_SSQ.getName(), phase, numberLotteryFetchDetailData.getFetcherType().getName(), numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        Assert.assertNotNull(numberLotteryFetchDetailData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QNumberLotteryFetchDetailData.id, 1L);
        apiRequest.filterEqual(QNumberLotteryFetchDetailData.lotteryType, LotteryType.FC_SSQ);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QNumberLotteryFetchDetailData.createdTime);
        apiRequestPage.addOrder(QNumberLotteryFetchDetailData.id);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryFetchDetailData> numberLotteryFetchDetailDataApiResponse = numberLotteryFetchDetailDataApiService.findAll(pageableWrapper);
        logger.info("查询总数：{}", numberLotteryFetchDetailDataApiResponse.getTotal());

        List<NumberLotteryFetchDetailData> list = Lists.newArrayList(numberLotteryFetchDetailDataApiResponse.getPagedData());
        list.forEach(numberLotteryFetchDetailData -> {
            logger.info("开奖详情抓取彩种[{}], 抓取来源[{}]", numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        });
    }
}
