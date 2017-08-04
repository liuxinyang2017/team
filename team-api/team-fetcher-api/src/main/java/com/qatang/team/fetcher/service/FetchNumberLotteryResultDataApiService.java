package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
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
public interface FetchNumberLotteryResultDataApiService {

    /***
     * 创建开奖结果抓取数据
     * @param fetchNumberLotteryResultData 抓取数据
     * @return 保存后的抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    FetchNumberLotteryResultData create(@RequestBody FetchNumberLotteryResultData fetchNumberLotteryResultData) throws NumberLotteryFetchResultDataException;

    /***
     * 按id获取开奖结果抓取数据
     * @param id 开奖结果抓取对象id
     * @return 获取到的抓取数据
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    FetchNumberLotteryResultData get(@RequestParam("id") Long id) throws NumberLotteryFetchResultDataException;

    /***
     * 根据彩种彩期抓取来源数据类型获取开奖结果抓取信息
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取来源数据类型
     * @return 获取到的开奖结果抓取信息
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhaseAndFetcherType", method = RequestMethod.POST)
    FetchNumberLotteryResultData getByLotteryTypeAndPhaseAndFetcherType(@RequestParam("lotteryType")LotteryType lotteryType, @RequestParam("phase")String phase, @RequestParam("fetcherType")FetcherType fetcherType) throws NumberLotteryFetchResultDataException;

    /***
     * 自定义查询开奖结果抓取数据
     * @param pageableWrapper 查询条件
     * @return 分页组织的开奖结果抓取数据信息查询列表
     * @throws NumberLotteryFetchResultDataException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<FetchNumberLotteryResultData> findAll(@RequestBody PageableWrapper pageableWrapper) throws NumberLotteryFetchResultDataException;

}
