package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
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
    FetcherLog create(@RequestBody FetcherLog fetcherLog) {

        logger.info("开始创建抓取日志对象");
//        FetcherLog fetcherLogResult = fetcherLogInternalService.save(fetcherLog);
        return new FetcherLog();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    FetcherLog update(@RequestBody FetcherLog fetcherLog) {
        logger.info("修改抓取日志对象");
//        FetcherLog fetcherLogResult = fetcherLogInternalService.update(fetcherLog);
        return new FetcherLog();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    FetcherLog get(@RequestParam(name = "id") Long id) {
        logger.info("获取抓取日志对象信息");
//        FetcherLog fetcherLogResult = fetcherLogInternalService.get(id);
        return new FetcherLog();
    }
}
