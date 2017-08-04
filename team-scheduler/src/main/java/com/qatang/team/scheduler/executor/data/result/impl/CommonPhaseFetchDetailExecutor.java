package com.qatang.team.scheduler.executor.data.result.impl;

import com.google.common.collect.Lists;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.*;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataApiService;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import com.qatang.team.fetcher.worker.NumberLotteryFetcherFactory;
import com.qatang.team.fetcher.wrapper.FetchNumberLotteryDetailWrapper;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.scheduler.exception.SchedulerException;
import com.qatang.team.scheduler.executor.data.result.AbstractPhaseFetchDataExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.util.List;

/**
 * @author qatang
 */
@Component("commonPhaseFetchDetailExecutor")
public class CommonPhaseFetchDetailExecutor extends AbstractPhaseFetchDataExecutor {
    private final FetcherDataType fetcherDataType = FetcherDataType.D_DETAIL;

    @Autowired
    private FetchNumberLotteryDetailDataApiService detailDataApiService;

    @Override
    protected FetcherDataType getFetcherDataType() {
        return fetcherDataType;
    }

    @Override
    protected boolean hasFetched(NumberLotteryData numberLotteryData, FetcherType fetcherType) {
        try {
            FetchNumberLotteryDetailData data = detailDataApiService.getByLotteryTypeAndPhaseAndFetcherType(numberLotteryData.getLotteryType(), numberLotteryData.getPhase(), fetcherType);
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
            String msg = String.format("数字彩开奖详情抓取定时：未找到(%s)(%s)的开奖详情抓取器", lotteryType.getName(), fetcherType.getName());
            logger.error(msg);
            throw new SchedulerException(msg);
        }
        ProxyInfo proxyInfo = new ProxyInfo(Proxy.Type.HTTP, proxyData.getHost(), proxyData.getPort(), proxyData.getUsername(), proxyData.getPassword());
        try {
            NumberLotteryFetchResult fetchDetail = numberLotteryFetcher.fetchDetail(phase, proxyInfo);

            FetchNumberLotteryDetailData detailData = new FetchNumberLotteryDetailData();
            detailData.setLotteryType(numberLotteryData.getLotteryType());
            detailData.setFetchedTime(fetchDetail.getFetchedTime());
            detailData.setFetcherType(fetcherType);
            detailData.setPhase(detailData.getPhase());
            detailData.setResult(fetchDetail.getResult());
            detailData.setPoolAmount(fetchDetail.getPoolAmount());
            detailData.setSaleAmount(fetchDetail.getSaleAmount());

            List<NumberLotteryFetchDetail> fetchDetailList = fetchDetail.getDetailList();
            if (fetchDetailList == null || fetchDetailList.isEmpty()) {
                String msg = String.format("数字彩开奖详情抓取定时：未抓取到(%s)(%s)(%s)的开奖详情", lotteryType.getName(), phase, fetcherType.getName());
                logger.error(msg);
                throw new SchedulerException(msg);
            }
            List<FetchNumberLotteryDetailItemData> detailItemDataList = Lists.newArrayList();
            fetchDetailList.forEach(d -> {
                FetchNumberLotteryDetailItemData detailItemData = new FetchNumberLotteryDetailItemData();
                detailItemData.setLotteryType(lotteryType);
                detailItemData.setFetchedTime(fetchDetail.getFetchedTime());
                detailItemData.setFetcherType(fetcherType);
                detailItemData.setPhase(phase);
                detailItemData.setPrizeKey(d.getPrizeKey());
                detailItemData.setPrizeName(d.getPrizeName());
                detailItemData.setPrizeAmount(d.getPrizeAmount());
                detailItemData.setPrizeCount(d.getPrizeCount());
                detailItemDataList.add(detailItemData);
            });

            FetchNumberLotteryDetailWrapper wrapper = new FetchNumberLotteryDetailWrapper();
            wrapper.setFetchNumberLotteryDetailData(detailData);
            wrapper.setFetchNumberLotteryDetailItemDataList(detailItemDataList);

            detailData = detailDataApiService.create(wrapper);
            logger.info(String.format("数字彩开奖详情抓取定时：抓取(%s)(%s)的开奖详情，fetcherType=%s, proxy=%s，poolAmount=%s", lotteryType.getName(), phase, fetcherType.getName(), proxyInfo.getUrlStr(), detailData.getPoolAmount()));
        } catch (Exception e) {
            throw new SchedulerException(e.getMessage());
        }
    }
}
