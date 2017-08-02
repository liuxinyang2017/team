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
        // 初始化
        NumberLotteryData numberLotteryData = null;
        try {
            numberLotteryData = numberLotteryDataApiService.get(-1L);
        } catch (NumberLotteryDataException e) {
            logger.error(e.getMessage(), e);
        }
        if (numberLotteryData == null) {
            logger.info(String.format("数字彩彩期生成定时：未查询到(%s)的彩期数据，从当前年份的第1期开始，生成200期数据", lotteryType.getName()));
            PhaseInfo startPhaseInfo = StartPhaseInfoBuilder.build(lotteryType);
            createNumberLotteryData(startPhaseInfo, 200);
            logger.info(String.format("数字彩彩期生成定时：初始化(%s)的彩期数据成功！", lotteryType.getName()));
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
            requestPage.addOrder(QNumberLotteryData.id);

            PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);
            ApiResponse<NumberLotteryData> response = numberLotteryDataApiService.findAll(pageableWrapper);
            if (response == null || response.getPagedData().isEmpty()) {
                String msg = String.format("数字彩彩期生成定时：未找到(%s)开始时间<%s，结束时间>%s的彩期，无法确认当前期", lotteryType.getName(), CoreDateUtils.formatLocalDateTime(now), CoreDateUtils.formatLocalDateTime(now));
                logger.error(msg);
                throw new SchedulerException(msg);
            }

            List<NumberLotteryData> list = Lists.newArrayList(response.getPagedData());
            if (list.size() != 1) {
                String msg = String.format("数字彩彩期生成定时：(%s)开始时间<%s，结束时间>%s的彩期，不止1期，无法指定当前期", lotteryType.getName(), CoreDateUtils.formatLocalDateTime(now), CoreDateUtils.formatLocalDateTime(now));
                logger.error(msg);
                throw new SchedulerException(msg);
            }

            currentNumberLotteryData = list.get(0);
            currentNumberLotteryData = numberLotteryDataApiService.specifyCurrentPhase(lotteryType, currentNumberLotteryData.getPhase());
        }

        // 当前期之后彩期不足10期，创建彩期
        ApiRequest request = ApiRequest.newInstance();
        request.filterEqual(QNumberLotteryData.lotteryType, lotteryType);
        request.filterGreaterThan(QNumberLotteryData.phase, currentNumberLotteryData.getPhase());

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        requestPage.paging(0, 20);
        requestPage.addOrder(QNumberLotteryData.id);

        PageableWrapper pageableWrapper = new PageableWrapper(request, requestPage);
        ApiResponse<NumberLotteryData> response = numberLotteryDataApiService.findAll(pageableWrapper);
        if (response == null || response.getPagedData().isEmpty()) {
            String msg = String.format("数字彩彩期生成定时：(%s)当前期(%s)之后已无彩期，不足10期自动生成逻辑异常，请检查", lotteryType.getName(), currentNumberLotteryData.getPhase());
            logger.error(msg);
            throw new SchedulerException(msg);
        }

        List<NumberLotteryData> list = Lists.newArrayList(response.getPagedData());
        if (list.size() < 10) {
            logger.info(String.format("数字彩彩期生成定时：(%s)当前期(%s)之后的彩期数量不足10期，需要生成新彩期", lotteryType.getName(), currentNumberLotteryData.getPhase()));
            NumberLotteryData lastNumberLotteryData =  list.get(list.size() - 1);

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
