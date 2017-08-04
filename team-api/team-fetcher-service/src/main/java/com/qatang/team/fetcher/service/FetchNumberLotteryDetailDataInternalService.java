package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailData;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.exception.FetchNumberLotteryDetailDataException;

import java.util.List;

/**
 * 数字彩开奖详情抓取数据对象服务
 * @author wp
 * @since 2017/7/23
 */
public interface FetchNumberLotteryDetailDataInternalService extends BaseInternalSerivce {

    /**
     * 创建数字彩开奖详情抓取数据对象
     * @param fetchNumberLotteryDetailData 数字彩开奖详情抓取数据对象
     * @param fetchNumberLotteryDetailItemDataList 数字彩开奖详情抓取数据子对象列表
     * @return 保存后的数字彩开奖详情抓取数据对象
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    FetchNumberLotteryDetailData save(FetchNumberLotteryDetailData fetchNumberLotteryDetailData, List<FetchNumberLotteryDetailItemData> fetchNumberLotteryDetailItemDataList) throws FetchNumberLotteryDetailDataException;

    /**
     * 分页查询数字彩开奖详情抓取数据对象
     * @param request 请求参数
     * @param requestPage 分页信息
     * @return 数字彩开奖详情抓取数据对象
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    ApiResponse<FetchNumberLotteryDetailData> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetchNumberLotteryDetailDataException;

    /**
     * 按id获取数字彩开奖详情抓取数据对象
     * @param id 编码
     * @return 抓取日志
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    FetchNumberLotteryDetailData get(Long id) throws FetchNumberLotteryDetailDataException;

    /**
     * 修改数字彩开奖详情抓取数据对象
     * @param fetchNumberLotteryDetailData 数字彩开奖详情抓取数据对象
     * @return 修改后的数字彩开奖详情抓取数据
     * @throws FetchNumberLotteryDetailDataException 异常
     */
    FetchNumberLotteryDetailData update(FetchNumberLotteryDetailData fetchNumberLotteryDetailData) throws FetchNumberLotteryDetailDataException;

}
