package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.service.NumberLotteryFetchResultDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/numberLotteryFetchResultData")
public class NumberLotteryFetchResultDataController extends BaseController{

    @Autowired
    private NumberLotteryFetchResultDataInternalService numberLotteryFetchResultDataInternalService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryFetchResultData create(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) {

        logger.info("开始创建抓取开奖结果对象");
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataInternalService.save(numberLotteryFetchResultData);
        return numberLotteryFetchResultDataResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryFetchResultData update(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) {
        logger.info("修改抓取开奖结果对象");
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataInternalService.update(numberLotteryFetchResultData);
        return numberLotteryFetchResultDataResult;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryFetchResultData get(@RequestParam(name = "id") Long id) {
        logger.info("获取抓取开奖结果对象信息");
        NumberLotteryFetchResultData numberLotteryFetchResultDataResult = numberLotteryFetchResultDataInternalService.get(id);
        return numberLotteryFetchResultDataResult;
    }
}
