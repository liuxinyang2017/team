package com.qatang.team.generator.phase.worker;

import com.google.common.collect.Maps;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.worker.impl.SsqPhaseGenerator;

import java.util.Map;

/**
 * @author qatang
 */
public class PhaseGeneratorFactory {
    private static Map<LotteryType, IPhaseGenerator> map = Maps.newHashMap();
    static {
        map.put(LotteryType.FC_SSQ, new SsqPhaseGenerator());
    }

    public static IPhaseGenerator getPhaseGenerator(LotteryType lotteryType) {
        return map.get(lotteryType);
    }
}
