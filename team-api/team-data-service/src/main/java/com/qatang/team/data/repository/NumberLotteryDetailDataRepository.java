package com.qatang.team.data.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.data.entity.NumberLotteryDetailDataEntity;
import com.qatang.team.enums.lottery.LotteryType;

/**
 * @author qatang
 */
public interface NumberLotteryDetailDataRepository extends BaseRepository<NumberLotteryDetailDataEntity, Long> {

    NumberLotteryDetailDataEntity getByLotteryTypeAndPhase(LotteryType lotteryType, String phase);
}
