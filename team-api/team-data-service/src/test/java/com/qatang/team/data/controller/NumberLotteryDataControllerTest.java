package com.qatang.team.data.controller;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.assertj.core.util.Lists;
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
    public void testGet() {
        Long id = 1L;
        NumberLotteryData numberLotteryData = numberLotteryDataApiService.get(id);
        logger.info("数字彩彩果[id={}]彩期为：{}", id, numberLotteryData.getPhase());
    }

    @Test
    public void testSpecifyCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";
        numberLotteryDataApiService.specifyCurrentPhase(lotteryType, phase);
    }

    @Test
    public void testFind() {
        PageableWrapper pageableWrapper = new PageableWrapper();
        ApiRequest apiRequest = ApiRequest.newInstance();
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance();
        pageableWrapper.setRequest(apiRequest);
        pageableWrapper.setRequestPage(apiRequestPage);
        ApiResponse<NumberLotteryData> numberLotteryDataApiResponse = numberLotteryDataApiService.find(pageableWrapper);
        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList(numberLotteryDataApiResponse.getPagedData());
        numberLotteryDataList.forEach(numberLotteryData -> {
            System.out.println(numberLotteryData.getPhase());
            System.out.println(numberLotteryData.getResult());
        });
    }

    @Test
    public void testSave() {
        NumberLotteryData numberLotteryData = new NumberLotteryData();
        numberLotteryData.setLotteryType(LotteryType.FC_SSQ);
        numberLotteryData.setPhase("2017004");
        numberLotteryData.setPhaseStatus(PhaseStatus.OPEN_NOT);
        numberLotteryData.setIsCurrent(YesNoStatus.NO);
        numberLotteryData.setCreatedTime(LocalDateTime.now());
        numberLotteryData.setUpdatedTime(LocalDateTime.now());
        numberLotteryDataApiService.create(numberLotteryData);
    }
}
