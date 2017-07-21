package com.qatang.team.daemon.executor;


import com.qatang.team.daemon.exception.executor.DaemonEventExecutorException;
import com.qatang.team.data.bean.DaemonEventTask;

/**
 * 守护事件任务的执行器
 * 所有的执行器都必须设计成无状态且幂等, 要保证同一任务可以被反复执行
 * author: sunshow.
 */
public interface IDaemonEventExecutor {

    /**
     * 执行守护事件任务
     * @param daemonEventTask 守护事件任务
     * @throws DaemonEventExecutorException
     */
    void execute(DaemonEventTask daemonEventTask) throws DaemonEventExecutorException;
}
