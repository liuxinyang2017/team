package com.qatang.team.proxy.fetcher.impl;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.fetcher.AbstractProxyFetcher;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qatang
 */
public class KuaiDailiProxyFetcher extends AbstractProxyFetcher {
    private final ProxyFetcherType proxyFetcherType = ProxyFetcherType.P_KUAI;

    @Override
    protected ProxyFetcherType getProxyFetcherType() {
        return proxyFetcherType;
    }

    @Override
    protected List<String> getFetchUrl() {
        return Lists.newArrayList("http://www.kuaidaili.com/free/");
    }

    @Override
    protected List<ProxyInfo> parseDoc(Document document) {
        Elements elements = document.select("#list table tbody tr");

        List<ProxyInfo> proxyList = new ArrayList<>();

        for (Element element : elements) {
            Elements tdElements = element.getElementsByTag("td");
            String hostname = StringUtils.trim(tdElements.get(0).text());
            String port = StringUtils.trim(tdElements.get(1).text());

            String type = StringUtils.trim(tdElements.get(3).text());
            if ("http".equalsIgnoreCase(type)
                    || "https".equalsIgnoreCase(type)) {
                proxyList.add(new ProxyInfo(hostname, Integer.valueOf(port)));
            }
        }
        return proxyList;
    }
}
