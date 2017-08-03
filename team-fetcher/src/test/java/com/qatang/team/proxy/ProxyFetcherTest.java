package com.qatang.team.proxy;

import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.worker.INumberLotteryFetcher;
import com.qatang.team.fetcher.worker.ssq.impl.SsqOfficialFetcher;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.ProxyFetcherFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author qatang
 */
public class ProxyFetcherTest {
    @Test
    public void testXiciDailiProxyFetcher() {
        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(ProxyFetcherType.P_XICI);
        List<ProxyInfo> proxyInfoList = proxyFetcher.fetchProxyList();
        Assert.assertTrue(proxyInfoList != null && !proxyInfoList.isEmpty());

        proxyInfoList.forEach(proxyInfo -> System.out.println(proxyInfo.getHost() + ":" + proxyInfo.getPort()));

    }

    @Test
    public void testKuaiDailiProxyFetcher() {
        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(ProxyFetcherType.P_KUAI);
        List<ProxyInfo> proxyInfoList = proxyFetcher.fetchProxyList();
        Assert.assertTrue(proxyInfoList != null && !proxyInfoList.isEmpty());

        proxyInfoList.forEach(proxyInfo -> System.out.println(proxyInfo.getHost() + ":" + proxyInfo.getPort()));

    }

    @Test
    public void testCz88DailiProxyFetcher() {
        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(ProxyFetcherType.P_CZ88);
        List<ProxyInfo> proxyInfoList = proxyFetcher.fetchProxyList();
        Assert.assertTrue(proxyInfoList != null && !proxyInfoList.isEmpty());

        proxyInfoList.forEach(proxyInfo -> System.out.println(proxyInfo.getHost() + ":" + proxyInfo.getPort()));

    }

    @Test
    public void testMipuDailiProxyFetcher() {
        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(ProxyFetcherType.P_MIMVP);
        List<ProxyInfo> proxyInfoList = proxyFetcher.fetchProxyList();
        Assert.assertTrue(proxyInfoList != null && !proxyInfoList.isEmpty());

        proxyInfoList.forEach(proxyInfo -> System.out.println(proxyInfo.getHost() + ":" + proxyInfo.getPort()));

    }
}
