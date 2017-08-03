package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestFilter;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.FetcherDataType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.bean.QFetcherLog;
import com.qatang.team.fetcher.service.FetcherLogInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/fetcherLog")
public class FetcherLogController extends BaseController {

    @Autowired
    private FetcherLogInternalService fetcherLogInternalService;

    /***
     * 创建抓取日志
     * @param fetcherLog 抓取日志对象
     * @return 保存后的抓取日志
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public FetcherLog create(@RequestBody FetcherLog fetcherLog) {
        logger.info("开始创建抓取日志对象");
        return fetcherLogInternalService.save(fetcherLog);
    }

    /***
     * 修改抓取日志信息
     * @param fetcherLog 抓取日志对象
     * @return 修改后的抓取日志信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public FetcherLog update(@RequestBody FetcherLog fetcherLog) {
        logger.info("修改抓取日志对象,id:[{}]", fetcherLog.getId());
        return fetcherLogInternalService.update(fetcherLog);
    }

    /***
     * 通过id获取抓取日志信息
     * @param id 抓取日志id
     * @return 获取到的抓取日志信息
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FetcherLog get(@RequestParam("id") Long id) {
        logger.info("获取抓取日志对象信息，id:[{}]", id);
        return fetcherLogInternalService.get(id);
    }

    /***
     * 自定义查询抓取日志信息
     * @param pageableWrapper 自定义查询条件
     * @return 返回自定义查询抓取日志信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<FetcherLog> findAll(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取日志对象信息");

        for (ApiRequestFilter filter : apiRequest.getFilterList()) {
            String field = filter.getField();
            Object value = filter.getValue();
            List<Object> valueList = filter.getValueList();

            switch (field) {
                case QFetcherLog.fetcherType:
                    if (value != null) {
                        value = FetcherType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> FetcherType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.success:
                    if (value != null) {
                        value = YesNoStatus.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> YesNoStatus.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.lotteryType:
                    if (value != null) {
                        value = LotteryType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> LotteryType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.fetcherDataType:
                    if (value != null) {
                        value = FetcherDataType.get((int)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> FetcherDataType.get((int)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.createdTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.beginFetchTime:
                    if (value != null) {
                        value = CoreDateUtils.parseLocalDateTime((String)value);
                        filter.setValue(value);
                    }
                    if (valueList != null) {
                        valueList = valueList.stream().map(val -> CoreDateUtils.parseLocalDateTime((String)val)).collect(Collectors.toList());
                        filter.setValueList(valueList);
                    }
                    break;
                case QFetcherLog.endFetchTime:
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
        return fetcherLogInternalService.findAll(apiRequest, apiRequestPage);
    }
}
