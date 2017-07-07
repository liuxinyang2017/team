package com.qatang.team.proxy;

import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import com.qatang.team.fetcher.worker.ssq.impl.SsqOfficialFetcher;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author qatang
 */
public class ProxyTest {
    @Test
    public void testProxy() {
        ProxyManager.start();

        INumberLotteryFetcher numberLotteryFetcher = new SsqOfficialFetcher();
        NumberLotteryFetchResult numberLotteryFetchResult = numberLotteryFetcher.fetchResult("2017077");
        Assert.assertTrue(numberLotteryFetchResult.getResult() != null);
        System.out.println(numberLotteryFetchResult.getResult());
    }
}
