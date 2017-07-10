package com.qatang.team.proxy.selector;

import com.google.common.collect.Lists;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.proxy.bean.ProxyInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
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

    private ProxySelector defaultProxySelector;
    private List<ProxyInfo> proxyInfoList;

    public FetcherProxySelector(ProxySelector defaultProxySelector, List<ProxyInfo> proxyInfoList) {
        this.defaultProxySelector = defaultProxySelector;
        this.proxyInfoList = proxyInfoList;
    }

    @Override
    public List<Proxy> select(URI uri) {
        logger.info(String.format("抓取代理选择器：开始处理url=%s", uri.toString()));
        if (StringUtils.containsAny(uri.toString(), (CharSequence[]) PROXY_URL_ARRAY)) {
            final List<Proxy> proxyList = Lists.newArrayList();
            proxyInfoList.forEach(proxyInfo -> proxyList.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyInfo.getHost() , proxyInfo.getPort()))));
            logger.info(String.format("抓取代理选择器：使用代理url=%s", uri.toString()));
            return proxyList;
        }
        logger.info(String.format("抓取代理选择器：使用默认代理url=%s", uri.toString()));
        return defaultProxySelector.select(uri);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        String msg = String.format("抓取代理选择器：代理抓取失败，url=%s，socketAddress=%s", uri.toString(), sa.toString());
        logger.error(msg);
        logger.error(ioe.getMessage(), ioe);
    }
}
