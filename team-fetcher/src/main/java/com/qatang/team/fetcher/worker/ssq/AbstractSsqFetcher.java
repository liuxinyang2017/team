package com.qatang.team.fetcher.worker.ssq;

import com.google.common.collect.Maps;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.worker.AbstractNumberLotteryFetcher;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author qatang
 */
public abstract class AbstractSsqFetcher extends AbstractNumberLotteryFetcher {
    protected final LotteryType lotteryType = LotteryType.FC_SSQ;

    protected final static Map<String, String> prizeNameToKeyMap = Maps.newHashMap();
    static {
        prizeNameToKeyMap.put("一等奖", "prize1");
        prizeNameToKeyMap.put("二等奖", "prize2");
        prizeNameToKeyMap.put("三等奖", "prize3");
        prizeNameToKeyMap.put("四等奖", "prize4");
        prizeNameToKeyMap.put("五等奖", "prize5");
        prizeNameToKeyMap.put("六等奖", "prize6");
    }

    @Override
    protected LotteryType getLotteryType() {
        return lotteryType;
    }
}
