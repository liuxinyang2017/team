package com.qatang.team.fetcher.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.fetcher.entity.FetchNumberLotteryResultDataEntity;

/**
 * @author wp
 * @since 2017/7/23
 */
public interface FetchNumberLotteryResultDataRepository extends BaseRepository<FetchNumberLotteryResultDataEntity, Long> {

    /***
     * 根据彩种彩期抓取来源数据类型获取开奖结果抓取信息
     * @param lotteryType 彩种
     * @param phase 彩期
     * @param fetcherType 抓取来源数据类型
     * @return 获取到的开奖结果抓取信息
     */
    FetchNumberLotteryResultDataEntity findByLotteryTypeAndPhaseAndFetcherType(LotteryType lotteryType, String phase, FetcherType fetcherType);
}
