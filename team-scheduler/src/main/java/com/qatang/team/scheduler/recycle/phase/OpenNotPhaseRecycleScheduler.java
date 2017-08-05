package com.qatang.team.scheduler.recycle.phase;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.open.not.phase.recycle.on")
public class OpenNotPhaseRecycleScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        for (LotteryType lotteryType : GlobalConstants.NUMBER_LOTTERY_LIST) {
            logger.info(String.format("待处理彩期回收定时：开始执行(%s)的待处理彩期回收定时", lotteryType.getName()));
            try {
                //处理彩期
                {// 将之前所有未开启的彩期全部回收
                    logger.info(String.format("待处理彩期回收定时：开始执行(%s)将之前所有未开启的彩期全部回收", lotteryType.getName()));
                    NumberLotteryData currentPhase = numberLotteryDataApiService.getCurrentPhase(lotteryType);
                    ApiRequest apiRequest = ApiRequest.newInstance()
                            .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                            .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.OPEN_NOT)
                            .filterLessThan(QNumberLotteryData.closeTime, currentPhase.getCloseTime());
                    ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                            .paging(0, 100)
                            .addOrder(QNumberLotteryData.id, PageOrderType.ASC);
                    PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

                    List<NumberLotteryData> phaseList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
                    if (phaseList != null && !phaseList.isEmpty()) {
                        phaseList.forEach(phase -> {
                            numberLotteryDataApiService.updateStatus(lotteryType, phase.getPhase(), PhaseStatus.CLOSED, PhaseStatus.OPEN_NOT);
                        });
                        logger.info(String.format("待处理彩期回收定时：查询到(%s)(%s)彩期数据(%s)条，执行将之前所有未开启的彩期全部回收全部成功。", lotteryType.getName(), PhaseStatus.PENDING.getName(), phaseList.size()));
                    } else {
                        logger.info(String.format("待处理彩期回收定时：未查询到(%s)(%s)彩期数据，无需执行将之前所有未开启的彩期全部回收", lotteryType.getName(), PhaseStatus.PENDING.getName()));
                    }
                    logger.info(String.format("待处理彩期回收定时：结束执行(%s)将之前所有未开启的彩期全部回收", lotteryType.getName()));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            logger.info(String.format("待处理彩期回收定时：结束执行(%s)的待处理彩期回收定时", lotteryType.getName()));
        }
    }
}
