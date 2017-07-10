package com.qatang.team.fetcher.worker;

import com.google.common.base.Strings;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.proxy.ProxyManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ProxySelector;

/**
 * @author qatang
 */
public abstract class AbstractNumberLotteryFetcher implements INumberLotteryFetcher {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取彩种类型
     * @return 彩种类型
     */
    protected abstract LotteryType getLotteryType();

    /**
     * 获取抓取器类型
     * @return 抓取器类型
     */
    protected abstract FetcherType getFetcherType();

    /**
     * 获取开奖号码抓取地址
     * @return 开奖号码抓取地址
     */
    protected abstract String getResultFetchUrl();

    /**
     * 获取开奖详情抓取地址
     * @return 开奖详情抓取地址
     */
    protected abstract String getDetailFetchUrl();

    /**
     * 解析开奖号码抓取内容
     * @param phase 彩期
     * @param document 抓取内容
     * @return 抓取数据
     */
    protected abstract NumberLotteryFetchResult parseResultDoc(String phase, Document document);

    /**
     * 解析开奖详情抓取内容
     * @param phase 彩期
     * @param document 抓取内容
     * @return 抓取数据
     */
    protected abstract NumberLotteryFetchResult parseDetailDoc(String phase, Document document);

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
    public NumberLotteryFetchResult fetchResult(String phase) throws FetcherException {
        if (Strings.isNullOrEmpty(phase)) {
            throw new FetcherException("彩期不能为空");
        }
        logger.info(String.format("开始抓开奖号码，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase));
        Document doc = this.fetch(this.getResultFetchUrl());
        try {
            return parseResultDoc(phase, doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new FetcherException(e.getMessage());
        }
    }

    @Override
    public NumberLotteryFetchResult fetchDetail(String phase) throws FetcherException {
        if (Strings.isNullOrEmpty(phase)) {
            throw new FetcherException("彩期不能为空");
        }
        logger.info(String.format("开始抓开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase));
        Document doc = this.fetch(this.getDetailFetchUrl());
        try {
            return parseDetailDoc(phase, doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new FetcherException(e.getMessage());
        }
    }
}
