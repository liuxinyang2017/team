package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataApiService;
import com.qatang.team.fetcher.wrapper.FetchNumberLotteryDetailWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/27
 */
public class FetchNumberLotteryDetailDataControllerTest extends AbstractControllerTest {

    @Autowired
    private FetchNumberLotteryDetailDataApiService fetchNumberLotteryDetailDataApiService;

    @Test
    public void testSave() {
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = new FetchNumberLotteryDetailData();
        fetchNumberLotteryDetailData.setLotteryType(LotteryType.JC_GYJ);
        fetchNumberLotteryDetailData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryDetailData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryDetailData.setPhase("20170703");
        fetchNumberLotteryDetailData.setPoolAmount(1000L);
        fetchNumberLotteryDetailData.setResult("1,2,3,4");
        fetchNumberLotteryDetailData.setSaleAmount(10000L);

        List<FetchNumberLotteryDetailItemData> fetchNumberLotteryResultData = Lists.newArrayList();
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailItemData = new FetchNumberLotteryDetailItemData();
        fetchNumberLotteryDetailItemData.setLotteryType(LotteryType.JC_ZQ);
        fetchNumberLotteryDetailItemData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryDetailItemData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryDetailItemData.setPhase("20170806");
        fetchNumberLotteryDetailItemData.setPrizeAmount(100L);
        fetchNumberLotteryDetailItemData.setPrizeCount(1L);
        fetchNumberLotteryDetailItemData.setPrizeKey("prize1");
        fetchNumberLotteryDetailItemData.setPrizeName("一等家");

        fetchNumberLotteryResultData.add(fetchNumberLotteryDetailItemData);

        FetchNumberLotteryDetailWrapper fetchNumberLotteryDetailWrapper = new FetchNumberLotteryDetailWrapper();
        fetchNumberLotteryDetailWrapper.setFetchNumberLotteryDetailData(fetchNumberLotteryDetailData);
        fetchNumberLotteryDetailWrapper.setFetchNumberLotteryDetailItemDataList(fetchNumberLotteryResultData);
        fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.create(fetchNumberLotteryDetailWrapper);
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testGet() {
        Long id = 3L;
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.get(id);
        logger.info("根据id[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", id, fetchNumberLotteryDetailData.getLotteryType().getName(), fetchNumberLotteryDetailData.getFetcherType().getName());
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170703";
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.JC_GYJ, phase, FetcherType.F_500W);
        logger.info("根据彩种[{}]，彩期[{}],抓取器[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", LotteryType.FC_SSQ.getName(), phase, fetchNumberLotteryDetailData.getFetcherType().getName(), fetchNumberLotteryDetailData.getLotteryType().getName(), fetchNumberLotteryDetailData.getFetcherType().getName());
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QNumberLotteryFetchDetailData.id, 3L);
        apiRequest.filterEqual(QNumberLotteryFetchDetailData.lotteryType, LotteryType.FC_SSQ);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QNumberLotteryFetchDetailData.createdTime);
        apiRequestPage.addOrder(QNumberLotteryFetchDetailData.id);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<FetchNumberLotteryDetailData> numberLotteryFetchDetailDataApiResponse = fetchNumberLotteryDetailDataApiService.findAll(pageableWrapper);
        logger.info("查询总数：{}", numberLotteryFetchDetailDataApiResponse.getTotal());

        List<FetchNumberLotteryDetailData> list = Lists.newArrayList(numberLotteryFetchDetailDataApiResponse.getPagedData());
        list.forEach(numberLotteryFetchDetailData -> {
            logger.info("开奖详情抓取彩种[{}], 抓取来源[{}]", numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        });
    }

    @Test
    public void testGetByDetailId() {
        Long id = 3L;
        List<FetchNumberLotteryDetailItemData> list = fetchNumberLotteryDetailDataApiService.getByDetailId(id);
        list.forEach(fetchNumberLotteryDetailItemData -> {
            logger.info("获取到的开奖详情子对象彩期是：{}", fetchNumberLotteryDetailItemData.getPhase());
        });
    }

    @Test
    public void testFindByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170801";
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataApiService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("获取到的开奖详情对象结果：{}", fetchNumberLotteryDetailData.getResult());
    }
}
