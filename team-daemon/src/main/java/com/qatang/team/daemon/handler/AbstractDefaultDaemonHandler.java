package com.qatang.team.daemon.handler;

import com.google.common.collect.Lists;
import com.qatang.team.data.bean.DaemonEventTask;
import com.qatang.team.data.exception.DaemonEventTaskException;
import com.qatang.team.data.service.DaemonEventTaskApiService;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * author: sunshow.
 */
public abstract class AbstractDefaultDaemonHandler extends AbstractDaemonHandler {

    @Autowired
    protected DaemonEventTaskApiService daemonEventTaskApiService;

    @Override
    protected void insertPersistentTaskList(List<DaemonEventTask> taskList) {
        if (taskList == null) {
            return;
        }
        for (DaemonEventTask daemonEventTask : taskList) {
            try {
                daemonEventTaskApiService.savePendingTask(daemonEventTask);
            } catch (DaemonEventTaskException e) {
                logger.error("插入守护事件任务失败", e);
            }
        }
    }

    @Override
    protected void updatePersistentTaskList(List<DaemonEventTask> taskList) {
        if (taskList == null) {
            return;
        }
        for (DaemonEventTask daemonEventTask : taskList) {
            try {
                daemonEventTaskApiService.update(daemonEventTask);
            } catch (DaemonEventTaskException e) {
                logger.error("更新守护事件任务失败", e);
            }
        }
    }

    @Override
    protected void removePersistentTaskList(List<DaemonEventTask> taskList) {
        if (taskList == null) {
            return;
        }
        Date date = new Date();
        for (DaemonEventTask daemonEventTask : taskList) {
            // 已经过期的任务, 不做处理, 可能是服务重启了等情况要回收
            if (daemonEventTask.getExecuteTime().before(date)) {
                continue;
            }
            try {
                // 默认直接作废
                daemonEventTaskApiService.updateStatus(daemonEventTask.getId(), DaemonEventStatus.OBSOLETED, DaemonEventStatus.PENDING);
            } catch (DaemonEventTaskException e) {
                logger.error("更新守护事件任务失败", e);
            }
        }
    }

    @Override
    protected List<DaemonEventTask> findNextTimeTaskList() {
        Collection<DaemonEventTask> pendingTaskCollection = this.findPendingTaskOrderByExecuteTimeAsc();
        if (pendingTaskCollection != null && !pendingTaskCollection.isEmpty()) {
            List<DaemonEventTask> taskList = Lists.newArrayList();
            Date executeTime = null;

            for (DaemonEventTask daemonEventTask : pendingTaskCollection) {
                if (executeTime == null) {
                    executeTime = daemonEventTask.getExecuteTime();
                }
                if (executeTime.equals(daemonEventTask.getExecuteTime())) {
                    taskList.add(daemonEventTask);
                } else {
                    break;
                }
            }

            return taskList;
        }
        return null;
    }

    /**
     * @return 待执行的任务列表, 按执行时间升序排列
     */
    abstract protected Collection<DaemonEventTask> findPendingTaskOrderByExecuteTimeAsc();
}
