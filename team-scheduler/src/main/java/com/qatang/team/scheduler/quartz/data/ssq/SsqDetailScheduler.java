package com.qatang.team.scheduler.quartz.data.ssq;

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
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.scheduler.executor.data.result.IPhaseFetchDataExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 双色球开奖详情抓取定时
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.ssq.detail.on")
public class SsqDetailScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LotteryType lotteryType = LotteryType.FC_SSQ;

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Autowired
    @Qualifier("commonPhaseFetchDetailExecutor")
    private IPhaseFetchDataExecutor commonPhaseFetchDetailExecutor;

    @Scheduled(fixedDelay = 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        try {
            logger.info(String.format("双色球开奖详情抓取定时：开始处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
            // 查询所有状态为 待处理 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                    .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.RESULT_SET);

            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<NumberLotteryData> numberLotteryDataList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
            if (numberLotteryDataList != null && !numberLotteryDataList.isEmpty()) {
                numberLotteryDataList.forEach(numberLotteryData -> commonPhaseFetchDetailExecutor.executeFetcher(numberLotteryData));
            } else {
                logger.info(String.format("双色球开奖详情抓取定时：未查询到(%s)状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
            }
            logger.info(String.format("双色球开奖详情抓取定时：结束处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
