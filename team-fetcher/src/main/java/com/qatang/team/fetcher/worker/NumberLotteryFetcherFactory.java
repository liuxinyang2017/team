package com.qatang.team.fetcher.worker;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.worker.ssq.impl.SsqNeteaseFetcher;
import com.qatang.team.fetcher.worker.ssq.impl.SsqOfficialFetcher;

/**
 * @author qatang
 */
public class NumberLotteryFetcherFactory {

    private static Table<LotteryType, FetcherType, INumberLotteryFetcher> numberLotteryFetcherTable = HashBasedTable.create();

    static  {
        numberLotteryFetcherTable.put(LotteryType.FC_SSQ, FetcherType.F_OFFICIAL, new SsqOfficialFetcher());
        numberLotteryFetcherTable.put(LotteryType.FC_SSQ, FetcherType.F_NETEASE, new SsqNeteaseFetcher());

    }

    public static INumberLotteryFetcher getFetcher(LotteryType lotteryType, FetcherType fetcherType) {
        return numberLotteryFetcherTable.get(lotteryType, fetcherType);
    }
}
