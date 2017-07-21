package com.qatang.team.generator.phase.worker;

import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.generator.phase.bean.PhaseInfo;
import com.qatang.team.generator.phase.exception.GeneratorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author qatang
 */
public abstract class AbstractPhaseGenerator implements IPhaseGenerator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认生成期数
     */
    protected final int defaultGenerateCount = 100;

    @Override
    public List<PhaseInfo> generate(PhaseInfo startPhaseInfo) throws GeneratorException {
        return this.generate(startPhaseInfo, defaultGenerateCount);
    }
}
