package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.NumberLotteryFetchResultData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchResultDataException;

/**
 * @author wp
 * @since 2017/7/23
 */
public interface NumberLotteryFetchResultDataInternalService extends BaseInternalSerivce {

    /**
     * 创建数字彩开奖结果抓取数据对象
     * @param numberLotteryFetchResultData 数字彩开奖结果抓取数据对象
     * @return 保存后的数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    NumberLotteryFetchResultData save(NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    /**
     * 分页查询数字彩开奖结果抓取数据对象
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    ApiResponse<NumberLotteryFetchResultData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryFetchResultDataException;

    /**
     * 按id获取数字彩开奖结果抓取数据对象
     * @param id 编码
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    NumberLotteryFetchResultData get(Long id) throws NumberLotteryFetchResultDataException;

    /**
     * 修改数字彩开奖结果抓取数据对象
     * @param numberLotteryFetchResultData 数字彩开奖结果抓取数据对象
     * @return 数字彩开奖结果抓取数据对象
     * @throws NumberLotteryFetchResultDataException 异常
     */
    NumberLotteryFetchResultData update(NumberLotteryFetchResultData numberLotteryFetchResultData) throws NumberLotteryFetchResultDataException;

    /***
     * 根据彩种彩期获取开奖结果抓取数据
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 查询结果
     */
    NumberLotteryFetchResultData getByLotteryTypeAndPhase(LotteryType lotteryType, String phase);

}
