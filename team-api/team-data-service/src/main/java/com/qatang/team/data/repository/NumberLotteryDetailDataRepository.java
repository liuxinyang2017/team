package com.qatang.team.data.repository;

import com.qatang.team.core.repository.BaseRepository;
import com.qatang.team.data.entity.NumberLotteryDetailDataEntity;

import java.util.List;

/**
 * @author qatang
 */
public interface NumberLotteryDetailDataRepository extends BaseRepository<NumberLotteryDetailDataEntity, Long> {

    /**
     * 根据彩果id获取详情信息列表
     * @param lotteryDataId 彩果id
     * @return 获取到的彩果信息列表
     */
    List<NumberLotteryDetailDataEntity> findBylotteryDataId(Long lotteryDataId);
}
