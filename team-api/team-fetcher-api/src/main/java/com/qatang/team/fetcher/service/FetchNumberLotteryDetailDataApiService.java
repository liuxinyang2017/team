package com.qatang.team.fetcher.service;

import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.exception.FetchNumberLotteryDetailDataException;
import com.qatang.team.fetcher.wrapper.FetchNumberLotteryDetailWrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 数字彩开奖详情抓取数据Api
 * @author wp
 * @since 2017/7/23
 */
@FeignClient(value = "team-fetcher-service")
@RequestMapping(value = "/fetcher/numberLotteryFetchDetailData")
public interface FetchNumberLotteryDetailDataApiService {

    /**
     * 创建数字彩开奖详情抓取数据
     * @param fetchNumberLotteryDetailWrapper 抓取数据详情
     * @return 保存后的抓取数据
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    FetchNumberLotteryDetailData create(@RequestBody FetchNumberLotteryDetailWrapper fetchNumberLotteryDetailWrapper) throws FetchNumberLotteryDetailDataException;

    /**
     * 按id获取开奖详情抓取数据
     * @param id 开奖详情id
     * @return 获取到的抓取数据
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    FetchNumberLotteryDetailData get(@RequestParam("id") Long id) throws FetchNumberLotteryDetailDataException;

    /**
     * 按id获取开奖详情抓取数据
     * @param detailId 开奖详情id
     * @return 获取到的抓取数据
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    @RequestMapping(value = "/getByFetchDetailId", method = RequestMethod.GET)
    List<FetchNumberLotteryDetailItemData> getByDetailId(@RequestParam("id") Long detailId) throws FetchNumberLotteryDetailDataException;

    /**
     * 根据彩种彩期获取开奖详情数据
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取数据来源类型
     * @return 抓取数据
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    @RequestMapping(value = "/getByLotteryTypeAndPhaseAndFetcherType", method = RequestMethod.POST)
    FetchNumberLotteryDetailData getByLotteryTypeAndPhaseAndFetcherType(@RequestParam("lotteryType") LotteryType lotteryType, @RequestParam("phase") String phase, @RequestParam("fetcher") FetcherType fetcherType) throws FetchNumberLotteryDetailDataException;

    /**
     * 自定义查询开奖详情抓取数据
     * @param pageableWrapper 查询条件
     * @return 分页组织的开奖详情抓取数据信息查询列表
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    ApiResponse<FetchNumberLotteryDetailData> findAll(@RequestBody PageableWrapper pageableWrapper) throws FetchNumberLotteryDetailDataException;
}
