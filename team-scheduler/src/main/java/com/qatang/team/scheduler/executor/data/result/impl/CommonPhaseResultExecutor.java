package com.qatang.team.scheduler.executor.data.result.impl;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.service.FetcherLogApiService;
import com.qatang.team.fetcher.service.NumberLotteryFetchResultDataApiService;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import com.qatang.team.fetcher.worker.NumberLotteryFetcherFactory;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.scheduler.exception.SchedulerException;
import com.qatang.team.scheduler.executor.data.result.AbstractPhaseResultExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.time.LocalDateTime;

/**
 * @author qatang
 */
@Component
public class CommonPhaseResultExecutor extends AbstractPhaseResultExecutor {
    private final FetcherDataType fetcherDataType = FetcherDataType.D_RESULT;

    @Autowired
    private FetcherLogApiService fetcherLogApiService;

    @Autowired
    private NumberLotteryFetchResultDataApiService numberLotteryFetchResultDataApiService;

    @Override
    protected FetcherDataType getFetcherDataType() {
        return FetcherDataType.D_RESULT;
    }

    @Override
    protected boolean hasFetched(NumberLotteryData numberLotteryData, FetcherType fetcherType) {
        try {
            NumberLotteryFetchResultData data = numberLotteryFetchResultDataApiService.getByLotteryTypeAndPhaseAndFetcherType(numberLotteryData.getLotteryType(), numberLotteryData.getPhase(), fetcherType);
            if (data != null) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    protected void doFetch(NumberLotteryData numberLotteryData, FetcherType fetcherType, ProxyData proxyData) {
        LotteryType lotteryType = numberLotteryData.getLotteryType();
        String phase = numberLotteryData.getPhase();

        INumberLotteryFetcher numberLotteryFetcher = NumberLotteryFetcherFactory.getFetcher(lotteryType, fetcherType);
        if (numberLotteryFetcher == null) {
            String msg = String.format("数字彩开奖结果抓取定时：未找到(%s)(%s)的开奖结果抓取器", lotteryType.getName(), fetcherType.getName());
            logger.error(msg);
            throw new SchedulerException(msg);
        }
        ProxyInfo proxyInfo = new ProxyInfo(Proxy.Type.HTTP, proxyData.getHost(), proxyData.getPort(), proxyData.getUsername(), proxyData.getPassword());
        try {
            NumberLotteryFetchResult fetchResult = numberLotteryFetcher.fetchResult(phase, proxyInfo);

            NumberLotteryFetchResultData numberLotteryFetchResultData = new NumberLotteryFetchResultData();
            numberLotteryFetchResultData.setLotteryType(lotteryType);
            numberLotteryFetchResultData.setFetcherType(fetcherType);
            numberLotteryFetchResultData.setPhase(phase);
            numberLotteryFetchResultData.setFetchedTime(fetchResult.getFetchedTime());
            numberLotteryFetchResultData.setResult(fetchResult.getResult());
            NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataApiService.create(numberLotteryFetchResultData);
            logger.info(String.format("数字彩开奖结果抓取定时：抓取(%s)(%s)的开奖结果，fetcherType=%s, proxy=%s，result=%s", lotteryType.getName(), phase, fetcherType.getName(), proxyInfo.getUrlStr(), numberLotteryFetchResultDataResult.getResult()));
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
