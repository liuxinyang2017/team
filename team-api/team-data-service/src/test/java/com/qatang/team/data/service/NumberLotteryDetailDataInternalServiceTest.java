package com.qatang.team.data.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.BaseTest;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.bean.QNumberLotteryDetailData;
import com.qatang.team.enums.lottery.LotteryType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 */
public class NumberLotteryDetailDataInternalServiceTest extends BaseTest {
    
    @Autowired
    private NumberLotteryDetailDataInternalService numberLotteryDetailDataInternalService;
    
    @Test
    public void testBatchSave() {
        Long lotteryDataId = 1L;
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";

        NumberLotteryDetailData data1 = new NumberLotteryDetailData();
        data1.setLotteryDataId(lotteryDataId);
        data1.setLotteryType(lotteryType);
        data1.setPhase(phase);
        data1.setPrizeKey("prizeKey1");
        data1.setPrizeName("prizeName1");
        data1.setPrizeCount(1000L);
        data1.setPrizeAmount(2000L);
        data1.setPriority(1);

        List<NumberLotteryDetailData> numberLotteryDetailDataList = Lists.newArrayList(data1);
        List<NumberLotteryDetailData> numberLotteryDetailDataListResult = numberLotteryDetailDataInternalService.batchSave(numberLotteryDetailDataList);
        logger.info("批量新建数字彩开奖详情，总数：{}", numberLotteryDetailDataListResult.size());
        numberLotteryDetailDataListResult.forEach(data -> logger.info("数字彩开奖详情，id：{}，prizeKey：{}，prizeName：{}", data.getId(), data.getPrizeKey(), data.getPrizeName()));
    }

    @Test
    public void testUpdate() {
        NumberLotteryDetailData data = numberLotteryDetailDataInternalService.get(1L);
        data.setLotteryDataId(2L);
        data.setLotteryType(LotteryType.FC_SSQ);
        data.setPhase("2017002");
        data.setPrizeKey("prizeKey2");
        data.setPrizeName("prizeName2");
        data.setPrizeCount(2000L);
        data.setPrizeAmount(4000L);
        data.setPriority(2);
        data = numberLotteryDetailDataInternalService.update(data);
        Assert.assertTrue(Objects.equals(data.getPrizeName(), "prizeName2"));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryDetailData numberLotteryDetailData = numberLotteryDetailDataInternalService.get(id);
        logger.info("根据id[{}]获取数字彩开奖详情，开奖结果对象编码：{}", id, numberLotteryDetailData.getLotteryDataId());
    }

    @Test
    public void testFindAll() {
        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QNumberLotteryDetailData.lotteryDataId, 1L);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 10);
        requestPage.addOrder(QNumberLotteryDetailData.createdTime);
        requestPage.addOrder(QNumberLotteryDetailData.id);
        ApiResponse<NumberLotteryDetailData> response = numberLotteryDetailDataInternalService.findAll(request, requestPage);
        logger.info("数字彩开奖详情总数：{}", response.getTotal());

        List<NumberLotteryDetailData> numberLotteryDetailDataList = Lists.newArrayList(response.getPagedData());
        numberLotteryDetailDataList.forEach(data -> logger.info("数字彩开奖详情，id：{}，prizeKey：{}，prizeName：{}", data.getId(), data.getPrizeKey(), data.getPrizeName()));
    }
}
