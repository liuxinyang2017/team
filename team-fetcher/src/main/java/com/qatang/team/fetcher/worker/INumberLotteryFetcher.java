package com.qatang.team.fetcher.worker;

import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.FetcherException;

/**
 * @author qatang
 */
public interface INumberLotteryFetcher {
    /**
     * 抓取开奖号码
     * @param phase 彩期
     * @return 开奖号码
     * @throws FetcherException 异常
     */
    NumberLotteryFetchResult fetchResult(String phase) throws FetcherException;

    /**
     * 抓取开奖详情
     * @param phase 彩期
     * @return 开奖详情
     * @throws FetcherException 异常
     */
    NumberLotteryFetchResult fetchDetail(String phase) throws FetcherException;
}
