package com.qatang.team.fetcher.worker.ssq.impl;

import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author qatang
 */
public class SsqOfficialFetcherTest {

    @Test
    public void testFetchResult() {
        INumberLotteryFetcher numberLotteryFetcher = new SsqOfficialFetcher();
        NumberLotteryFetchResult numberLotteryFetchResult = numberLotteryFetcher.fetchResult("2017077");
        Assert.assertTrue(numberLotteryFetchResult.getResult() != null);
        System.out.println(numberLotteryFetchResult.getResult());
    }
}
