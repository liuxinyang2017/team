package com.qatang.team.fetcher.worker.ssq.impl;

import com.google.common.base.Strings;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.worker.ssq.AbstractSsqFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;

/**
 * 双色球官方数据抓取器
 * @author qatang
 */
public class SsqOfficialFetcher extends AbstractSsqFetcher {
    private final FetcherType fetcherType = FetcherType.F_OFFICIAL;


    @Override
    protected FetcherType getFetcherType() {
        return fetcherType;
    }

    @Override
    protected String getResultFetchUrl() {
        return "http://www.cwl.gov.cn/kjxx/ssq/hmhz/";
    }

    @Override
    protected String getDetailFetchUrl() {
        return "http://www.cwl.gov.cn/kjxx/ssq/hmhz/";
    }

    @Override
    protected NumberLotteryFetchResult parseResultDoc(String phase, Document document) {
        NumberLotteryFetchResult numberLotteryFetchResult = new NumberLotteryFetchResult();
        numberLotteryFetchResult.setLotteryType(this.getLotteryType());
        numberLotteryFetchResult.setFetcherType(this.getFetcherType());
        numberLotteryFetchResult.setPhase(phase);

        boolean fetched = false;
        Elements elements = document.select("table.hz tbody tr");
        for (Element tr : elements) {
            Elements tdList = tr.select("td");
            if (phase.equals(tdList.first().text())) {
                numberLotteryFetchResult.setFetchedTime(LocalDateTime.now());
                numberLotteryFetchResult.setResult(tdList.get(1).html() + tdList.get(2).html());
                fetched = true;
                break;
            }
        }

        if (!fetched) {
            String msg = String.format("未抓取到开奖号码，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new NumberFormatException(msg);
        }
        logger.info(String.format("已抓到开奖号码，lotteryType=%s，fetcherType=%s, phase=%s，result=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, numberLotteryFetchResult.getResult()));
        return numberLotteryFetchResult;
    }

    @Override
    protected NumberLotteryFetchResult parseDetailDoc(String phase, Document document) {
        NumberLotteryFetchResult numberLotteryFetchResult = new NumberLotteryFetchResult();
        numberLotteryFetchResult.setLotteryType(this.getLotteryType());
        numberLotteryFetchResult.setFetcherType(this.getFetcherType());
        numberLotteryFetchResult.setPhase(phase);

        // 先获取详情抓取地址
        String detailUrl = null;
        Elements elements = document.select("table.hz tbody tr");
        for (Element tr : elements) {
            Elements tdList = tr.select("td");
            if (phase.equals(tdList.first().text())) {
                detailUrl = tdList.get(6).select("a").attr("abs:href");
                break;
            }
        }

        if (Strings.isNullOrEmpty(detailUrl)) {
            String msg = String.format("未抓取到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new NumberFormatException(msg);
        }

        // 再抓取详情数据
        Document detailDocument = this.fetch(detailUrl);

        boolean fetched = false;
        Elements detailElements = detailDocument.select("table.hz tbody tr");
        for (Element tr : detailElements) {
            Elements tdList = tr.select("td");
            if (phase.equals(tdList.first().text())) {

                fetched = true;
                break;
            }
        }

        if (!fetched) {
            String msg = String.format("未抓取到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new NumberFormatException(msg);
        }
        logger.info(String.format("已抓到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s，result=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, numberLotteryFetchResult.getResult()));
        return null;
    }
}
