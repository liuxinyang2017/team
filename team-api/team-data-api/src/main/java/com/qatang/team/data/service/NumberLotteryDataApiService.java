package com.qatang.team.data.service;

import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;

/**
 * 数字彩彩果服务
 * @author qatang
 */
public interface NumberLotteryDataApiService {

    /**
     * 创建
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData save(NumberLotteryData numberLotteryData) throws NumberLotteryDataException;
}
