package com.qatang.team.data.service;

import com.qatang.team.core.service.BaseInternalSerivce;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.exception.NumberLotteryDataException;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

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

    /**
     * 更新
     * @param numberLotteryData 数字彩彩果
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData update(NumberLotteryData numberLotteryData) throws NumberLotteryDataException;

    /**
     * 获取数字彩彩果
     * @param id 编码
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData get(Long id) throws NumberLotteryDataException;

    /**
     * 获取数字彩彩果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getByLotteryTypeAndPhase(LotteryType lotteryType, String phase) throws NumberLotteryDataException;

    /**
     * 获取当前期数字彩彩果
     * @param lotteryType 彩种
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData getCurrentPhase(LotteryType lotteryType) throws NumberLotteryDataException;

    /**
     * 更新彩期状态
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param toStatus 目标状态
     * @param checkStatus 检查状态
     * @return 数字彩彩果
     * @throws NumberLotteryDataException
     */
    NumberLotteryData updatePhaseStatus(LotteryType lotteryType, String phase, PhaseStatus toStatus, PhaseStatus checkStatus) throws NumberLotteryDataException;
}
