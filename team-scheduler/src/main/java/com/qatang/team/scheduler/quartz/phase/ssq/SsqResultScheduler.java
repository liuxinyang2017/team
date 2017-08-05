package com.qatang.team.scheduler.quartz.phase.ssq;

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
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.QFetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.service.FetchNumberLotteryResultDataApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 双色球开奖结果录入定时
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.ssq.result.on")
public class SsqResultScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LotteryType lotteryType = LotteryType.FC_SSQ;

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Autowired
    private FetchNumberLotteryResultDataApiService fetchNumberLotteryResultDataApiService;

    @Scheduled(fixedDelay = 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        try {
            logger.info(String.format("双色球开奖结果录入定时：开始处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.CLOSED.getName()));
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                    .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.CLOSED);

            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<NumberLotteryData> numberLotteryDataList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
            if (numberLotteryDataList != null && !numberLotteryDataList.isEmpty()) {
                numberLotteryDataList.forEach(numberLotteryData -> {
                    ApiRequest apiRequest2 = ApiRequest.newInstance()
                            .filterEqual(QFetchNumberLotteryResultData.lotteryType, lotteryType)
                            .filterEqual(QFetchNumberLotteryResultData.phase, numberLotteryData.getPhase());

                    ApiRequestPage apiRequestPage2 = ApiRequestPage.newInstance()
                            .paging(0, 100)
                            .addOrder(QProxyData.id, PageOrderType.ASC);
                    PageableWrapper pageableWrapper2 = new PageableWrapper(apiRequest2, apiRequestPage2);

                    List<FetchNumberLotteryResultData> fetchNumberLotteryResultDataList = ApiPageRequestHelper.request(pageableWrapper2, fetchNumberLotteryResultDataApiService::findAll);
                    if (fetchNumberLotteryResultDataList == null || fetchNumberLotteryResultDataList.isEmpty()) {
                        logger.info(String.format("双色球开奖结果录入定时：未查询到(%s)(%s)的抓取数据，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase()));
                        return;
                    }

                    if (fetchNumberLotteryResultDataList.size() < GlobalConstants.FETCH_DATA_MIN_COUNT) {
                        logger.info(String.format("双色球开奖结果录入定时：(%s)(%s)的抓取数据数量是(%s)，小于最小对比数量(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), fetchNumberLotteryResultDataList.size(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }

                    String result = compareResult(fetchNumberLotteryResultDataList);
                    if (StringUtils.isEmpty(result)) {
                        logger.info(String.format("双色球开奖结果录入定时：(%s)(%s)的抓取数据结果一致的数量小于(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }
                    numberLotteryDataApiService.updateResult(lotteryType, numberLotteryData.getPhase(), result);
                    logger.info(String.format("双色球开奖结果录入定时：(%s)(%s)更新开奖结果成功，result=%s", lotteryType.getName(), numberLotteryData.getPhase(), result));
                });
            } else {
                logger.info(String.format("双色球开奖结果录入定时：未查询到(%s)状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.CLOSED.getName()));
            }
            logger.info(String.format("双色球开奖结果录入定时：结束处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.CLOSED.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String compareResult(List<FetchNumberLotteryResultData> fetchNumberLotteryResultDataList) {
        Map<String, List<FetchNumberLotteryResultData>> map = fetchNumberLotteryResultDataList.stream().collect(Collectors.groupingBy(FetchNumberLotteryResultData::getResult));

        for (String key : map.keySet()) {
            List<FetchNumberLotteryResultData> values = map.get(key);
            if (values.size() >= GlobalConstants.FETCH_DATA_MIN_COUNT) {
                return key;
            }
        }
        return null;
    }
}
