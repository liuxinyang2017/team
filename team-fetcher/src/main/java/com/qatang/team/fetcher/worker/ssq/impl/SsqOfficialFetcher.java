package com.qatang.team.fetcher.worker.ssq.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.qatang.team.core.util.CoreLotteryUtils;
import com.qatang.team.core.util.CoreMathUtils;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetail;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResult;
import com.qatang.team.fetcher.exception.FetcherException;
import com.qatang.team.fetcher.worker.ssq.AbstractSsqFetcher;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 双色球官方数据抓取器
 * @author qatang
 */
public class SsqOfficialFetcher extends AbstractSsqFetcher {
    private final FetcherType fetcherType = FetcherType.F_OFFICIAL;

    private final static Map<String, String> prizeNameToKeyMap = Maps.newHashMap();
    static {
        prizeNameToKeyMap.put("一等奖", "prize1");
        prizeNameToKeyMap.put("二等奖", "prize2");
        prizeNameToKeyMap.put("三等奖", "prize3");
        prizeNameToKeyMap.put("四等奖", "prize4");
        prizeNameToKeyMap.put("五等奖", "prize5");
        prizeNameToKeyMap.put("六等奖", "prize6");
    }

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
                Elements redElements = tdList.get(1).select("p.haoma span");
                String[] red = redElements.stream().map(Element::text).toArray(String[]::new);
                String blue = tdList.get(2).select("p.haoma span").get(0).text().trim();

                numberLotteryFetchResult.setResult(CoreLotteryUtils.formatSsq(red, blue));
                fetched = true;
                break;
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
            throw new FetcherException(msg);
        }

        // 再抓取详情数据
        Document detailDocument = this.fetch(detailUrl);

        Elements detailElements = detailDocument.select("li.caizhong span");
        String fetchedPhase = detailElements.get(0).text().substring(1, 8);
        String saleAmount = detailElements.get(2).select("i").text();
        String poolAmount = detailElements.get(3).select("i").text();
        if (Strings.isNullOrEmpty(fetchedPhase)
                || Strings.isNullOrEmpty(saleAmount)
                || Strings.isNullOrEmpty(poolAmount)) {
            String msg = String.format("未抓取到开奖详情，lotteryType=%s，fetcherType=%s, phase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase);
            logger.error(msg);
            throw new FetcherException(msg);
        }
        if (!fetchedPhase.equals(phase)) {
            String msg = String.format("开奖详情抓取异常：彩期不一致，lotteryType=%s，fetcherType=%s, phase=%s，fetchedPhase=%s", this.getLotteryType().getName(), this.getFetcherType().getName(), phase, fetchedPhase);
            logger.error(msg);
            throw new FetcherException(msg);
        }
        numberLotteryFetchResult.setSaleAmount(CoreMathUtils.mul(Longs.tryParse(StringUtils.replace(saleAmount, ",", "")), 100));
        numberLotteryFetchResult.setPoolAmount(CoreMathUtils.mul(Longs.tryParse(StringUtils.replace(poolAmount, ",", "")), 100));

        Elements detailElements2 = detailDocument.select("li.haoma3 span");
        String[] red = new String[]{detailElements2.get(0).text(),
                detailElements2.get(1).text(),
                detailElements2.get(2).text(),
                detailElements2.get(3).text(),
                detailElements2.get(4).text(),
                detailElements2.get(5).text()};
        String blue = detailElements2.get(6).text();
        numberLotteryFetchResult.setResult(CoreLotteryUtils.formatSsq(red, blue));

        List<NumberLotteryFetchDetail> numberLotteryFetchDetailList = Lists.newArrayList();
        Elements detailElements3 = detailDocument.select("table.mt17 tbody tr");
        for (Element tr : detailElements3) {
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

        if (numberLotteryFetchDetailList.isEmpty()) {
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
