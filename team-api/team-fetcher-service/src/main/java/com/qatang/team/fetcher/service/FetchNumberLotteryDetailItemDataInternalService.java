package com.qatang.team.fetcher.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.bean.FetchNumberLotteryDetailItemData;
import com.qatang.team.fetcher.exception.FetchNumberLotteryDetailItemDataException;

import java.util.List;

/**
 * 数字彩开奖详情抓取数据内部服务接口
 * @author wp
 * @since 2017/8/4
 */
public interface FetchNumberLotteryDetailItemDataInternalService extends BaseInternalSerivce {

    /**
     * 根据id获取开奖详情抓取数据内部子对象
     * @param id 主键id
     * @return 获取到的数据对象
     * @throws FetchNumberLotteryDetailItemDataException 异常
     */
    FetchNumberLotteryDetailItemData get(Long id) throws FetchNumberLotteryDetailItemDataException;

    /**
     * 根据详情id获取 子对象数据列表
     * @param detailId 详情对象id
     * @return 获取到的子对象数据列表
     * @throws FetchNumberLotteryDetailItemDataException 异常
     */
    List<FetchNumberLotteryDetailItemData> getByDetailId(Long detailId) throws FetchNumberLotteryDetailItemDataException;

    /**
     * 自定义查询抓取详情子对象信息
     * @param request 自定义查询条件
     * @param requestPage 查询页
     * @return 查询到的信息
     * @throws FetchNumberLotteryDetailItemDataException 异常
     */
    ApiResponse<FetchNumberLotteryDetailItemData> findAll(ApiRequest request, ApiRequestPage requestPage) throws FetchNumberLotteryDetailItemDataException;

}
