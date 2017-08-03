package com.qatang.team.scheduler.quartz.data.ssq;

import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.scheduler.executor.proxy.validator.IProxyValidatorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 双色球开奖结果抓取定时
 * @author qatang
 */
@Component
public class SsqResultScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LotteryType lotteryType = LotteryType.FC_SSQ;
    private final FetcherDataType fetcherDataType = FetcherDataType.D_RESULT;

    private final IProxyValidatorExecutor proxyValidatorExecutor;

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    public SsqResultScheduler(@Qualifier("commonPhaseResultExecutor") IProxyValidatorExecutor proxyValidatorExecutor) {
        this.proxyValidatorExecutor = proxyValidatorExecutor;
    }

//    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        try {
            logger.info(String.format("双色球开奖结果抓取定时：开始处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.CLOSED.getName()));
            // 查询所有状态为 待处理 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                    .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.CLOSED);

            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<NumberLotteryData> numberLotteryDataList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
            if (numberLotteryDataList != null && !numberLotteryDataList.isEmpty()) {

            } else {
                logger.info(String.format("双色球开奖结果抓取定时：未查询到状态为(%s)的代理数据", ProxyValidateStatus.WAITING_TEST.getName()));
            }
            logger.info(String.format("双色球开奖结果抓取定时：结束处理所有状态为(%s)的代理数据", ProxyValidateStatus.WAITING_TEST.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
