package com.qatang.team.data.controller;

import com.qatang.team.core.controller.BaseController;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.service.NumberLotteryDataInternalService;
import com.qatang.team.enums.lottery.LotteryType;
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

    /**
     * 创建数字彩彩果
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    NumberLotteryData save(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("开始创建数字彩开奖号码对象");
        NumberLotteryData numberLotteryDataResult = numberLotteryDataInternalService.save(numberLotteryData);
        return numberLotteryDataResult;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    void delete(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("删除创建数字彩开奖号码对象, numberLotteryDataId={}", numberLotteryData.getId());
        //numberLotteryDataService.delete(numberLotteryData);
    }

    /**
     * 修改数字彩彩果
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryData update(@RequestBody NumberLotteryData numberLotteryData) {
        logger.info("修改数字彩开奖号码对象");
        NumberLotteryData numberLotteryDataResult = numberLotteryDataInternalService.update(numberLotteryData);
        return numberLotteryDataResult;
    }

    /**
     * 获取数字彩彩果
     * @param id 数字彩彩果id
     * @return 数字彩彩果
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryData get(@RequestParam Long id) {
        logger.info("获取数字彩开奖号码对象信息");
        NumberLotteryData numberLotteryData = numberLotteryDataInternalService.get(id);
        return numberLotteryData;
    }

    /**
     * 数字彩彩果信息自定义查询
     * @param pageableWrapper 自定义组合查询条件、分页和排序条件
     * @return 分页组织的数字彩彩果信息查询列表
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    ApiResponse<NumberLotteryData> find(@RequestBody PageableWrapper pageableWrapper) {
        logger.info("自定义查询数字彩开奖号码对象信息");
        ApiRequest request = pageableWrapper.getRequest();
        ApiRequestPage requestPage = pageableWrapper.getRequestPage();
        return numberLotteryDataInternalService.findAll(request, requestPage);
    }

    /**
     * 指定当前期
     * @param lotteryType 彩种
     * @param phase 彩期
     */
    @RequestMapping(value = "/specifyCurrentPhase", method = RequestMethod.POST)
    void specifyCurrentPhase(@RequestParam LotteryType lotteryType, @RequestParam String phase) {
        numberLotteryDataInternalService.specifyCurrentPhase(lotteryType, phase);
    }
}
