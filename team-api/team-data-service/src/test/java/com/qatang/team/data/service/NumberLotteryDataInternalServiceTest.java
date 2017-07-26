package com.qatang.team.data.service;

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
        NumberLotteryData data = numberLotteryDataInternalService.get(1L);;
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
        logger.info("数字彩彩果[id={}]彩种：{}，彩期：{}", numberLotteryData.getId(), numberLotteryData.getLotteryType().getName(), numberLotteryData.getPhase());
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
        ApiResponse response = numberLotteryDataInternalService.findAll(request, requestPage);

        logger.info("彩种[{}]数字彩彩果总数：{}", lotteryType.getName(), response.getTotal());
    }

    @Test
    public void testGetByLotteryTypeAndPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "2017001";
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
    public void testGetPreviousPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getPreviousPhase(lotteryType);
        logger.info("彩种[{}]当前期的上一期：{}", lotteryType.getName(), numberLotteryData.getPhase());

        String phase = "20170102";
        numberLotteryData = numberLotteryDataInternalService.getPreviousPhase(lotteryType, phase);
        logger.info("彩种[{}][{}]的上一期：{}", lotteryType.getName(), phase, numberLotteryData.getPhase());
    }

    @Test
    public void testGetNextPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getNextPhase(lotteryType);
        logger.info("彩种[{}]当前期的下一期：{}", lotteryType.getName(), numberLotteryData.getPhase());

        String phase = "20170101";
        numberLotteryData = numberLotteryDataInternalService.getNextPhase(lotteryType, phase);
        logger.info("彩种[{}][{}]的下一期：{}", lotteryType.getName(), phase, numberLotteryData.getPhase());
    }

    @Test
    public void testUpdatePhaseStatus() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "";
        PhaseStatus toStatus = PhaseStatus.RESULT_DETAIL_SET;
        PhaseStatus checkStatus = PhaseStatus.RESULT_SET;
        numberLotteryDataInternalService.updatePhaseStatus(lotteryType, phase, toStatus, checkStatus);
    }

    @Test
    public void testSwitchCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        numberLotteryDataInternalService.switchCurrentPhase(lotteryType);
    }

    @Test
    public void testSpecifyCurrentPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        numberLotteryDataInternalService.specifyCurrentPhase(lotteryType, phase);
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.getCurrentPhase(lotteryType);
        logger.info("彩种：{}，当前期：{}", lotteryType.getName(), numberLotteryData.getPhase());
    }

    @Test
    public void testUpdateResult() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        String phase = "20170101";
        String result = "01,02,03,04,05,06|07";

        numberLotteryDataInternalService.updateResult(lotteryType, phase, result);
    }

    @Test
    public void testGetNearestPhase() {
        LotteryType lotteryType = LotteryType.FC_SSQ;
        int prePhases = 10;
        int nextPhases = 10;
        logger.info("彩种：{} 当前期前{}期至后{}期", lotteryType.getName(), prePhases, nextPhases);
        List<NumberLotteryData> numberLotteryDataList = numberLotteryDataInternalService.findNearestPhaseList(lotteryType, prePhases, nextPhases);
        for (NumberLotteryData numberLotteryData : numberLotteryDataList) {
            logger.info("彩期：{}", numberLotteryData.getPhase());
        }
    }
}
