package com.qatang.team.fetcher.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.entity.NumberLotteryFetchDetailDataEntity;

/**
 * @author wp
 * @since 2017/7/23
 */
public interface NumberLotteryFetchDetailDataRepository extends BaseRepository<NumberLotteryFetchDetailDataEntity, Long> {

    /**
     * 根据彩种、彩期获取数字彩开奖详情
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取器
     * @return 数字彩开奖详情抓取数据
     */
    NumberLotteryFetchDetailDataEntity findByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType);
}
