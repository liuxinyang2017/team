package com.qatang.team.proxy.fetcher;

import com.google.common.collect.Lists;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author qatang
 */
public abstract class AbstractProxyFetcher implements IProxyFetcher {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取代理抓取器类型
     * @return 代理抓取器类型
     */
    protected abstract ProxyFetcherType getProxyFetcherType();

    /**
     * 获取代理抓取地址
     * @return 代理抓取地址
     */
    protected abstract List<String> getFetchUrl();

    /**
     * 解析代理抓取内容
     * @param document 抓取内容
     * @return 抓取数据
     */
    protected abstract List<ProxyInfo> parseDoc(Document document);

    /**
     * 抓取源数据
     * @param url 源数据地址
     * @return 源数据
     * @throws FetcherException 异常
     */
    protected Document fetch(String url) throws FetcherException {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                    .timeout(10000)
                    .get();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new FetcherException(e.getMessage());
        }
    }

    @Override
    public List<ProxyInfo> fetchProxyList() throws ProxyException {
        logger.info(String.format("开始抓取代理列表, proxyFetcherType=%s", this.getProxyFetcherType().getName()));
        List<ProxyInfo> allProxyInfoList = Lists.newArrayList();

        List<String> fetchUrlList = this.getFetchUrl();
        for (String fetchUrl : fetchUrlList) {
            List<ProxyInfo> proxyInfoList;
            try {
                Document doc = this.fetch(fetchUrl);
                proxyInfoList = parseDoc(doc);
            } catch (FetcherException e) {
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
