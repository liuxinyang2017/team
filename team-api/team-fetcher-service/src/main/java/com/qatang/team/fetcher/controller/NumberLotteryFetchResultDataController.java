package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.lottery.LotteryType;
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
    public NumberLotteryFetchResultData create(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) {
        logger.info("开始创建抓取开奖结果对象");
        return numberLotteryFetchResultDataInternalService.save(numberLotteryFetchResultData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public NumberLotteryFetchResultData update(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) {
        logger.info("修改抓取开奖结果对象");
        return numberLotteryFetchResultDataInternalService.update(numberLotteryFetchResultData);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public NumberLotteryFetchResultData get(@RequestParam Long id) {
        logger.info("获取抓取开奖结果对象信息");
        return numberLotteryFetchResultDataInternalService.get(id);
    }

    @RequestMapping(value = "/getByLotteryTypeAndPhase", method = RequestMethod.POST)
    public NumberLotteryFetchResultData getByLotteryTypeAndPhase(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phase) {
        logger.info("根据彩种彩期查询开奖结果抓取数据,彩种{},彩期{}", lotteryType.getName(), phase);
        return numberLotteryFetchResultDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<NumberLotteryFetchResultData> find(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取开奖结果对象信息");
        return numberLotteryFetchResultDataInternalService.findAll(apiRequest, apiRequestPage);
    }
}
