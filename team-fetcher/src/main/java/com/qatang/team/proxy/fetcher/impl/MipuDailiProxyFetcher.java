package com.qatang.team.proxy.fetcher.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.Lists;
import com.qatang.team.core.util.CoreHttpUtils;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import com.qatang.team.proxy.fetcher.AbstractProxyFetcher;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qatang
 */
public class MipuDailiProxyFetcher extends AbstractProxyFetcher {
    private final ProxyFetcherType proxyFetcherType = ProxyFetcherType.P_MIMVP;

    @Override
    protected ProxyFetcherType getProxyFetcherType() {
        return proxyFetcherType;
    }

    @Override
    protected List<String> getFetchUrl() {
        return Lists.newArrayList("http://proxy.mimvp.com/api/fetch.php?orderid=860170803115806114&country=中国&http_type=1&anonymous=5&result_format=json");
    }

    @Override
    protected List<ProxyInfo> parseDoc(Document document) {
        return null;
    }

    @Override
    public List<ProxyInfo> fetchProxyList() throws ProxyException {
        logger.info(String.format("开始抓取代理列表, proxyFetcherType=%s", this.getProxyFetcherType().getName()));
        List<ProxyInfo> allProxyInfoList = Lists.newArrayList();

        List<String> fetchUrlList = this.getFetchUrl();
        for (String fetchUrl : fetchUrlList) {
            List<ProxyInfo> proxyInfoList = Lists.newArrayList();
            try {
                String result = CoreHttpUtils.get(fetchUrl);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(result);
                JsonNode jsonResult = root.get("result");
                if (jsonResult.isArray()) {
                    for (JsonNode objNode : jsonResult) {
                        String proxy = objNode.get("ip:port").asText();
                        String hostname = StringUtils.split(proxy, ":")[0];
                        String port = StringUtils.split(proxy, ":")[1];
                        proxyInfoList.add(new ProxyInfo(hostname, Integer.valueOf(port)));
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("抓去代理列表失败，proxyFetcherType=%s，url=%s", this.getProxyFetcherType().getName(), fetchUrl));
                logger.error(e.getMessage(), e);
                continue;
            }

            if (proxyInfoList.isEmpty()) {
                continue;
            }
            logger.error(String.format("抓去代理列表成功，proxyFetcherType=%s，url=%s，size=%s", this.getProxyFetcherType().getName(), fetchUrl, proxyInfoList.size()));
            allProxyInfoList.addAll(proxyInfoList);
        }
        return allProxyInfoList;
    }
}
