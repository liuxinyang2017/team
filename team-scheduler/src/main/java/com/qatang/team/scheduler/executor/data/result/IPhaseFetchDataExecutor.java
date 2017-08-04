package com.qatang.team.scheduler.executor.data.result;

import com.qatang.team.data.bean.NumberLotteryData;

/**
 * @author qatang
 */
public interface IPhaseFetchDataExecutor {
    /**
     * 执行数字彩开奖结果抓取
     */
    void executeFetcher(NumberLotteryData numberLotteryData);
}
