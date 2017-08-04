package com.qatang.team.scheduler.executor.data.result.impl;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.service.FetchNumberLotteryResultDataApiService;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import com.qatang.team.fetcher.worker.NumberLotteryFetcherFactory;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.scheduler.exception.SchedulerException;
import com.qatang.team.scheduler.executor.data.result.AbstractPhaseFetchDataExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Proxy;

/**
 * @author qatang
 */
@Component("commonPhaseFetchResultExecutor")
public class CommonPhaseFetchResultExecutor extends AbstractPhaseFetchDataExecutor {
    private final FetcherDataType fetcherDataType = FetcherDataType.D_RESULT;

    @Autowired
    private FetchNumberLotteryResultDataApiService fetchNumberLotteryResultDataApiService;

    @Override
    protected FetcherDataType getFetcherDataType() {
        return fetcherDataType;
    }

    @Override
    protected boolean hasFetched(NumberLotteryData numberLotteryData, FetcherType fetcherType) {
        try {
            FetchNumberLotteryResultData data = fetchNumberLotteryResultDataApiService.getByLotteryTypeAndPhaseAndFetcherType(numberLotteryData.getLotteryType(), numberLotteryData.getPhase(), fetcherType);
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

            FetchNumberLotteryResultData fetchNumberLotteryResultData = new FetchNumberLotteryResultData();
            fetchNumberLotteryResultData.setLotteryType(lotteryType);
            fetchNumberLotteryResultData.setFetcherType(fetcherType);
            fetchNumberLotteryResultData.setPhase(phase);
            fetchNumberLotteryResultData.setFetchedTime(fetchResult.getFetchedTime());
            fetchNumberLotteryResultData.setResult(fetchResult.getResult());
            FetchNumberLotteryResultData fetchResultDataNumberLotteryResult = fetchNumberLotteryResultDataApiService.create(fetchNumberLotteryResultData);
            logger.info(String.format("数字彩开奖结果抓取定时：抓取(%s)(%s)的开奖结果，fetcherType=%s, proxy=%s，result=%s", lotteryType.getName(), phase, fetcherType.getName(), proxyInfo.getUrlStr(), fetchResultDataNumberLotteryResult.getResult()));
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
