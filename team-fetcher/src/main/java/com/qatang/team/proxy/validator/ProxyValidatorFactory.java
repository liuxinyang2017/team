package com.qatang.team.proxy.validator;

import com.google.common.collect.Maps;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.Cz88DailiProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.KuaiDailiProxyFetcher;
import com.qatang.team.proxy.fetcher.impl.XiciDailiProxyFetcher;
import com.qatang.team.proxy.validator.impl.BaiduProxyValidator;
import com.qatang.team.proxy.validator.impl.FucaiProxyValidator;

import java.util.Map;

/**
 * @author qatang
 */
public class ProxyValidatorFactory {
    private static Map<ProxyValidatorType, IProxyValidator> map = Maps.newHashMap();
    static {
        map.put(ProxyValidatorType.V_BAIDU, new BaiduProxyValidator());
        map.put(ProxyValidatorType.V_FUCAI, new FucaiProxyValidator());
    }

    public static IProxyValidator getValidator(ProxyValidatorType proxyValidatorType) {
        return map.get(proxyValidatorType);
    }
}
