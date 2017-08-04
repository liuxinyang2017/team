package com.qatang.team.data.service;

import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface NumberLotteryDetailDataInternalService extends BaseInternalSerivce {
    /**
     * 批量创建
     * @param numberLotteryDetailDataList 数字彩开奖详情列表
     * @return 数字彩开奖详情列表
     * @throws NumberLotteryDetailDataException
     */
    List<NumberLotteryDetailData> batchSave(List<NumberLotteryDetailData> numberLotteryDetailDataList) throws NumberLotteryDetailDataException;

    /**
     * 更新
     * @param numberLotteryDetailData 数字彩开奖详情
     * @return 数字彩开奖详情
     * @throws NumberLotteryDetailDataException
     */
    NumberLotteryDetailData update(NumberLotteryDetailData numberLotteryDetailData) throws NumberLotteryDetailDataException;

    /**
     * 获取数字彩开奖详情
     * @param id 编码
     * @return 数字彩开奖详情
     * @throws NumberLotteryDetailDataException
     */
    NumberLotteryDetailData get(Long id) throws NumberLotteryDetailDataException;

    /**
     * 数字彩开奖详情自定义查询
     * @param request 自定义组合查询条件
     * @param requestPage 分页和排序条件
     * @return 分页组织的数字彩开奖详情
     * @throws NumberLotteryDetailDataException
     */
    ApiResponse<NumberLotteryDetailData> findAll(ApiRequest request, ApiRequestPage requestPage) throws NumberLotteryDetailDataException;

    /**
     * 根据数字彩彩果对象id获取详情信息列表
     * @param lotteryDataId 数字彩彩果对象id
     * @return 获取到的详情列表
     * @throws NumberLotteryDetailDataException
     */
    List<NumberLotteryDetailData> findBylotteryDataId(Long lotteryDataId) throws NumberLotteryDetailDataException;
}
