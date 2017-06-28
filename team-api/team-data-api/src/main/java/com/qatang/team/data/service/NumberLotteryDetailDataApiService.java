package com.qatang.team.data.service;

import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;

import java.util.List;

/**
 * 数字彩详情服务
 * @author qatang
 */
public interface NumberLotteryDetailDataApiService {

    /**
     * 批量创建
     * @param numberLotteryDetailDataList 数字彩详情
     * @return 数字彩详情
     * @throws NumberLotteryDetailDataException
     */
    List<NumberLotteryDetailData> batchSave(List<NumberLotteryDetailData> numberLotteryDetailDataList) throws NumberLotteryDetailDataException;
}
