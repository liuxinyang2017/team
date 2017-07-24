package com.qatang.team.data.service;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jinsheng
 */
public class NumberLotteryDataInternalServiceTest extends BaseInternalServiceTest{

    @Autowired
    private NumberLotteryDataInternalService numberLotteryDataInternalService;

    @Test
    public void testSave() {
        NumberLotteryData numberLotteryData = new NumberLotteryData();
        numberLotteryData.setId(1L);
        numberLotteryData.setLotteryType(LotteryType.FC_SSQ);
        numberLotteryData.setPhase("20170101");
        numberLotteryData.setPhaseStatus(PhaseStatus.RESULT_SET);
        numberLotteryData.setIsCurrent(YesNoStatus.NO);
        numberLotteryData.setResult("01,02,03,04,05,06|07");
        numberLotteryData.setPoolAmount(1000L);
        numberLotteryData.setSaleAmount(1000L);
        numberLotteryDataInternalService.save(numberLotteryData);
    }

    @Test
    public void testUpdate() {
        NumberLotteryData numberLotteryData = new NumberLotteryData();
        numberLotteryData.setId(1L);
        numberLotteryData.setSaleAmount(2000L);
        numberLotteryData.setPoolAmount(2000L);
        numberLotteryData = numberLotteryDataInternalService.update(numberLotteryData);
        logger.info("数字彩彩果[id={}]销售总金额：{} 分，奖池金额：{} 分", numberLotteryData.getId(), numberLotteryData.getSaleAmount(), numberLotteryData.getPoolAmount());
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.get(id);
        logger.info("数字彩彩果[id={}]彩种：{}，彩期：{}", numberLotteryData.getId(), numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
        logger.info("数字彩彩果[id={}]彩种：{}，彩期：{}", numberLotteryData.getId(), numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testGetCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("数字彩彩果[id={}]彩种：{}，彩期：{}", numberLotteryData.getId(), numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testUpdatePhaseStatus() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "";
        PhaseStatus toStatus = PhaseStatus.RESULT_DETAIL_SET;
        PhaseStatus checkStatus = PhaseStatus.RESULT_SET;
        numberLotteryDataInternalService.updatePhaseStatus(lotteryType, phase, toStatus, checkStatus);
    }
}
