package com.qatang.team.scheduler.executor.generator.phase.impl;

import com.google.common.collect.Lists;
import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.generator.phase.bean.PhaseInfo;
import com.qatang.team.generator.phase.exception.GeneratorException;
import com.qatang.team.generator.phase.worker.IPhaseGenerator;
import com.qatang.team.generator.phase.worker.PhaseGeneratorFactory;
import com.qatang.team.scheduler.exception.SchedulerException;
import com.qatang.team.scheduler.executor.generator.phase.AbstractPhaseGeneratorExecutor;
import com.qatang.team.scheduler.executor.generator.phase.StartPhaseInfoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author qatang
 */
@Component
@Qualifier("numberLotteryPhaseGeneratorExecutor")
public class NumberLotteryPhaseGeneratorExecutor extends AbstractPhaseGeneratorExecutor {

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Override
    public void executeGenerator() throws SchedulerException {
        for (LotteryType lotteryType : GlobalConstants.NUMBER_LOTTERY_LIST) {
            logger.info(String.format("数字彩彩期生成定时：开始执行(%s)的彩期数据生成", lotteryType.getName()));
            try {
                generate(lotteryType);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            logger.info(String.format("数字彩彩期生成定时：结束执行(%s)的彩期数据生成", lotteryType.getName()));
        }
    }

    private void generate(LotteryType lotteryType) {
        {// 初始化创建彩期
            ApiRequest request = ApiRequest.newInstance();
            request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);

            ApiRequestPage requestPage = ApiRequestPage.newInstance();
            requestPage.paging(0, 1);
            requestPage.addOrder(QNumberLotteryData.id);

            PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);
            ApiResponse<NumberLotteryData> response = numberLotteryDataApiService.findAll(pageableWrapper);
            if (response == null || response.getPagedData().isEmpty()) {
                logger.info(String.format("数字彩彩期生成定时：未查询到(%s)的彩期数据，从当前年份的第1期开始，生成200期数据", lotteryType.getName()));
                PhaseInfo startPhaseInfo = StartPhaseInfoBuilder.build(lotteryType);
                createNumberLotteryData(startPhaseInfo, 92);
                logger.info(String.format("数字彩彩期生成定时：初始化(%s)的彩期数据成功！", lotteryType.getName()));
            }
        }

        // 当前时间+20天>最后一期的结束时间，创建彩期
        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 1);
        requestPage.addOrder(QNumberLotteryData.id, PageOrderType.DESC);

        PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);
        ApiResponse<NumberLotteryData> response = numberLotteryDataApiService.findAll(pageableWrapper);
        if (response == null || response.getPagedData().isEmpty()) {
            String msg = String.format("数字彩彩期生成定时：(%s)彩期不存在，自动生成彩期逻辑异常，请检查", lotteryType.getName());
            logger.error(msg);
            throw new SchedulerException(msg);
        }

        LocalDateTime now = LocalDateTime.now();
        NumberLotteryData lastNumberLotteryData = response.getPagedData().iterator().next();
        if (now.until(lastNumberLotteryData.getCloseTime(), ChronoUnit.DAYS) < 20) {
            logger.info(String.format("数字彩彩期生成定时：(%s)当前时间+20天>最后一期的结束时间，需要生成新彩期", lotteryType.getName()));

            PhaseInfo startPhaseInfo = new PhaseInfo();
            startPhaseInfo.setLotteryType(lotteryType);
            startPhaseInfo.setPhase(lastNumberLotteryData.getPhase());
            startPhaseInfo.setOpenTime(lastNumberLotteryData.getOpenTime());
            startPhaseInfo.setCloseTime(lastNumberLotteryData.getCloseTime());
            startPhaseInfo.setPrizeTime(lastNumberLotteryData.getPrizeTime());

            createNumberLotteryData(startPhaseInfo, 100);
        } else {
            logger.info(String.format("数字彩彩期生成定时：(%s)彩期数量充足，无需生成新彩期", lotteryType.getName()));
        }
    }

    private void createNumberLotteryData(PhaseInfo startPhaseInfo, int count) {
        LotteryType lotteryType = startPhaseInfo.getLotteryType();
        String phase = startPhaseInfo.getPhase();
        IPhaseGenerator phaseGenerator = PhaseGeneratorFactory.getPhaseGenerator(lotteryType);
        if (phaseGenerator == null) {
            String msg = String.format("数字彩彩期生成定时：未找到(%s)的彩期生成器，请检查配置", lotteryType.getName());
            logger.error(msg);
            throw new SchedulerException(msg);
        }

        List<PhaseInfo> phaseInfoList;
        try {
            phaseInfoList = phaseGenerator.generate(startPhaseInfo, count);
        } catch (GeneratorException e) {
            logger.error(e.getMessage(), e);
            throw new SchedulerException(e.getMessage());
        }

        phaseInfoList.forEach(phaseInfo -> {
            NumberLotteryData data = new NumberLotteryData();
            data.setLotteryType(lotteryType);
            data.setPhase(phaseInfo.getPhase());
            data.setPhaseStatus(PhaseStatus.PENDING);
            data.setIsCurrent(YesNoStatus.NO);
            data.setOpenTime(phaseInfo.getOpenTime());
            data.setCloseTime(phaseInfo.getCloseTime());
            data.setPrizeTime(phaseInfo.getPrizeTime());
            numberLotteryDataApiService.create(data);
        });
        logger.info(String.format("数字彩彩期生成定时：生成(%s)的彩期数据，从第%s期开始，生成%s期数据成功！", lotteryType.getName(), phase, count));
    }
}
