package com.qatang.team.data.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;

/**
 * @author qatang
 */
public interface NumberLotteryDataRepository extends BaseRepository<NumberLotteryDataEntity, Long> {

    /**
     * 根据彩种、彩期获取数字彩彩果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩彩果
     */
    NumberLotteryDataEntity findByLotteryTypeAndPhase(LotteryType lotteryType, String phase);

    /**
     * 根据彩种、是否当前期获取数字彩彩果
     * @param lotteryType 彩种
     * @param isCurrent 是否当前期
     * @return 数字彩彩果
     */
    NumberLotteryDataEntity findByLotteryTypeAndIsCurrent(LotteryType lotteryType, YesNoStatus isCurrent);
}
