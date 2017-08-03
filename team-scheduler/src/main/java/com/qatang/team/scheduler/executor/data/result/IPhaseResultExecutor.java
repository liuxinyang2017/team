package com.qatang.team.scheduler.executor.data.result;

import com.qatang.team.data.bean.NumberLotteryData;

/**
 * @author qatang
 */
public interface IPhaseResultExecutor {
    /**
     * 执行数字彩开奖结果抓取
     */
    void executeFetcher(NumberLotteryData numberLotteryData);
}
