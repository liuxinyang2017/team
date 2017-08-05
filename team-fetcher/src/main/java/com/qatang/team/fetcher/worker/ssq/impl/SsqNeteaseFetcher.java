package com.qatang.team.fetcher.worker.ssq.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import com.qatang.team.core.util.CoreLotteryUtils;
import com.qatang.team.core.util.CoreMathUtils;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetail;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.fetcher.worker.ssq.AbstractSsqFetcher;
import com.qatang.team.proxy.bean.ProxyInfo;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 双色球官方数据抓取器
 * @author qatang
 */
public class SsqNeteaseFetcher extends AbstractSsqFetcher {
    private final FetcherType fetcherType = FetcherType.F_NETEASE;

    @Override
    protected FetcherType getFetcherType() {
        return fetcherType;
    }

    @Override
    protected String getResultFetchUrl(String phase) {
        return "http://caipiao.163.com/award/ssq/" + phase + ".html";
    }

    @Override
    protected String getDetailFetchUrl(String phase) {
        return "http://caipiao.163.com/award/ssq/" + phase + ".html";
    }

    @Override
    protected String getFetchEncoding() {
        return "UTF-8";
    }

    @Override
    protected NumberLotteryFetchResult parseResultDoc(String phase, Document document, ProxyInfo proxyInfo) {
        NumberLotteryFetchResult numberLotteryFetchResult = new NumberLotteryFetchResult();
        numberLotteryFetchResult.setLotteryType(this.getLotteryType());
        numberLotteryFetchResult.setFetcherType(this.getFetcherType());
        numberLotteryFetchResult.setPhase(phase);

        Elements redElements = document.select("#zj_area span.red_ball");
        Elements blueElements = document.select("#zj_area span.blue_ball");

        String[] red = redElements.stream().map(Element::text).toArray(String[]::new);
        String blue = blueElements.get(0).text();

        boolean fetched = false;
        if (red.length == 6) {
            if (StringUtils.isNotEmpty(red[0]) && StringUtils.isNotEmpty(blue)
                    && red[0].length() == 2 && blue.length() == 2) {
                numberLotteryFetchResult.setResult(CoreLotteryUtils.formatSsq(red, blue));
                fetched = true;
            }
        }

        if (!fetched) {
            String msg = String.format("未抓取到开奖号码，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new FetcherException(msg);
        }

        numberLotteryFetchResult.setFetchedTime(LocalDateTime.now());
        logger.info(String.format("已抓到开奖号码，lotteryType=%s，fetcherType=%s, phase=%s，result=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, numberLotteryFetchResult.getResult()));
        return numberLotteryFetchResult;
    }

    @Override
    protected NumberLotteryFetchResult parseDetailDoc(String phase, Document document, ProxyInfo proxyInfo) {
        NumberLotteryFetchResult numberLotteryFetchResult = new NumberLotteryFetchResult();
        numberLotteryFetchResult.setLotteryType(this.getLotteryType());
        numberLotteryFetchResult.setFetcherType(this.getFetcherType());
        numberLotteryFetchResult.setPhase(phase);

        Elements redElements = document.select("#zj_area span.red_ball");
        Elements blueElements = document.select("#zj_area span.blue_ball");
        Elements saleAmountElements = document.select("#sale");
        Elements poolAmountElements = document.select("#pool");
        Elements detailElements = document.select("#bonus tbody tr");
        detailElements.remove(0);

        String[] red = redElements.stream().map(Element::text).toArray(String[]::new);
        String blue = blueElements.get(0).text();
        String saleAmount = saleAmountElements.get(0).text();
        String poolAmount = poolAmountElements.get(0).text();

        if (Strings.isNullOrEmpty(saleAmount)
                || Strings.isNullOrEmpty(poolAmount)) {
            String msg = String.format("未抓取到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new FetcherException(msg);
        }

        numberLotteryFetchResult.setResult(CoreLotteryUtils.formatSsq(red, blue));
        numberLotteryFetchResult.setSaleAmount(CoreMathUtils.mul(Longs.tryParse(StringUtils.replace(saleAmount, ",", "")), 100));
        numberLotteryFetchResult.setPoolAmount(CoreMathUtils.mul(Longs.tryParse(StringUtils.replace(poolAmount, ",", "")), 100));

        List<NumberLotteryFetchDetail> numberLotteryFetchDetailList = Lists.newArrayList();
        for (Element tr : detailElements) {
            Elements tdList = tr.select("td");

            String prizeName = tdList.get(0).text();
            String prizeCount = tdList.get(1).text();
            String prizeAmount = tdList.get(2).text();
            String prizeKey = prizeNameToKeyMap.get(prizeName);
            if (Strings.isNullOrEmpty(prizeKey)) {
                String msg = String.format("开奖详情抓取异常：奖级名称未找到对应key，lotteryType=%s，fetcherType=%s, phase=%s，prizeName=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, prizeName);
                logger.error(msg);
                throw new FetcherException(msg);
            }

            NumberLotteryFetchDetail numberLotteryFetchDetail = new NumberLotteryFetchDetail();
            numberLotteryFetchDetail.setPrizeKey(prizeKey);
            numberLotteryFetchDetail.setPrizeName(prizeName);
            numberLotteryFetchDetail.setPrizeCount(Longs.tryParse(prizeCount));
            numberLotteryFetchDetail.setPrizeAmount(CoreMathUtils.mul(Longs.tryParse(prizeAmount), 100));
            numberLotteryFetchDetailList.add(numberLotteryFetchDetail);
        }



        if (Strings.isNullOrEmpty(numberLotteryFetchResult.getResult())
                || numberLotteryFetchResult.getSaleAmount() <= 0
                || numberLotteryFetchResult.getPoolAmount() <= 0
                || numberLotteryFetchDetailList.isEmpty()) {
            String msg = String.format("未抓取到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new FetcherException(msg);
        }
        numberLotteryFetchResult.setDetailList(numberLotteryFetchDetailList);

        numberLotteryFetchResult.setFetchedTime(LocalDateTime.now());
        logger.info(String.format("已抓到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s，result=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, numberLotteryFetchResult.getResult()));
        return numberLotteryFetchResult;
    }
}
