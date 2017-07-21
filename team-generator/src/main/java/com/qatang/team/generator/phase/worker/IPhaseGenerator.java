package com.qatang.team.generator.phase.worker;

import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.bean.PhaseInfo;
import com.qatang.team.generator.phase.exception.GeneratorException;

import java.util.List;

/**
 * 彩期生成器
 * @author qatang
 */
public interface IPhaseGenerator {

    /**
     * 生成彩期
     * @param startPhaseInfo 起始彩期
     * @return 彩期信息列表
     * @throws GeneratorException 异常
     */
    List<PhaseInfo> generate(PhaseInfo startPhaseInfo) throws GeneratorException;

    /**
     * 生成彩期
     * @param startPhaseInfo 起始彩期
     * @param count 生成期数
     * @return 彩期信息列表
     * @throws GeneratorException 异常
     */
    List<PhaseInfo> generate(PhaseInfo startPhaseInfo, Integer count) throws GeneratorException;
}
