package com.qatang.team.fetcher.worker;

import com.google.common.base.Strings;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.NumberLotteryFetcherException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

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
     * 获取代理
     * @return 代理
     */
    protected Proxy getHttpProxy() {
        String host = "";
        int port = 0;
        String user = "";
        String password = "";

        if (!Strings.isNullOrEmpty(user)) {
            Authenticator authenticator = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication(user,
                            password.toCharArray()));
                }
            };
            Authenticator.setDefault(authenticator);
        }
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host , port));
    }

    @Override
    public NumberLotteryFetchResult fetchResult(String phase) throws NumberLotteryFetcherException {
        if (Strings.isNullOrEmpty(phase)) {
            throw new NumberLotteryFetcherException("彩期不能为空");
        }
        logger.info(String.format("开始抓开奖号码，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase));
        try {
            Document doc = Jsoup.connect(this.getResultFetchUrl())
                    .proxy(this.getHttpProxy()).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").get();
            return parseResultDoc(phase, doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NumberLotteryFetcherException(e.getMessage());
        }
    }

    @Override
    public NumberLotteryFetchResult fetchDetail(String phase) throws NumberLotteryFetcherException {
        if (Strings.isNullOrEmpty(phase)) {
            throw new NumberLotteryFetcherException("彩期不能为空");
        }
        logger.info(String.format("开始抓开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase));
        try {
            Document doc = Jsoup.connect(this.getDetailFetchUrl())
                    .proxy(this.getHttpProxy()).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36").get();
            return parseDetailDoc(phase, doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new NumberLotteryFetcherException(e.getMessage());
        }
    }
}
