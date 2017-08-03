package com.qatang.team.proxy.fetcher;

import com.google.common.collect.Maps;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.proxy.fetcher.impl.Cz88DailiProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.KuaiDailiProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.MipuDailiProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.XiciDailiProxyFetcher;

import java.util.Map;

/**
 * @author qatang
 */
public class ProxyFetcherFactory {
    private static Map<ProxyFetcherType, IProxyFetcher> map = Maps.newHashMap();
    static {
        map.put(ProxyFetcherType.P_XICI, new XiciDailiProxyFetcher());
        map.put(ProxyFetcherType.P_KUAI, new KuaiDailiProxyFetcher());
        map.put(ProxyFetcherType.P_CZ88, new Cz88DailiProxyFetcher());
        map.put(ProxyFetcherType.P_MIMVP, new MipuDailiProxyFetcher());
    }

    public static IProxyFetcher getFetcher(ProxyFetcherType proxyFetcherType) {
        return map.get(proxyFetcherType);
    }
}
