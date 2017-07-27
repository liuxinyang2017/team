package com.qatang.team.fetcher.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.lottery.LotteryType;
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
    public NumberLotteryFetchDetailData create(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) {
        logger.info("开始创建抓取开奖详情对象");
        return numberLotteryFetchDetailDataInternalService.save(numberLotteryFetchDetailData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public NumberLotteryFetchDetailData update(@RequestBody NumberLotteryFetchDetailData numberLotteryFetchDetailData) {
        logger.info("修改抓取开奖详情对象");
        return numberLotteryFetchDetailDataInternalService.update(numberLotteryFetchDetailData);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public NumberLotteryFetchDetailData get(@RequestParam Long id) {
        logger.info("获取抓取开奖详情对象信息");
        return numberLotteryFetchDetailDataInternalService.get(id);
    }

    @RequestMapping(value = "/getByLotteryTypeAndPhase", method = RequestMethod.POST)
    public NumberLotteryFetchDetailData getByLotteryTypeAndPhase(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phase) {
        logger.info("根据彩种彩期查询开奖详情抓取数据,彩种{},彩期{}", lotteryType.getName(), phase);
        return numberLotteryFetchDetailDataInternalService.getByLotteryTypeAndPhase(lotteryType, phase);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ApiResponse<NumberLotteryFetchDetailData> findAll(@RequestBody PageableWrapper pageableWrapper) {
        ApiRequest apiRequest = pageableWrapper.getRequest();
        ApiRequestPage apiRequestPage = pageableWrapper.getRequestPage();
        logger.info("自定义查询抓取开奖详情对象信息");
        return numberLotteryFetchDetailDataInternalService.findAll(apiRequest, apiRequestPage);
    }
}
