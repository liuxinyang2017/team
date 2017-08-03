package com.qatang.team.scheduler.executor.data.result;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.retrymodel.ApiRetryCallback;
import com.qatang.team.core.retrymodel.ApiRetryTaskExecutor;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.service.FetcherLogApiService;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

/**
 * @author qatang
 */
public abstract class AbstractPhaseResultExecutor implements IPhaseResultExecutor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ProxyDataApiService proxyDataApiService;

    @Autowired
    protected FetcherLogApiService fetcherLogApiService;

    protected abstract FetcherDataType getFetcherDataType();

    /**
     * 是否已抓取成功
     * @param numberLotteryData 彩期数据
     * @param fetcherType 抓取类型
     * @return 是否已抓取成功
     */
    protected abstract boolean hasFetched(NumberLotteryData numberLotteryData, FetcherType fetcherType);

    @Override
    public void executeFetcher(NumberLotteryData numberLotteryData) {
        if (numberLotteryData == null || !numberLotteryData.getPhaseStatus().equals(PhaseStatus.CLOSED)) {
            logger.info(String.format("数字彩开奖结果抓取定时：彩期数据为空或彩期数据测试状态不是(%s)", PhaseStatus.CLOSED.getName()));
            return;
        }

        // 按分数降序，id升序，获取10个可用代理列表
        ApiRequest apiRequest = ApiRequest.newInstance()
                .filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.PASS);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, 10)
                .addOrder(QProxyData.score, PageOrderType.DESC)
                .addOrder(QProxyData.id, PageOrderType.ASC);
        PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

        List<ProxyData> proxyDataList = ApiPageRequestHelper.request(pageableWrapper, proxyDataApiService::findAll);
        if (proxyDataList == null || proxyDataList.isEmpty()) {
            logger.info(String.format("数字彩开奖结果抓取定时：抓取(%s)结果未找到可用代理列表", numberLotteryData.getLotteryType()));
            return;
        }

        // 随机排序
        Collections.shuffle(proxyDataList);

        // 获取可用fetcherType列表
        for (FetcherType fetcherType : GlobalConstants.FETCHER_TYPE_LIST) {
            if (hasFetched(numberLotteryData, fetcherType)) {
                continue;
            }

            for (ProxyData proxyData : proxyDataList) {
                // 重试3次，每次间隔1秒
                boolean fetched = true;
                LocalDateTime begin = LocalDateTime.now();
                YesNoStatus success = YesNoStatus.YES;
                String message = "抓取结果成功";
                try {
                    ApiRetryTaskExecutor.invoke(new ApiRetryCallback<Void>() {

                        @Override
                        protected Void execute() throws Exception {
                            doFetch(numberLotteryData, fetcherType, proxyData);
                            return null;
                        }
                    });
                } catch (Exception e) {
                    fetched = false;
                    success = YesNoStatus.NO;
                    message = "抓取结果失败";
                    logger.error(e.getMessage(), e);
                }
                fetcherLogApiService.create(generateFetcherLog(numberLotteryData, proxyData, fetcherType, begin, success, message));
                logger.info(String.format("数字彩开奖结果抓取定时：抓取日志入库成功，lotteryType=%s，phase=%s，fetcherType=%s", numberLotteryData.getLotteryType(), numberLotteryData.getPhase(), fetcherType.getName()));

                if (fetched) {
                    // 加分
                    break;
                }
            }
        }
    }

    private FetcherLog generateFetcherLog(NumberLotteryData numberLotteryData, ProxyData proxyData, FetcherType fetcherType, LocalDateTime begin, YesNoStatus success, String message) {
        LocalDateTime end = LocalDateTime.now();

        FetcherLog fetcherLog = new FetcherLog();
        fetcherLog.setBeginFetchTime(begin);
        fetcherLog.setEndFetchTime(end);
        fetcherLog.setFetcherType(fetcherType);
        fetcherLog.setProxyId(proxyData.getId());
        fetcherLog.setHost(proxyData.getHost());
        fetcherLog.setPort(proxyData.getPort());
        fetcherLog.setMessage(message);
        fetcherLog.setSpentMills(begin.until(end, ChronoUnit.MILLIS));
        fetcherLog.setSuccess(success);
        fetcherLog.setLotteryType(numberLotteryData.getLotteryType());
        fetcherLog.setPhase(numberLotteryData.getPhase());
        fetcherLog.setFetcherDataType(getFetcherDataType());
        return fetcherLog;
    }

    protected abstract void doFetch(NumberLotteryData numberLotteryData, FetcherType fetcherType, ProxyData proxyData);
}
