package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.exception.NumberLotteryFetchDetailDataException;

/**
 * 数字彩开奖详情抓取数据对象服务
 * @author wp
 * @since 2017/7/23
 */
public interface NumberLotteryFetchDetailDataInternalService extends BaseInternalSerivce {

    /**
     * 创建数字彩开奖详情抓取数据对象
     * @param fetchNumberLotteryDetailData 数字彩开奖详情抓取数据对象
     * @return 保存后的数字彩开奖详情抓取数据对象
     * @throws NumberLotteryFetchDetailDataException 异常
     */
    FetchNumberLotteryDetailItemData save(FetchNumberLotteryDetailItemData fetchNumberLotteryDetailData) throws NumberLotteryFetchDetailDataException;

    /**
     * 分页查询数字彩开奖详情抓取数据对象
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 数字彩开奖详情抓取数据对象
     * @throws NumberLotteryFetchDetailDataException 异常
     */
    ApiResponse<FetchNumberLotteryDetailItemData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryFetchDetailDataException;

    /**
     * 按id获取数字彩开奖详情抓取数据对象
     * @param id 编码
     * @return 抓取日志
     * @throws NumberLotteryFetchDetailDataException 异常
     */
    FetchNumberLotteryDetailItemData get(Long id) throws NumberLotteryFetchDetailDataException;

    /**
     * 修改数字彩开奖详情抓取数据对象
     * @param fetchNumberLotteryDetailDataException 数字彩开奖详情抓取数据对象
     * @return 修改后的数字彩开奖详情抓取数据
     * @throws NumberLotteryFetchDetailDataException 异常
     */
    FetchNumberLotteryDetailItemData update(FetchNumberLotteryDetailItemData fetchNumberLotteryDetailDataException) throws NumberLotteryFetchDetailDataException;

    /***
     * 根据彩种彩期获取开奖详情抓取数据
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取数据来源类型
     * @return 查询结果
     */
    FetchNumberLotteryDetailItemData getByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType);
}
