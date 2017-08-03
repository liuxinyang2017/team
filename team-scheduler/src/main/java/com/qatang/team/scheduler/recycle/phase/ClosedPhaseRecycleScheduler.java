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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qatang
 */
@Component
public class ClosedPhaseRecycleScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        for (LotteryType lotteryType : GlobalConstants.NUMBER_LOTTERY_LIST) {
            logger.info(String.format("已关闭彩期回收定时：开始执行(%s)的已关闭彩期回收定时", lotteryType.getName()));
            try {
                //处理彩期
                {// 将20天以前未获取到开奖结果的彩期置为不可用
                    logger.info(String.format("已关闭彩期回收定时：开始执行(%s)将20天以前未获取到开奖结果的彩期置为不可用", lotteryType.getName()));
                    NumberLotteryData currentPhase = numberLotteryDataApiService.getCurrentPhase(lotteryType);
                    ApiRequest apiRequest = ApiRequest.newInstance()
                            .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                            .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.CLOSED)
                            .filterLessThan(QNumberLotteryData.openTime, currentPhase.getOpenTime().minusDays(20));
                    ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                            .paging(0, 100)
                            .addOrder(QNumberLotteryData.id, PageOrderType.ASC);
                    PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

                    List<NumberLotteryData> phaseList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
                    if (phaseList != null && !phaseList.isEmpty()) {
                        phaseList.forEach(phase -> {
                            numberLotteryDataApiService.updateStatus(lotteryType, phase.getPhase(), PhaseStatus.DISABLED, PhaseStatus.CLOSED);
                        });
                        logger.info(String.format("已关闭彩期回收定时：查询到(%s)(%s)彩期数据(%s)条，成功执行将20天以前未获取到开奖结果的彩期置为不可用。", lotteryType.getName(), PhaseStatus.CLOSED.getName(), phaseList.size()));
                    } else {
                        logger.info(String.format("已关闭彩期回收定时：未查询到(%s)(%s)彩期数据，无需执行将20天以前未获取到开奖结果的彩期置为不可用", lotteryType.getName(), PhaseStatus.CLOSED.getName()));
                    }
                    logger.info(String.format("已关闭彩期回收定时：结束执行(%s)将20天以前未获取到开奖结果的彩期置为不可用", lotteryType.getName()));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            logger.info(String.format("已关闭彩期回收定时：结束执行(%s)将20天以前未获取到开奖结果的彩期置为不可用", lotteryType.getName()));
        }
    }
}
