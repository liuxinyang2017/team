package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchDetailData;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailDataInternalService;
import com.qatang.team.fetcher.service.FetchNumberLotteryDetailItemDataInternalService;
import com.qatang.team.fetcher.wrapper.FetchNumberLotteryDetailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/numberLotteryFetchDetailData")
public class FetchNumberLotteryDetailDataController extends BaseController {

    @Autowired
    private FetchNumberLotteryDetailDataInternalService fetchNumberLotteryDetailDataInternalService;

    @Autowired
    private FetchNumberLotteryDetailItemDataInternalService fetchNumberLotteryDetailItemDataInternalService;

    /**
     * 保存开奖详情
     * @param fetchNumberLotteryDetailWrapper 开奖详情对象
     * @return 保存后的开奖详情对象
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public FetchNumberLotteryDetailData create(@RequestBody FetchNumberLotteryDetailWrapper fetchNumberLotteryDetailWrapper) {
        logger.info("开始创建抓取开奖详情对象");
        FetchNumberLotteryDetailData fetchNumberLotteryDetailData = fetchNumberLotteryDetailWrapper.getFetchNumberLotteryDetailData();
        List<FetchNumberLotteryDetailItemData> lotteryDetailItemDatas = fetchNumberLotteryDetailWrapper.getFetchNumberLotteryDetailItemDataList();
        return fetchNumberLotteryDetailDataInternalService.save(fetchNumberLotteryDetailData, lotteryDetailItemDatas);
    }

    /**
     * 根据id获取开奖详情信息
     * @param id 开奖详情id
     * @return 获取到的开奖详情信息
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FetchNumberLotteryDetailData get(@RequestParam Long id) {
        logger.info("获取抓取开奖详情对象信息，id:[{}]", id);
        return fetchNumberLotteryDetailDataInternalService.get(id);
    }

    /**
     * 按id获取开奖详情抓取数据
     * @param detailId 开奖详情id
     * @return 获取到的抓取数据
     */
    @RequestMapping(value = "/getByFetchDetailId", method = RequestMethod.GET)
    List<FetchNumberLotteryDetailItemData> getByDetailId(@RequestParam("id") Long detailId) {
        logger.info("获取抓取开奖详情对象信息，detailId:[{}]", detailId);
        return fetchNumberLotteryDetailItemDataInternalService.getByDetailId(detailId);
    }

    /**
     * 根据彩种彩期获取开奖详情数据
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取数据来源类型
     * @return 抓取数据
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhaseAndFetcherType", method = RequestMethod.POST)
    FetchNumberLotteryDetailData getByLotteryTypeAndPhaseAndFetcherType(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phase, @RequestParam("fetcher")FetcherType fetcherType) {
        logger.info("根据彩种彩期抓取器查询开奖详情抓取数据,彩种{},彩期{},抓取数据来源类型{}", lotteryType.getName(), phase, fetcherType.getName());
        return fetchNumberLotteryDetailDataInternalService.findByLotteryTypeAndPhaseAndFetcherType(lotteryType, phase, fetcherType);
    }

    /**
     * 自定义查询开奖详情信息
     * @param pageableWrapper 自定义查询条件
     * @return 自定义查询到的开奖详情信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<FetchNumberLotteryDetailData> findAll(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取开奖详情对象信息");

        for (ApiRequestFilter filter : apiRequest.getFilterList()) {
            String filed = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (filed) {
                case QNumberLotteryFetchDetailData.lotteryType:
                    if (value != null) {
                        value = LotteryType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> LotteryType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchDetailData.fetcherType:
                    if (value != null) {
                        value = FetcherType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> FetcherType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchDetailData.createdTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchDetailData.updatedTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchDetailData.fetchedTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                default:
            }
        }
        return fetchNumberLotteryDetailDataInternalService.findAll(apiRequest, apiRequestPage);
    }
}
