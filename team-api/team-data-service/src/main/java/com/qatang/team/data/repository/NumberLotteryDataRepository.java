package com.qatang.team.data.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.data.entity.NumberLotteryDataEntity;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @author qatang
 */
public interface NumberLotteryDataRepository extends BaseRepository<NumberLotteryDataEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    NumberLotteryDataEntity getByLotteryTypeAndPhase(LotteryType lotteryType, String phase);

    NumberLotteryDataEntity findByLotteryTypeAndPhase(LotteryType lotteryType, String phase);

    NumberLotteryDataEntity findByLotteryTypeAndIsCurrent(LotteryType lotteryType, YesNoStatus isCurrent);
}
