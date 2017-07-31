package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数字彩开奖结果抓取数据服务
 * @author wp
 * @since 2017/7/23
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/numberLotteryFetchResultData")
public interface NumberLotteryFetchResultDataApiService {

    /***
     * 创建开奖结果抓取数据
     * @param numberLotteryFetchResultData 抓取数据
     * @return 保存后的抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    NumberLotteryFetchResultData create(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    /***
     * 修改开奖结果抓取数据
     * @param numberLotteryFetchResultData 开奖抓取数据
     * @return 修改后的开奖结果抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    NumberLotteryFetchResultData update(@RequestBody NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    /***
     * 按id获取开奖结果抓取数据
     * @param id 开奖结果抓取对象id
     * @return 获取到的抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    NumberLotteryFetchResultData get(@RequestParam("id") Long id) throws NumberLotteryFetchResultDataException;

    /***
     * 根据彩种彩期获取开奖结果抓取数据
     * @param lotteryType 彩种
     * @param phsae 彩期
     * @return 抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhase", method = RequestMethod.POST)
    NumberLotteryFetchResultData getByLotteryTypeAndPhase(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phsae) throws NumberLotteryFetchResultDataException;

    /***
     * 自定义查询开奖结果抓取数据
     * @param pageableWrapper 查询条件
     * @return 分页组织的开奖结果抓取数据信息查询列表
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<NumberLotteryFetchResultData> findAll(@RequestBody PageableWrapper pageableWrapper) throws NumberLotteryFetchResultDataException;

}
