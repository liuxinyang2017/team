package com.qatang.team.data.service;

import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;

/**
 * @author qatang
 */
public interface NumberLotteryDataInternalService extends BaseInternalSerivce {
    /**
     * 创建
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData save(NumberLotteryData numberLotteryData) throws NumberLotteryDataException;
}
