package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.NumberLotteryDataSearchable;
import com.qatang.team.data.service.NumberLotteryDataInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangzhiliang
 */
@RestController
@RequestMapping("/data/numberLotteryData")
public class NumberLotteryDataController extends BaseController {
    @Autowired
    private NumberLotteryDataInternalService numberLotteryDataInternalService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryData create(@RequestBody NumberLotteryData numberLotteryData) {

        logger.info("开始创建数字彩开奖号码对象");
        //NumberLotteryData numberLotteryDataResult = numberLotteryDataInternalService.save(numberLotteryData);
        return new NumberLotteryData();
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("删除创建数字彩开奖号码对象, numberLotteryDataId={}", numberLotteryData.getId());
        //numberLotteryDataService.delete(numberLotteryData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryData update(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("修改数字彩开奖号码对象");
        //NumberLotteryData numberLotteryData = numberLotteryDataService.update(numberLotteryData);
        return new NumberLotteryData();
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    NumberLotteryData get(@RequestParam(name = "id") Long id) {
        logger.info("获取数字彩开奖号码对象信息");
        //NumberLotteryData numberLotteryData = numberLotteryDataInternalService.get(id);
        return new NumberLotteryData();
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<NumberLotteryData> find(@RequestBody NumberLotteryDataSearchable numberLotteryDataSearchable) {
        logger.info("自定义查询数字彩开奖号码对象信息");
        //return numberLotteryDataService.find(apiRequest, apiRequestPage);
        return null;
    }
}
