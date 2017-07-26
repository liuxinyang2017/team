package com.qatang.team.scheduler.executor.generator.phase.impl;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.YesNoStatus;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qatang
 */
@Component
public class NumberLotteryPhaseGeneratorExecutor extends AbstractPhaseGeneratorExecutor {

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Override
    public void executeGenerator() throws SchedulerException {
        for (LotteryType lotteryType : GlobalConstants.NUMBER_LOTTERY_LIST) {
            NumberLotteryData numberLotteryData = numberLotteryDataApiService.get(1L);
            if (numberLotteryData == null) {
                logger.info(String.format("数字彩彩期生成定时：未查询到(%s)的彩期数据，从当前年份的第1期开始，生成400期数据", lotteryType.getName()));
                IPhaseGenerator phaseGenerator = PhaseGeneratorFactory.getPhaseGenerator(lotteryType);
                if (phaseGenerator == null) {
                    String msg = String.format("数字彩彩期生成定时：未找到(%s)的彩期生成器，请检查配置", lotteryType.getName());
                    logger.error(msg);
                    continue;
                }

                PhaseInfo startPhaseInfo = StartPhaseInfoBuilder.build(lotteryType);
                List<PhaseInfo> phaseInfoList;
                try {
                    phaseInfoList = phaseGenerator.generate(startPhaseInfo, 400);
                } catch (GeneratorException e) {
                    logger.error(e.getMessage(), e);
                    continue;
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
                    numberLotteryDataApiService.save(data);
                });
                logger.info(String.format("数字彩彩期生成定时：生成(%s)的彩期数据，从当前年份的第1期开始，生成400期数据成功！", lotteryType.getName()));
            }

            // 确定当前期
            NumberLotteryData currentNumberLotteryData = numberLotteryDataApiService.getCurrentPhase(lotteryType);
            if (currentNumberLotteryData == null) {
                // 指定当前期
                LocalDateTime now = LocalDateTime.now();
                ApiRequest request = ApiRequest.newInstance();
                request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);
                request.filterLessEqual(QNumberLotteryData.openTime, now);
                request.filterGreaterEqual(QNumberLotteryData.closeTime, now);

                ApiRequestPage requestPage = ApiRequestPage.newInstance();
                requestPage.paging(0, 10);
                requestPage.addOrder(QNumberLotteryData.createdTime);
                requestPage.addOrder(QNumberLotteryData.id);
//                ApiResponse response = numberLotteryDataApiService.findAll(request, requestPage);

            }

            // 当前期之后彩期不足10期，创建彩期
            ApiRequest request = ApiRequest.newInstance();
            request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);
            request.filterGreaterThan(QNumberLotteryData.phase, currentNumberLotteryData.getPhase());

            ApiRequestPage requestPage = ApiRequestPage.newInstance();
            requestPage.paging(0, 20);
            requestPage.addOrder(QNumberLotteryData.createdTime);
            requestPage.addOrder(QNumberLotteryData.id);
//                ApiResponse response = numberLotteryDataApiService.findAll(request, requestPage);
        }
    }
}
