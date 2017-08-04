package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryResultData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;

/**
 * @author wp
 * @since 2017/7/23
 */
public interface NumberLotteryFetchResultDataInternalService extends BaseInternalSerivce {

    /**
     * 创建数字彩开奖结果抓取数据对象
     * @param fetchNumberLotteryResultData 数字彩开奖结果抓取数据对象
     * @return 保存后的数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    FetchNumberLotteryResultData save(FetchNumberLotteryResultData fetchNumberLotteryResultData) throws NumberLotteryFetchResultDataException;

    /**
     * 分页查询数字彩开奖结果抓取数据对象
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    ApiResponse<FetchNumberLotteryResultData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryFetchResultDataException;

    /**
     * 按id获取数字彩开奖结果抓取数据对象
     * @param id 编码
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    FetchNumberLotteryResultData get(Long id) throws NumberLotteryFetchResultDataException;

    /**
     * 修改数字彩开奖结果抓取数据对象
     * @param fetchNumberLotteryResultData 数字彩开奖结果抓取数据对象
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    FetchNumberLotteryResultData update(FetchNumberLotteryResultData fetchNumberLotteryResultData) throws NumberLotteryFetchResultDataException;

    /***
     * 根据彩种彩期抓取来源数据类型获取开奖结果抓取信息
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取来源数据类型
     * @return 获取到的开奖结果抓取信息
     * @throws NumberLotteryFetchResultDataException 异常
     */
    FetchNumberLotteryResultData getByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType) throws NumberLotteryFetchResultDataException;



}
