package com.qatang.team.data.controller;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
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
    public void testSpecifyCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";
        numberLotteryDataApiService.specifyCurrentPhase(lotteryType, phase);
    }

    @Test
    public void testFind() {
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);
        ApiResponse<NumberLotteryData> numberLotteryDataApiResponse = numberLotteryDataApiService.find(pageableWrapper);
        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList(numberLotteryDataApiResponse.getPagedData());
        numberLotteryDataList.forEach(numberLotteryData -> {
            System.out.println(numberLotteryData.getPhase());
            System.out.println(numberLotteryData.getResult());
        });
    }

}
