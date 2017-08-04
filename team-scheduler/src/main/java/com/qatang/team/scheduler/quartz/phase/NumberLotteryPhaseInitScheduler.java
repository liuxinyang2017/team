package com.qatang.team.scheduler.quartz.phase;

import com.google.common.collect.Lists;
import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.scheduler.exception.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.number.lottery.phase.init.on")
public class NumberLotteryPhaseInitScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000L, initialDelay = 10 * 1000L)
    public void run() {
        for (LotteryType lotteryType : GlobalConstants.NUMBER_LOTTERY_LIST) {
            logger.info(String.format("数字彩彩期初始化定时：开始执行(%s)的数字彩彩期初始化定时", lotteryType.getName()));
            try {
                //初始化彩期状态和当前期
                {// 将所有待处理的彩期全部改为未开启
                    logger.info(String.format("数字彩彩期初始化定时：开始执行(%s)将所有待处理的彩期全部改为未开启", lotteryType.getName()));
                    ApiRequest apiRequest = ApiRequest.newInstance()
                            .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                            .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.PENDING);
                    ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                            .paging(0, 100)
                            .addOrder(QNumberLotteryData.id, PageOrderType.ASC);
                    PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

                    List<NumberLotteryData> phaseList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
                    if (phaseList != null && !phaseList.isEmpty()) {
                        phaseList.forEach(phase -> {
                            numberLotteryDataApiService.updateStatus(lotteryType, phase.getPhase(), PhaseStatus.OPEN_NOT, PhaseStatus.PENDING);
                        });
                        logger.info(String.format("数字彩彩期初始化定时：查询到(%s)(%s)彩期数据(%s)条，执行将所有待处理的彩期全部改为未开启全部成功。", lotteryType.getName(), PhaseStatus.PENDING.getName(), phaseList.size()));
                    } else {
                        logger.info(String.format("数字彩彩期初始化定时：未查询到(%s)(%s)彩期数据，无需执行将所有待处理的彩期全部改为未开启", lotteryType.getName(), PhaseStatus.PENDING.getName()));
                    }
                    logger.info(String.format("数字彩彩期初始化定时：结束执行(%s)将所有待处理的彩期全部改为未开启", lotteryType.getName()));
                }

                {// 确定当前期
                    NumberLotteryData currentNumberLotteryData = null;
                    try {
                        currentNumberLotteryData = numberLotteryDataApiService.getCurrentPhase(lotteryType);
                    } catch (NumberLotteryDataException e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (currentNumberLotteryData == null) {
                        // 指定当前期
                        logger.info(String.format("数字彩彩期初始化定时：未查询到(%s)当前期，开始指定当前期", lotteryType.getName()));
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
                            String msg = String.format("数字彩彩期初始化定时：未找到(%s)开始时间<%s，结束时间>%s的彩期，无法确认当前期", lotteryType.getName(), CoreDateUtils.formatLocalDateTime(now), CoreDateUtils.formatLocalDateTime(now));
                            logger.error(msg);
                            throw new SchedulerException(msg);
                        }

                        List<NumberLotteryData> list = Lists.newArrayList(response.getPagedData());
                        if (list.size() != 1) {
                            String msg = String.format("数字彩彩期初始化定时：(%s)开始时间<%s，结束时间>%s的彩期，不止1期，无法指定当前期", lotteryType.getName(), CoreDateUtils.formatLocalDateTime(now), CoreDateUtils.formatLocalDateTime(now));
                            logger.error(msg);
                            throw new SchedulerException(msg);
                        }

                        currentNumberLotteryData = list.get(0);
                        if (!currentNumberLotteryData.getPhaseStatus().equals(PhaseStatus.OPEN)) {
                            numberLotteryDataApiService.updateStatus(lotteryType, currentNumberLotteryData.getPhase(), PhaseStatus.OPEN, PhaseStatus.OPEN_NOT);
                        }
                        currentNumberLotteryData = numberLotteryDataApiService.specifyCurrentPhase(lotteryType, currentNumberLotteryData.getPhase());
                        logger.info(String.format("数字彩彩期初始化定时：设定(%s)的彩期(%s)为当前期", lotteryType.getName(), currentNumberLotteryData.getPhase()));
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            logger.info(String.format("数字彩彩期初始化定时：结束执行(%s)的数字彩彩期初始化定时", lotteryType.getName()));
        }
    }
}
