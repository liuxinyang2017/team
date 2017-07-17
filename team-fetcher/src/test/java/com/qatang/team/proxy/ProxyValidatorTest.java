package com.qatang.team.proxy;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.fetcher.IProxyFetcher;
import com.qatang.team.proxy.fetcher.ProxyFetcherFactory;
import com.qatang.team.proxy.validator.IProxyValidator;
import com.qatang.team.proxy.validator.ProxyValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.Proxy;
import java.util.List;

/**
 * @author qatang
 */
public class ProxyValidatorTest {
    private List<ProxyInfo> proxyInfoList;

    @Before
    public void init() {
        IProxyFetcher proxyFetcher = ProxyFetcherFactory.getFetcher(ProxyFetcherType.P_CZ88);
        proxyInfoList = proxyFetcher.fetchProxyList();
    }

    @Test
    public void testBaiduProxyValidator() {
        IProxyValidator proxyValidator = ProxyValidatorFactory.getValidator(ProxyValidatorType.V_BAIDU);
        proxyInfoList.forEach(proxyInfo -> {
            try {
                proxyValidator.validate(proxyInfo);
            } catch (ProxyException e) {
//                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    public void testFucaiProxyValidator() {
        IProxyValidator proxyValidator = ProxyValidatorFactory.getValidator(ProxyValidatorType.V_FUCAI);
        proxyInfoList.forEach(proxyInfo -> {
            try {
                proxyValidator.validate(proxyInfo);
            } catch (ProxyException e) {
//                System.out.println(e.getMessage());
            }
        });
    }
}
