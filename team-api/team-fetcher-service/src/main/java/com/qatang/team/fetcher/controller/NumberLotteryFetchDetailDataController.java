package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.fetcher.bean.NumberLotteryFetchDetailData;
import com.qatang.team.fetcher.service.NumberLotteryFetchDetailDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wp
 * @since 2017/7/23
 */
@RestController
@RequestMapping("/fetcher/numberLotteryFetchDetailData")
public class NumberLotteryFetchDetailDataController extends BaseController {

    @Autowired
    private NumberLotteryFetchDetailDataInternalService numberLotteryFetchDetailDataInternalService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryFetchDetailData create(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) {

        logger.info("开始创建抓取开奖详情对象");
        NumberLotteryFetchDetailData numberLotteryFetchDetailDataResult = numberLotteryFetchDetailDataInternalService.save(numberLotteryFetchDetailData);
        return numberLotteryFetchDetailDataResult;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryFetchDetailData update(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) {
        logger.info("修改抓取开奖详情对象");
        NumberLotteryFetchDetailData numberLotteryFetchDetailDataResult = numberLotteryFetchDetailDataInternalService.update(numberLotteryFetchDetailData);
        return numberLotteryFetchDetailDataResult;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryFetchDetailData get(@RequestParam(name = "id") Long id) {
        logger.info("获取抓取开奖详情对象信息");
        NumberLotteryFetchDetailData numberLotteryFetchDetailDataResult = numberLotteryFetchDetailDataInternalService.get(id);
        return numberLotteryFetchDetailDataResult;
    }
}
