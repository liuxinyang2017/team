package com.qatang.team.fetcher.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.BaseTest;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchResultData;
import com.qatang.team.fetcher.config.InitConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wp
 * @since 2017/7/23
 */
public class NumberLotteryFetchResultDataInternalServiceTest extends BaseTest {

    @Autowired
    private  NumberLotteryFetchResultDataInternalService numberLotteryFetchResultDataInternalService;

    @Test
    public void testSave() {
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setLotteryType(LotteryType.FC_SSQ);
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setFetcherType(FetcherType.F_500W);
        numberLotteryFetchResultData.setPhase("20170801");
        numberLotteryFetchResultData.setFetchedTime(LocalDateTime.now());
        numberLotteryFetchResultData.setPoolAmount(10000L);
        numberLotteryFetchResultData.setResult("1,12,34");
        numberLotteryFetchResultData.setSaleAmount(100L);
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataInternalService.save(numberLotteryFetchResultData);
        Assert.assertNotNull(numberLotteryFetchResultDataResult);
    }

    @Test
    public void testUpdate() {
        String result = "1,2,4,4";
        NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
        numberLotteryFetchResultData.setId(1L);
        numberLotteryFetchResultData.setResult(result);
        numberLotteryFetchResultData = numberLotteryFetchResultDataInternalService.update(numberLotteryFetchResultData);
        Assert.assertTrue(result.equals(numberLotteryFetchResultData.getResult()));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataInternalService.get(id);
        logger.info("根据id[{}]获取开奖结果抓取数据：抓取结果：[{}], 彩期：[{}]", id, numberLotteryFetchResultData.getResult(), numberLotteryFetchResultData.getPhase());
        Assert.assertNotNull(numberLotteryFetchResultData);
    }

    @Test
    public void testFindAll() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QNumberLotteryFetchResultData.id, 1L);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QNumberLotteryFetchResultData.createdTime);
        apiRequestPage.addOrder(QNumberLotteryFetchResultData.id);
        ApiResponse<NumberLotteryFetchResultData> numberLotteryFetchResultDataApiResponse = numberLotteryFetchResultDataInternalService.findAll(apiRequest, apiRequestPage);
        logger.info("查询总数为：{}", numberLotteryFetchResultDataApiResponse.getPageTotal());

        List<NumberLotteryFetchResultData> numberLotteryFetchResultDatas = Lists.newArrayList(numberLotteryFetchResultDataApiResponse.getPagedData());
        numberLotteryFetchResultDatas.forEach(numberLotteryFetchResultData -> {
            logger.info("开奖结果抓取彩期：[{}], 彩种:[{}]", numberLotteryFetchResultData.getPhase(), numberLotteryFetchResultData.getLotteryType().getName());
        });
    }

    @Test
    public void testGetByLotteryTypeAndPhaseAndFetcherType() {
        String phase = "20170801";
        NumberLotteryFetchResultData numberLotteryFetchResultData = numberLotteryFetchResultDataInternalService.getByLotteryTypeAndPhaseAndFetcherType(LotteryType.FC_SSQ, phase, FetcherType.F_500W);
        logger.info("根据彩种：[{}], 彩期：[{}],抓取数据来源类型[{}]:获取开奖结果抓取数据，抓取结果：[{}]", LotteryType.TC_DLT, phase, numberLotteryFetchResultData.getFetcherType().getName(), numberLotteryFetchResultData.getResult());
        Assert.assertNotNull(numberLotteryFetchResultData);
    }
}
