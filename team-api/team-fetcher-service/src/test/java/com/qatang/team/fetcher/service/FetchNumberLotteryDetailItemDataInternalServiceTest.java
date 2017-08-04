package com.qatang.team.fetcher.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.bean.QFetchNumberLotteryDetailItemData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wp
 * @since 2017/8/4
 */
public class FetchNumberLotteryDetailItemDataInternalServiceTest extends BaseTest {

    @Autowired
    private FetchNumberLotteryDetailItemDataInternalService fetchNumberLotteryDetailItemDataInternalService;

    @Test
    public void testGet() {
        Long id = 1L;
        FetchNumberLotteryDetailItemData fetchNumberLotteryDetailItemData = fetchNumberLotteryDetailItemDataInternalService.get(id);
        logger.info("获取到的详情子对象彩期是：{}", fetchNumberLotteryDetailItemData.getPhase());
    }

    @Test
    public void testGetByDetailId() {
        Long detailId = 1L;
        List<FetchNumberLotteryDetailItemData> list = fetchNumberLotteryDetailItemDataInternalService.getByDetailId(detailId);
        list.forEach(fetchNumberLotteryDetailItemData -> {
            logger.info("获取到的开奖详情子对象彩期是：{}", fetchNumberLotteryDetailItemData.getPhase());
        });
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QFetchNumberLotteryDetailItemData.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QFetchNumberLotteryDetailItemData.createdTime);
        apiRequestPage.addOrder(QFetchNumberLotteryDetailItemData.id);
        ApiResponse<FetchNumberLotteryDetailItemData> fetchNumberLotteryDetailItemDataApiResponse = fetchNumberLotteryDetailItemDataInternalService.findAll(apiRequest, apiRequestPage);
        logger.info("查询总数为：{}", fetchNumberLotteryDetailItemDataApiResponse.getPageTotal());

        List<FetchNumberLotteryDetailItemData> fetchNumberLotteryResultData = Lists.newArrayList(fetchNumberLotteryDetailItemDataApiResponse.getPagedData());
        fetchNumberLotteryResultData.forEach(fetchNumberLotteryDetailItemData -> {
            logger.info("开奖结果抓取彩期：[{}], 彩种:[{}]", fetchNumberLotteryDetailItemData.getPhase(), fetchNumberLotteryDetailItemData.getLotteryType().getName());
        });
    }
}
