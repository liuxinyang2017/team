package com.qatang.team.data.controller;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.lottery.LotteryType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        String phase = "20170101";
        numberLotteryDataApiService.specifyCurrentPhase(lotteryType, phase);
    }
}
