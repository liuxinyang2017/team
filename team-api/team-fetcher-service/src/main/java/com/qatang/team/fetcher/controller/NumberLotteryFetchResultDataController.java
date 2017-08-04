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
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.bean.QNumberLotteryFetchResultData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;
import com.qatang.team.fetcher.service.NumberLotteryFetchResultDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/numberLotteryFetchResultData")
public class NumberLotteryFetchResultDataController extends BaseController{

    @Autowired
    private NumberLotteryFetchResultDataInternalService numberLotteryFetchResultDataInternalService;

    /***
     * 创建开奖结果抓取数据信息
     * @param fetchNumberLotteryResultData 开奖结果抓取数据对象
     * @return 保存后的开奖结果抓取数据
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public FetchNumberLotteryResultData create(@RequestBody FetchNumberLotteryResultData fetchNumberLotteryResultData) {
        logger.info("开始创建抓取开奖结果对象");
        return numberLotteryFetchResultDataInternalService.save(fetchNumberLotteryResultData);
    }

    /***
     * 修改开奖结果抓取数据
     * @param fetchNumberLotteryResultData 开奖结果抓取数据对象
     * @return 修改后的开奖结果抓取数据
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public FetchNumberLotteryResultData update(@RequestBody FetchNumberLotteryResultData fetchNumberLotteryResultData) {
        logger.info("修改抓取开奖结果对象,id:[{}]", fetchNumberLotteryResultData.getId());
        return numberLotteryFetchResultDataInternalService.update(fetchNumberLotteryResultData);
    }

    /***
     * 根据id获取开奖结果抓取数据
     * @param id 开奖结果抓取数据对象id
     * @return 获取到的开奖结果抓取数据信息
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FetchNumberLotteryResultData get(@RequestParam Long id) {
        logger.info("获取抓取开奖结果对象信息, id:[{}]", id);
        return numberLotteryFetchResultDataInternalService.get(id);
    }

    /***
     * 根据彩种彩期抓取来源数据类型获取开奖结果抓取信息
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取来源数据类型
     * @return 获取到的开奖结果抓取信息
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhaseAndFetcherType", method = RequestMethod.POST)
    FetchNumberLotteryResultData getByLotteryTypeAndPhaseAndFetcherType(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phase, @RequestParam("fetcherType")FetcherType fetcherType) {
        logger.info("根据彩种彩期数据类型查询开奖结果抓取数据,彩种{},彩期{},抓取数据来源类型{}", lotteryType.getName(), phase, fetcherType.getName());
        return numberLotteryFetchResultDataInternalService.getByLotteryTypeAndPhaseAndFetcherType(lotteryType, phase, fetcherType);
    }

    /***
     * 自定义查询开奖结果抓取数据
     * @param pageableWrapper 自定义查询条件
     * @return 自定义查询到的开奖结果抓取数据信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<FetchNumberLotteryResultData> find(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取开奖结果对象信息");

        for (ApiRequestFilter filter : apiRequest.getFilterList()) {
            String filed = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (filed) {
                case QNumberLotteryFetchResultData.lotteryType:
                    if (value != null) {
                        value = LotteryType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> LotteryType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchResultData.fetcherType:
                    if (value != null) {
                        value = FetcherType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> FetcherType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchResultData.createdTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchResultData.updatedTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QNumberLotteryFetchResultData.fetchedTime:
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
        return numberLotteryFetchResultDataInternalService.findAll(apiRequest, apiRequestPage);
    }
}
