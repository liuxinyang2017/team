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
public class XiciDailiProxyFetcher extends AbstractProxyFetcher {
    private final ProxyFetcherType proxyFetcherType = ProxyFetcherType.P_XICI;

    @Override
    protected ProxyFetcherType getProxyFetcherType() {
        return proxyFetcherType;
    }

    @Override
    protected List<String> getFetchUrl() {
        return Lists.newArrayList(
                "http://www.xicidaili.com/wt/",
                "http://www.xicidaili.com/wn/",
                "http://www.xicidaili.com/nn/",
                "http://www.xicidaili.com/nt/"
        );
    }

    @Override
    protected List<ProxyInfo> parseDoc(Document document) {
        Elements elements = document.select("#ip_list tbody tr");

        List<ProxyInfo> proxyList = new ArrayList<>();

        for (int i = 1; i < elements.size(); i++) {
            Elements tdElements = elements.get(i).getElementsByTag("td");
            String hostname = StringUtils.trim(tdElements.get(1).text());
            String port = StringUtils.trim(tdElements.get(2).text());

            String type = StringUtils.trim(tdElements.get(5).text());
            if ("http".equalsIgnoreCase(type)
                    || "https".equalsIgnoreCase(type)) {
                proxyList.add(new ProxyInfo(hostname, Integer.valueOf(port)));
            }
        }
        return proxyList;
    }
}
