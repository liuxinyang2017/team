package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataApiService;
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
    private FetchNumberLotteryDetailDataApiService fetchNumberLotteryDetailDataApiService;

    @Test
    public void testSave() {
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData = new FetchNumberLotteryDetailItemData();
        fetchNumberLotteryDetailData.setLotteryType(LotteryType.FC_SSQ);
        fetchNumberLotteryDetailData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryDetailData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryDetailData.setFetchResultId(1L);
        fetchNumberLotteryDetailData.setPhase("20170701");
        fetchNumberLotteryDetailData.setPrizeAmount(1000L);
        fetchNumberLotteryDetailData.setPrizeCount(1L);
        fetchNumberLotteryDetailData.setPrizeKey("prize1");
        fetchNumberLotteryDetailData.setPrizeName("一等奖");

        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailDataResult = fetchNumberLotteryDetailDataApiService.create(fetchNumberLotteryDetailData);
        Assert.assertNotNull(fetchNumberLotteryDetailDataResult);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData = new FetchNumberLotteryDetailItemData();
        fetchNumberLotteryDetailData.setId(id);
        fetchNumberLotteryDetailData.setPrizeName("二等奖");
        fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.update(fetchNumberLotteryDetailData);
        Assert.assertTrue("二等奖".equals(fetchNumberLotteryDetailData.getPrizeName()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.get(id);
        logger.info("根据id[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", id, fetchNumberLotteryDetailData.getLotteryType().getName(), fetchNumberLotteryDetailData.getFetcherType().getName());
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170701";
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("根据彩种[{}]，彩期[{}],抓取器[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", LotteryType.FC_SSQ.getName(), phase, fetchNumberLotteryDetailData.getFetcherType().getName(), fetchNumberLotteryDetailData.getLotteryType().getName(), fetchNumberLotteryDetailData.getFetcherType().getName());
        Assert.assertNotNull(fetchNumberLotteryDetailData);
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
        ApiResponse<FetchNumberLotteryDetailItemData> numberLotteryFetchDetailDataApiResponse = fetchNumberLotteryDetailDataApiService.findAll(pageableWrapper);
        logger.info("查询总数：{}", numberLotteryFetchDetailDataApiResponse.getTotal());

        List<FetchNumberLotteryDetailItemData> list = Lists.newArrayList(numberLotteryFetchDetailDataApiResponse.getPagedData());
        list.forEach(numberLotteryFetchDetailData -> {
            logger.info("开奖详情抓取彩种[{}], 抓取来源[{}]", numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        });
    }
}
