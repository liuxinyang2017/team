package com.qatang.team.data.service;

import com.google.common.collect.Lists;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.BaseTest;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
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
public class NumberLotteryDataInternalServiceTest extends BaseTest {

    @Autowired
    private NumberLotteryDataInternalService numberLotteryDataInternalService;

    @Test
    public void testSave() {
        NumberLotteryData data = new NumberLotteryData();
        data.setLotteryType(LotteryType.FC_SSQ);
        data.setPhase("2017003");
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
        data = numberLotteryDataInternalService.save(data);
        Assert.assertTrue(data.getId() != null);
    }

    @Test
    public void testUpdate() {
        NumberLotteryData data = numberLotteryDataInternalService.get(1L);
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
        data = numberLotteryDataInternalService.update(data);
        Assert.assertTrue(data.getPhaseStatus().equals(PhaseStatus.OPEN_NOT));
    }

    @Test
    public void testGet() {
        Long id = 1L;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.get(id);
        logger.info("根据id[{}]获取数字彩彩果，彩种为：{}，彩期为：{}", id, numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testFindAll() {
        LotteryType lotteryType = LotteryType.FC_SSQ;

        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 10);
        requestPage.addOrder(QNumberLotteryData.createdTime);
        requestPage.addOrder(QNumberLotteryData.id);
        ApiResponse<NumberLotteryData> response = numberLotteryDataInternalService.findAll(request, requestPage);
        logger.info("彩种[{}]数字彩彩果总数：{}", lotteryType.getName(), response.getTotal());

        List<NumberLotteryData> numberLotteryDataList = Lists.newArrayList(response.getPagedData());
        numberLotteryDataList.forEach(numberLotteryData -> logger.info("彩期：{}", numberLotteryData.getPhase()));
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
        logger.info("根据彩种[{}]、彩期[{}]获取数字彩彩果id为：{}", lotteryType.getName(), phase, numberLotteryData.getId());
    }

    @Test
    public void testGetCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("彩种[{}]当前期：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testGetPreviousPhaseByLotteryType() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getPreviousPhase(lotteryType);
        logger.info("彩种[{}]当前期的上一期为：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testGetPreviousPhaseByLotteryTypeAndPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170102";
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getPreviousPhase(lotteryType, phase);
        logger.info("彩种[{}]彩期[{}]的上一期为：{}", lotteryType.getName(), phase, numberLotteryData.getPhase());
    }

    @Test
    public void testGetNextPhaseByLotteryType() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getNextPhase(lotteryType);
        logger.info("彩种[{}]当前期的下一期为：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testGetNextPhaseByLotteryTypeAndPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getNextPhase(lotteryType, phase);
        logger.info("彩种[{}]彩期[{}]的下一期为：{}", lotteryType.getName(), phase, numberLotteryData.getPhase());
    }

    @Test
    public void testUpdatePhaseStatus() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        PhaseStatus toStatus = PhaseStatus.RESULT_DETAIL_SET;
        PhaseStatus checkStatus = PhaseStatus.RESULT_SET;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.updatePhaseStatus(lotteryType, phase, toStatus, checkStatus);
        logger.info("[{}][{}]的彩期状态为：{}", lotteryType.getName(), phase, numberLotteryData.getPhaseStatus().getName());
    }

    @Test
    public void testSwitchCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;

        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("切换当前期前，彩种[{}]当前期为：{}", lotteryType.getName(), numberLotteryData.getPhase());

        numberLotteryDataInternalService.switchCurrentPhase(lotteryType);

        numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("切换当前期后，彩种[{}]当前期为：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testSpecifyCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        numberLotteryDataInternalService.specifyCurrentPhase(lotteryType, phase);
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("彩种[{}]当前期为：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testUpdateResult() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        String result = "01,02,03,04,05,06|07";
        numberLotteryDataInternalService.updateResult(lotteryType, phase, result);
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
        logger.info("[{}][{}]开奖结果为：{}", lotteryType.getName(), phase, numberLotteryData.getResult());
    }

    @Test
    public void testFindNearestPhaseList() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        int prePhases = 10;
        int nextPhases = 10;
        logger.info("彩种：{} 当前期前{}期至后{}期", lotteryType.getName(), prePhases, nextPhases);
        List<NumberLotteryData> numberLotteryDataList = numberLotteryDataInternalService.findNearestPhaseList(lotteryType, prePhases, nextPhases);
        numberLotteryDataList.forEach(numberLotteryData -> logger.info("彩期：{}", numberLotteryData.getPhase()));
    }

    @Test
    public void testFindPreviousPhaseList() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170102";
        int n = 10;
        logger.info("[{}][{}]前{}期：", lotteryType.getName(), phase, n);
        List<NumberLotteryData> numberLotteryDataList = numberLotteryDataInternalService.findPreviousPhaseList(lotteryType, phase, n);
        numberLotteryDataList.forEach(numberLotteryData -> logger.info("彩期：{}", numberLotteryData.getPhase()));
    }

    @Test
    public void testFindNextPhaseList() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        int n = 10;
        logger.info("[{}][{}]后{}期：", lotteryType.getName(), phase, n);
        List<NumberLotteryData> numberLotteryDataList = numberLotteryDataInternalService.findNextPhaseList(lotteryType, phase, n);
        numberLotteryDataList.forEach(numberLotteryData -> logger.info("彩期：{}", numberLotteryData.getPhase()));
    }
}
