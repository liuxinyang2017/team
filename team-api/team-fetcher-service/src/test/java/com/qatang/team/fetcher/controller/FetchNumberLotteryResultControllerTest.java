package com.qatang.team.fetcher.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchResultData;
import com.qatang.team.fetcher.service.FetchNumberLotteryResultDataApiService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/26
 */
public class FetchNumberLotteryResultControllerTest extends AbstractControllerTest {

    @Autowired
    private FetchNumberLotteryResultDataApiService fetchNumberLotteryResultDataApiService;

    @Test
    public void testSave() {
        FetchNumberLotteryResultData fetchNumberLotteryResultData = new FetchNumberLotteryResultData();
        fetchNumberLotteryResultData.setLotteryType(LotteryType.TC_DLT);
        fetchNumberLotteryResultData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryResultData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryResultData.setPhase("20170802");
        fetchNumberLotteryResultData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryResultData.setResult("1,12,34");

        FetchNumberLotteryResultData fetchResultDataNumberLotteryResult = fetchNumberLotteryResultDataApiService.create(fetchNumberLotteryResultData);
        Assert.assertNotNull(fetchResultDataNumberLotteryResult);
    }

    @Test
    public void testGet() {
        Long id = 1L;
        FetchNumberLotteryResultData fetchNumberLotteryResultData = fetchNumberLotteryResultDataApiService.get(id);
        logger.info("根据id[{}]获取开奖结果抓取数据：抓取结果：[{}], 彩期：[{}]", id, fetchNumberLotteryResultData.getResult(), fetchNumberLotteryResultData.getPhase());
        Assert.assertNotNull(fetchNumberLotteryResultData);
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170801";
        FetchNumberLotteryResultData fetchNumberLotteryResultData = fetchNumberLotteryResultDataApiService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("根据彩种：[{}], 彩期：[{}],抓取数据来源类型[{}]:获取开奖结果抓取数据，抓取结果：[{}]", LotteryType.TC_DLT, phase, fetchNumberLotteryResultData.getFetcherType().getName(), fetchNumberLotteryResultData.getResult());
        Assert.assertNotNull(fetchNumberLotteryResultData);
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
        ApiResponse<FetchNumberLotteryResultData> numberLotteryFetchResultDataApiResponse = fetchNumberLotteryResultDataApiService.findAll(pageableWrapper);
        logger.info("查询总数为：{}", numberLotteryFetchResultDataApiResponse.getPageTotal());

        List<FetchNumberLotteryResultData> fetchNumberLotteryResultData = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        fetchNumberLotteryResultData.forEach(numberLotteryFetchResultData -> {
            logger.info("开奖结果抓取彩期：[{}], 彩种:[{}]", numberLotteryFetchResultData.getPhase(), numberLotteryFetchResultData.getLotteryType().getName());
        });
    }
}
