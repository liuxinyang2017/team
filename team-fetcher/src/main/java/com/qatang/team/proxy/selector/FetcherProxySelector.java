package com.qatang.team.proxy.selector;

import com.google.common.collect.Lists;
import com.qatang.team.proxy.bean.ProxyInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

/**
 * 抓取器代理选择器
 * @author qatang
 */
public class FetcherProxySelector extends ProxySelector {
    private final static Logger logger = LoggerFactory.getLogger(FetcherProxySelector.class);

    private final static String[] PROXY_URL_ARRAY = new String[] {
            "cwl.gov.cn"
    };

    private List<ProxyInfo> proxyInfoList;

    public FetcherProxySelector(List<ProxyInfo> proxyInfoList) {
        this.proxyInfoList = proxyInfoList;
    }

    @Override
    public List<Proxy> select(URI uri) {
        logger.info(String.format("抓取代理选择器：开始处理url=%s", uri.toString()));
        if (!StringUtils.containsAny(uri.toString(), PROXY_URL_ARRAY)) {
            logger.info(String.format("抓取代理选择器：无需代理url=%s", uri.toString()));
            return Lists.newArrayList(Proxy.NO_PROXY);
        }


        return null;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

    }
}
