package com.qatang.team.data.service;

import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryDetailData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.data.exception.NumberLotteryDetailDataException;

/**
 * @author wangzhiliang
 */
public interface NumberLotteryDetailDataInternalService extends BaseInternalSerivce {
    /**
     * 创建
     * @param numberLotteryDetailData 数字彩开奖详情
     * @return 数字彩开间详情
     * @throws NumberLotteryDetailDataException
     */
    NumberLotteryDetailData save(NumberLotteryDetailData numberLotteryDetailData) throws NumberLotteryDetailDataException;
}
