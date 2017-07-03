package com.qatang.team.data.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.enums.lottery.LotteryType;

/**
 * @author qatang
 */
public interface NumberLotteryDataRepository extends BaseRepository<NumberLotteryDataEntity, Long> {

    NumberLotteryDataEntity getByLotteryTypeAndPhase(LotteryType lotteryType, String phase);
}
