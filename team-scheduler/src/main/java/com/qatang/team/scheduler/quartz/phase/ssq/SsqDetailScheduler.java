package com.qatang.team.scheduler.quartz.phase.ssq;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataApiService;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.fetcher.bean.*;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataApiService;
import com.qatang.team.fetcher.service.FetchNumberLotteryResultDataApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 双色球开奖详情录入定时
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.ssq.detail.on")
public class SsqDetailScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LotteryType lotteryType = LotteryType.FC_SSQ;

    @Autowired
    private NumberLotteryDataApiService numberLotteryDataApiService;

    @Autowired
    private FetchNumberLotteryDetailDataApiService fetchNumberLotteryDetailDataApiService;

    @Scheduled(fixedDelay = 5 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        try {
            logger.info(String.format("双色球开奖详情录入定时：开始处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QNumberLotteryData.lotteryType, lotteryType)
                    .filterEqual(QNumberLotteryData.phaseStatus, PhaseStatus.RESULT_SET);

            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<NumberLotteryData> numberLotteryDataList = ApiPageRequestHelper.request(pageableWrapper, numberLotteryDataApiService::findAll);
            if (numberLotteryDataList != null && !numberLotteryDataList.isEmpty()) {
                numberLotteryDataList.forEach(numberLotteryData -> {
                    ApiRequest apiRequest2 = ApiRequest.newInstance()
                            .filterEqual(QFetchNumberLotteryDetailData.lotteryType, lotteryType)
                            .filterEqual(QFetchNumberLotteryDetailData.phase, numberLotteryData.getPhase());

                    ApiRequestPage apiRequestPage2 = ApiRequestPage.newInstance()
                            .paging(0, 100)
                            .addOrder(QProxyData.id, PageOrderType.ASC);
                    PageableWrapper pageableWrapper2 = new PageableWrapper(apiRequest2, apiRequestPage2);

                    List<FetchNumberLotteryDetailData> fetchNumberLotteryDetailDataList = ApiPageRequestHelper.request(pageableWrapper2, fetchNumberLotteryDetailDataApiService::findAll);
                    if (fetchNumberLotteryDetailDataList == null || fetchNumberLotteryDetailDataList.isEmpty()) {
                        logger.info(String.format("双色球开奖详情录入定时：未查询到(%s)(%s)的抓取数据，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase()));
                        return;
                    }

                    if (fetchNumberLotteryDetailDataList.size() < GlobalConstants.FETCH_DATA_MIN_COUNT) {
                        logger.info(String.format("双色球开奖详情录入定时：(%s)(%s)的抓取数据数量是(%s)，小于最小对比数量(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), fetchNumberLotteryDetailDataList.size(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }

                    Long poolAmount = comparePoolAmount(fetchNumberLotteryDetailDataList);
                    if (poolAmount == null || poolAmount <= 0L) {
                        logger.info(String.format("双色球开奖详情录入定时：(%s)(%s)的抓取奖池金额数据结果一致的数量小于(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }
                    Long saleAmount = compareSaleAmount(fetchNumberLotteryDetailDataList);
                    if (saleAmount == null || saleAmount <= 0L) {
                        logger.info(String.format("双色球开奖详情录入定时：(%s)(%s)的抓取销售额金额数据结果一致的数量小于(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }

                    List<FetchNumberLotteryDetailItemData> allItemDataList = Lists.newArrayList();
                    fetchNumberLotteryDetailDataList.forEach(detail -> allItemDataList.addAll(fetchNumberLotteryDetailDataApiService.getByDetailId(detail.getId())));
                    List<NumberLotteryDetailData> dataList = compareDetail(allItemDataList);
                    if (dataList == null || dataList.isEmpty()) {
                        logger.info(String.format("双色球开奖详情录入定时：(%s)(%s)的抓取详情数据结果一致的数量小于(%s)，等待下一次定时处理", lotteryType.getName(), numberLotteryData.getPhase(), GlobalConstants.FETCH_DATA_MIN_COUNT));
                        return;
                    }
                    numberLotteryDataApiService.updateDetailData(lotteryType, numberLotteryData.getPhase(), poolAmount, saleAmount, dataList);
                    logger.info(String.format("双色球开奖详情录入定时：(%s)(%s)更新开奖详情成功", lotteryType.getName(), numberLotteryData.getPhase()));
                });
            } else {
                logger.info(String.format("双色球开奖详情录入定时：未查询到(%s)状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
            }
            logger.info(String.format("双色球开奖详情录入定时：结束处理(%s)所有状态为(%s)的彩期数据", lotteryType.getName(), PhaseStatus.RESULT_SET.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Long comparePoolAmount(List<FetchNumberLotteryDetailData> fetchNumberLotteryDetailDataList) {
        Map<Long, List<FetchNumberLotteryDetailData>> map = fetchNumberLotteryDetailDataList.stream().collect(Collectors.groupingBy(FetchNumberLotteryDetailData::getPoolAmount));

        for (Long key : map.keySet()) {
            List<FetchNumberLotteryDetailData> values = map.get(key);
            if (values.size() >= GlobalConstants.FETCH_DATA_MIN_COUNT) {
                return key;
            }
        }
        return null;
    }

    private Long compareSaleAmount(List<FetchNumberLotteryDetailData> fetchNumberLotteryDetailDataList) {
        Map<Long, List<FetchNumberLotteryDetailData>> map = fetchNumberLotteryDetailDataList.stream().collect(Collectors.groupingBy(FetchNumberLotteryDetailData::getSaleAmount));

        for (Long key : map.keySet()) {
            List<FetchNumberLotteryDetailData> values = map.get(key);
            if (values.size() >= GlobalConstants.FETCH_DATA_MIN_COUNT) {
                return key;
            }
        }
        return null;
    }

    private List<NumberLotteryDetailData> compareDetail(List<FetchNumberLotteryDetailItemData> itemDataList) {
        Multimap<String, String> groupKeyMultimap = ArrayListMultimap.create();

        // 先按prizyKey分
        Set<String> prizeKeySet = itemDataList.stream().map(FetchNumberLotteryDetailItemData::getPrizeKey).collect(Collectors.toSet());

        Multimap<String, FetchNumberLotteryDetailItemData> multimap = ArrayListMultimap.create();
        for (FetchNumberLotteryDetailItemData itemData : itemDataList) {
            String key = itemData.getPrizeKey() + "_" + itemData.getPrizeCount() + "_" + itemData.getPrizeAmount();
            multimap.put(key, itemData);
            // prizeKey对应的数据key列表
            groupKeyMultimap.put(itemData.getPrizeKey(), key);
        }

        List<NumberLotteryDetailData> resultList = Lists.newArrayList();
        for (String groupKey : prizeKeySet) {
            List<String> dataKeyList = Lists.newArrayList(groupKeyMultimap.get(groupKey));
            for (String key : dataKeyList) {
                List<FetchNumberLotteryDetailItemData> values = Lists.newArrayList(multimap.get(key));
                if (values.size() < GlobalConstants.FETCH_DATA_MIN_COUNT) {
                    continue;
                }
                FetchNumberLotteryDetailItemData itemData = values.get(0);
                NumberLotteryDetailData numberLotteryDetailData = new NumberLotteryDetailData();
                numberLotteryDetailData.setLotteryType(lotteryType);
                numberLotteryDetailData.setPhase(itemData.getPhase());
                numberLotteryDetailData.setPrizeKey(itemData.getPrizeKey());
                numberLotteryDetailData.setPrizeName(itemData.getPrizeName());
                numberLotteryDetailData.setPrizeCount(itemData.getPrizeCount());
                numberLotteryDetailData.setPrizeAmount(itemData.getPrizeAmount());
                numberLotteryDetailData.setPriority(0);
                resultList.add(numberLotteryDetailData);
                break;
            }
        }

        if (resultList.size() != prizeKeySet.size()) {
            return Lists.newArrayList();
        }

        resultList.sort(Comparator.comparing(NumberLotteryDetailData::getPrizeKey));
        return resultList;
    }
}
