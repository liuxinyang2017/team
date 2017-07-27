package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.fetcher.bean.FetcherLog;
import com.qatang.team.fetcher.service.FetcherLogInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/fetcherLog")
public class FetcherLogController extends BaseController {

    @Autowired
    private FetcherLogInternalService fetcherLogInternalService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public FetcherLog create(@RequestBody FetcherLog fetcherLog) {
        logger.info("开始创建抓取日志对象");
        return fetcherLogInternalService.save(fetcherLog);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public FetcherLog update(@RequestBody FetcherLog fetcherLog) {
        logger.info("修改抓取日志对象");
        return fetcherLogInternalService.update(fetcherLog);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public FetcherLog get(@RequestParam("id") Long id) {
        logger.info("获取抓取日志对象信息");
        return fetcherLogInternalService.get(id);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<FetcherLog> findAll(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取日志对象信息");
        return fetcherLogInternalService.findAll(apiRequest, apiRequestPage);
    }
}
