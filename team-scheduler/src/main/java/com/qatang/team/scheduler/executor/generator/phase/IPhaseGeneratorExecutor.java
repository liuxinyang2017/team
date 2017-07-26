package com.qatang.team.scheduler.executor.generator.phase;

import com.qatang.team.scheduler.exception.SchedulerException;

/**
 * @author qatang
 */
public interface IPhaseGeneratorExecutor {

    void executeGenerator() throws SchedulerException;
}
