package com.qatang.team.fetcher.worker.ssq;

import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.worker.AbstractNumberLotteryFetcher;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qatang
 */
public abstract class AbstractSsqFetcher extends AbstractNumberLotteryFetcher {
    protected final LotteryType lotteryType = LotteryType.FC_SSQ;

    @Override
    protected LotteryType getLotteryType() {
        return lotteryType;
    }
}
