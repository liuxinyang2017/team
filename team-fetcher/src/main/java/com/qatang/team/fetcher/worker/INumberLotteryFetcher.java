package com.qatang.team.fetcher.worker;

import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.NumberLotteryFetcherException;

/**
 * @author qatang
 */
public interface INumberLotteryFetcher {
    /**
     * 抓取开奖号码
     * @param phase 彩期
     * @return 开奖号码
     * @throws NumberLotteryFetcherException 异常
     */
    NumberLotteryFetchResult fetchResult(String phase) throws NumberLotteryFetcherException;

    /**
     * 抓取开奖详情
     * @param phase 彩期
     * @return 开奖详情
     * @throws NumberLotteryFetcherException 异常
     */
    NumberLotteryFetchResult fetchDetail(String phase) throws NumberLotteryFetcherException;
}
