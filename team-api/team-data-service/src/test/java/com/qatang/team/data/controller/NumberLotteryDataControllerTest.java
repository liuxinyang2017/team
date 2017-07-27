package com.qatang.team.data.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import java.util.List;

/**
 * @author jinsheng
 */
public class NumberLotteryDataControllerTest extends AbstractControllerTest {

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Test
    public void testSave() {
        NumberLotteryData data = new NumberLotteryData();
        data.setLotteryType(LotteryType.FC_SSQ);
        data.setPhase("2017005");
        data.setPhaseStatus(PhaseStatus.PENDING);
        data.setIsCurrent(YesNoStatus.NO);
        data.setOpenTime(LocalDateTime.now());
        data.setCloseTime(LocalDateTime.now());
        data.setPrizeTime(LocalDateTime.now());
        data.setResult("01,02,03,04,05,06|07");
        data.setResultTime(LocalDateTime.now());
        data.setResultDetailTime(LocalDateTime.now());
        data.setPoolAmount(1000L);
        data.setSaleAmount(2000L);

        data = numberLotteryDataApiService.create(data);
        Assert.assertTrue(data.getId() != null);
    }

    @Test
    public void testUpdate() {
        NumberLotteryData data = numberLotteryDataApiService.get(1L);
        data.setLotteryType(LotteryType.FC_SSQ);
        data.setPhase("2017002");
        data.setPhaseStatus(PhaseStatus.OPEN_NOT);
        data.setIsCurrent(YesNoStatus.YES);
        data.setOpenTime(LocalDateTime.now());
        data.setCloseTime(LocalDateTime.now());
        data.setPrizeTime(LocalDateTime.now());
        data.setResult("01,02,03,04,05,06|07");
        data.setResultTime(LocalDateTime.now());
        data.setResultDetailTime(LocalDateTime.now());
        data.setPoolAmount(1000L);
        data.setSaleAmount(2000L);
        data = numberLotteryDataApiService.update(data);
        Assert.assertTrue(data.getPhaseStatus().equals(PhaseStatus.OPEN_NOT));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryData numberLotteryData = numberLotteryDataApiService.get(id);
        logger.info("根据id[{}]获取数字彩彩果，彩种为：{}，彩期为：{}", id, numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testFindAll() {
        LotteryType lotteryType = LotteryType.FC_SSQ;

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual(QNumberLotteryData.lotteryType, lotteryType);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        apiRequestPage.paging(0, 10);
        apiRequestPage.addOrder(QNumberLotteryData.createdTime);
        apiRequestPage.addOrder(QNumberLotteryData.id);

        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryData> response = numberLotteryDataApiService.findAll(pageableWrapper);
        logger.info("彩种[{}]数字彩彩果总数：{}", lotteryType.getName(), response.getTotal());

        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList(response.getPagedData());
        numberLotteryDataList.forEach(numberLotteryData -> logger.info("彩期：{}", numberLotteryData.getPhase()));
    }

    @Test
    public void testSpecifyCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";
        numberLotteryDataApiService.specifyCurrentPhase(lotteryType, phase);
    }
}
