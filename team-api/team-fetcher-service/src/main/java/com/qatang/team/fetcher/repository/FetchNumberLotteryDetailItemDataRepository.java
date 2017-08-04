package com.qatang.team.fetcher.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.fetcher.entity.FetchNumberLotteryDetailItemDataEntity;

import java.util.List;

/**
 * @author wp
 * @since 2017/8/4
 */
public interface FetchNumberLotteryDetailItemDataRepository extends BaseRepository<FetchNumberLotteryDetailItemDataEntity, Long> {

    /**
     * 根据fetchDetailId获取开奖详情抓取数据子对象数据列表
     * @param fetchDetailId 详情id
     * @return 获取到的子对象数据列表
     */
    List<FetchNumberLotteryDetailItemDataEntity> findByFetchDetailId(Long fetchDetailId);
}
