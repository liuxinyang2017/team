package com.qatang.team.fetcher.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.entity.NumberLotteryFetchResultDataEntity;

/**
 * @author wp
 * @since 2017/7/23
 */
public interface NumberLotteryFetchResultDataRepository extends BaseRepository<NumberLotteryFetchResultDataEntity, Long> {

    /**
     * 根据彩种、彩期获取数字彩彩果
     * @param lotteryType 彩种
     * @param phase 彩期
     * @return 数字彩开奖结果抓取数据
     */
    NumberLotteryFetchResultDataEntity findByLotteryTypeAndPhase(LotteryType lotteryType, String phase);
}
