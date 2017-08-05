package com.qatang.team.fetcher.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.bean.QFetchNumberLotteryDetailData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/23
 */
public class FetchNumberLotteryDetailDataInternalServiceTest extends BaseTest {

    @Autowired
    private FetchNumberLotteryDetailDataInternalService fetchNumberLotteryDetailDataInternalService;

    @Test
    public void testSave() {
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = new FetchNumberLotteryDetailData();
        fetchNumberLotteryDetailData.setLotteryType(LotteryType.FC_SSQ);
        fetchNumberLotteryDetailData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryDetailData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryDetailData.setPhase("20170701");
        fetchNumberLotteryDetailData.setPoolAmount(1000L);
        fetchNumberLotteryDetailData.setResult("1,2,3,4");
        fetchNumberLotteryDetailData.setSaleAmount(10000L);

        List<FetchNumberLotteryDetailItemData> fetchNumberLotteryResultData = Lists.newArrayList();
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailItemData = new FetchNumberLotteryDetailItemData();
        fetchNumberLotteryDetailItemData.setLotteryType(LotteryType.JC_GJ);
        fetchNumberLotteryDetailItemData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryDetailItemData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryDetailItemData.setPhase("20170802");
        fetchNumberLotteryDetailItemData.setPrizeAmount(100L);
        fetchNumberLotteryDetailItemData.setPrizeCount(1L);
        fetchNumberLotteryDetailItemData.setPrizeKey("prize1");
        fetchNumberLotteryDetailItemData.setPrizeName("一等家");

        fetchNumberLotteryResultData.add(fetchNumberLotteryDetailItemData);
        fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataInternalService.save(fetchNumberLotteryDetailData, fetchNumberLotteryResultData);
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testGet() {
        Long id = 1L;
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataInternalService.get(id);
        logger.info("根据id[{}]获取开奖详情数据，彩种：{}，抓取来源：{}", id, fetchNumberLotteryDetailData.getLotteryType().getName(), fetchNumberLotteryDetailData.getFetcherType().getName());
        Assert.assertNotNull(fetchNumberLotteryDetailData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QFetchNumberLotteryDetailData.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QFetchNumberLotteryDetailData.createdTime);
        apiRequestPage.addOrder(QFetchNumberLotteryDetailData.id);
        ApiResponse<FetchNumberLotteryDetailData> apiResponse = fetchNumberLotteryDetailDataInternalService.findAll(apiRequest, apiRequestPage);
        logger.info("查询总数：{}", apiResponse.getTotal());

        List<FetchNumberLotteryDetailData> list = Lists.newArrayList(apiResponse.getPagedData());
        list.forEach(numberLotteryFetchDetailData -> {
            logger.info("开奖详情抓取彩种[{}], 抓取来源[{}]", numberLotteryFetchDetailData.getLotteryType().getName(), numberLotteryFetchDetailData.getFetcherType().getName());
        });
    }

    @Test
    public void testFindByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170801";
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailDataInternalService.findByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("获取到的开奖详情对象结果：{}", fetchNumberLotteryDetailData.getResult());
    }
}
