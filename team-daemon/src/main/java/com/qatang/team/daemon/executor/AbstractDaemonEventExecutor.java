package com.qatang.team.daemon.executor;

import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.daemon.exception.executor.DaemonEventExecutorException;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.data.service.DaemonEventTaskApiService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: sunshow.
 */
public abstract class AbstractDaemonEventExecutor implements IDaemonEventExecutor {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DaemonEventTaskApiService daemonEventTaskApiService;

    @Override
    public void execute(DaemonEventTask daemonEventTask) throws DaemonEventExecutorException {
        try {
            logger.error("[{}]开始处理守护事件任务, id={}, type={}, lotteryType={}, phase={}, matchNum={}, executeTime={}", daemonEventTask.getLotteryType().getName(),
                    daemonEventTask.getId(), daemonEventTask.getType(),
                    daemonEventTask.getLotteryType(), daemonEventTask.getPhase(), daemonEventTask.getMatchNum(),
                    CoreDateUtils.formatLocalDateTime(daemonEventTask.getExecuteTime()));

            // 获取一次最新的任务数据
            daemonEventTask = daemonEventTaskApiService.get(daemonEventTask.getId());

            if (daemonEventTask.getStatus() != DaemonEventStatus.SENDING) {
                logger.error("任务已经不是已派发未执行状态, 忽略处理, id={}, status={}", daemonEventTask.getId(), daemonEventTask.getStatus());
                return;
            }

            // 将任务更新成执行中状态, 避免重复处理
            try {
//                daemonEventTaskApiService.updateStatus(daemonEventTask.getId(), DaemonEventStatus.EXECUTING, DaemonEventStatus.SENDING);
            } catch (DaemonEventTaskException e) {
                logger.error("更新任务到执行中失败, 忽略处理, id=" + daemonEventTask.getId(), e);
                return;
            }

            // 进入实际执行
            this.executeTask(daemonEventTask);

            // 执行成功更新状态
//            daemonEventTaskApiService.updateStatus(daemonEventTask.getId(), DaemonEventStatus.EXECUTED, DaemonEventStatus.EXECUTING);

            logger.error("[{}]成功处理守护事件任务, id={}, type={}, lotteryType={}, phase={}, matchNum={}, executeTime={}", daemonEventTask.getLotteryType().getName(),
                    daemonEventTask.getId(), daemonEventTask.getType(),
                    daemonEventTask.getLotteryType(), daemonEventTask.getPhase(), daemonEventTask.getMatchNum(),
                    CoreDateUtils.formatLocalDateTime(daemonEventTask.getExecuteTime()));
        } catch (Exception e) {
            logger.error("执行守护事件任务发生异常, 重新将任务设置成待处理, id=" + daemonEventTask.getId(), e);
//            daemonEventTaskApiService.updateStatus(daemonEventTask.getId(), DaemonEventStatus.PENDING, DaemonEventStatus.EXECUTING);
            throw new DaemonEventExecutorException("执行守护事件任务发生异常");
        }
    }

    /**
     * 实际执行守护事件任务, 执行失败必须抛出异常
     * @param daemonEventTask 守护事件任务
     */
    abstract protected void executeTask(DaemonEventTask daemonEventTask) throws Exception;
}
