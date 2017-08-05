package com.qatang.team.fetcher.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.QFetchNumberLotteryResultData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/23
 */
public class FetchNumberLotteryResultDataInternalServiceTest extends BaseTest {

    @Autowired
    private FetchNumberLotteryResultDataInternalService fetchNumberLotteryResultDataInternalService;

    @Test
    public void testSave() {
        FetchNumberLotteryResultData fetchNumberLotteryResultData = new FetchNumberLotteryResultData();
        fetchNumberLotteryResultData.setLotteryType(LotteryType.FC_SSQ);
        fetchNumberLotteryResultData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryResultData.setFetcherType(FetcherType.F_500W);
        fetchNumberLotteryResultData.setPhase("20170801");
        fetchNumberLotteryResultData.setFetchedTime(LocalDateTime.now());
        fetchNumberLotteryResultData.setResult("1,12,34");
        FetchNumberLotteryResultData fetchResultDataNumberLotteryResult = fetchNumberLotteryResultDataInternalService.save(fetchNumberLotteryResultData);
        Assert.assertNotNull(fetchResultDataNumberLotteryResult);
    }

    @Test
    public void testUpdate() {
        String result = "1,2,4,4";
        FetchNumberLotteryResultData fetchNumberLotteryResultData = new FetchNumberLotteryResultData();
        fetchNumberLotteryResultData.setId(1L);
        fetchNumberLotteryResultData.setResult(result);
        fetchNumberLotteryResultData = fetchNumberLotteryResultDataInternalService.update(fetchNumberLotteryResultData);
        Assert.assertTrue(result.equals(fetchNumberLotteryResultData.getResult()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        FetchNumberLotteryResultData fetchNumberLotteryResultData = fetchNumberLotteryResultDataInternalService.get(id);
        logger.info("根据id[{}]获取开奖结果抓取数据：抓取结果：[{}], 彩期：[{}]", id, fetchNumberLotteryResultData.getResult(), fetchNumberLotteryResultData.getPhase());
        Assert.assertNotNull(fetchNumberLotteryResultData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QFetchNumberLotteryResultData.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QFetchNumberLotteryResultData.createdTime);
        apiRequestPage.addOrder(QFetchNumberLotteryResultData.id);
        ApiResponse<FetchNumberLotteryResultData> numberLotteryFetchResultDataApiResponse = fetchNumberLotteryResultDataInternalService.findAll(apiRequest, apiRequestPage);
        logger.info("查询总数为：{}", numberLotteryFetchResultDataApiResponse.getPageTotal());

        List<FetchNumberLotteryResultData> fetchNumberLotteryResultData = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        fetchNumberLotteryResultData.forEach(numberLotteryFetchResultData -> {
            logger.info("开奖结果抓取彩期：[{}], 彩种:[{}]", numberLotteryFetchResultData.getPhase(), numberLotteryFetchResultData.getLotteryType().getName());
        });
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170801";
        FetchNumberLotteryResultData fetchNumberLotteryResultData = fetchNumberLotteryResultDataInternalService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("根据彩种：[{}], 彩期：[{}],抓取数据来源类型[{}]:获取开奖结果抓取数据，抓取结果：[{}]", LotteryType.TC_DLT, phase, fetchNumberLotteryResultData.getFetcherType().getName(), fetchNumberLotteryResultData.getResult());
        Assert.assertNotNull(fetchNumberLotteryResultData);
    }
}
