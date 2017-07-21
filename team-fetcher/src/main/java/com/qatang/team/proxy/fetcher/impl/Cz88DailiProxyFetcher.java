package com.qatang.team.proxy.fetcher.impl;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.fetcher.AbstractProxyFetcher;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qatang
 */
public class Cz88DailiProxyFetcher extends AbstractProxyFetcher {
    private final ProxyFetcherType proxyFetcherType = ProxyFetcherType.P_CZ88;

    @Override
    protected ProxyFetcherType getProxyFetcherType() {
        return proxyFetcherType;
    }

    @Override
    protected List<String> getFetchUrl() {
        return Lists.newArrayList(
                "http://www.cz88.net/proxy/index.shtml",
                "http://www.cz88.net/proxy/http_2.shtml",
                "http://www.cz88.net/proxy/http_3.shtml",
                "http://www.cz88.net/proxy/http_4.shtml",
                "http://www.cz88.net/proxy/http_5.shtml",
                "http://www.cz88.net/proxy/http_6.shtml",
                "http://www.cz88.net/proxy/http_7.shtml",
                "http://www.cz88.net/proxy/http_8.shtml",
                "http://www.cz88.net/proxy/http_9.shtml",
                "http://www.cz88.net/proxy/http_10.shtml"
        );
    }

    @Override
    protected List<ProxyInfo> parseDoc(Document document) {
        Elements elements = document.select(".divbox .box694 ul li");

        List<ProxyInfo> proxyList = new ArrayList<>();

        for (int i = 1; i < elements.size(); i++) {
            Elements divElements = elements.get(i).getElementsByTag("div");
            String hostname = StringUtils.trim(StringUtils.replace(divElements.get(0).text(), "　", ""));
            String port = StringUtils.trim(divElements.get(1).text());

            String type = "HTTP";
            if ("http".equalsIgnoreCase(type)
                    || "https".equalsIgnoreCase(type)) {
                proxyList.add(new ProxyInfo(hostname, Integer.valueOf(port)));
            }
        }
        return proxyList;
    }
}
