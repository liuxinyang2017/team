package com.qatang.team.scheduler.quartz.generator.phase;

import com.qatang.team.scheduler.executor.generator.phase.IPhaseGeneratorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数字彩彩期定时
 * @author qatang
 */
@Component
public class NumberLotteryPhaseGeneratorScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IPhaseGeneratorExecutor phaseGeneratorExecutor;

    @Autowired
    public NumberLotteryPhaseGeneratorScheduler(@Qualifier("numberLotteryPhaseGeneratorExecutor") IPhaseGeneratorExecutor phaseGeneratorExecutor) {
        this.phaseGeneratorExecutor = phaseGeneratorExecutor;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000L, initialDelay = 5000L)
    public void run() {
        try {
            phaseGeneratorExecutor.executeGenerator();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
