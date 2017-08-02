package com.qatang.team.daemon.handler;

import com.google.common.collect.Lists;
import com.qatang.team.core.thread.AbstractThreadRunnable;
import com.qatang.team.core.util.CoreDateUtils;
import com.qatang.team.data.bean.DaemonEventTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author sunshow
 */
public abstract class AbstractDaemonHandler extends AbstractThreadRunnable implements IDaemonHandler {

    private long reloadInterval = 10 * 60 * 1000; //重载间隔(毫秒)

    private boolean emptyTaskReloading = true;	//没有任务时自动重载

    private boolean autoTaskReloading = true;   //自动重载任务

    protected volatile boolean reloading = false;   // 重载标记

    @Override
    public void executeReload() {
        synchronized (this) {
            reloading = true;
        }
        logger.error("守护线程接收到重载请求");
        executeNotify();
    }

    @Override
    protected void executeRun() {
        while (running) {
            // reset
            reloading = false;

            // 初始化任务
            initTask();

            // 每次都查找最近一个时间点要执行的所有任务
            List<DaemonEventTask> nextTimeTaskList = this.findNextTimeTaskList();

            if (nextTimeTaskList == null || nextTimeTaskList.isEmpty()) {
                if (!this.isEmptyTaskReloading()) {
                    // 如果没有任务时不自动重载则退出循环
                    logger.error("当前守护线程没有要守护的任务, 且配置了不自动重载, 退出守护");
                    break;
                }

                // 否则进行等待重载
                logger.error("当前守护线程没有要守护的任务, 等待不自动重载");
                synchronized (this) {
                    try {
                        this.wait(this.getReloadInterval());
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }

                continue;
            }

            // 下一个要执行的时间点
            DaemonEventTask daemonEventTask = nextTimeTaskList.get(0);
            LocalDateTime next = daemonEventTask.getExecuteTime();
            LocalDateTime now = LocalDateTime.now();
            logger.error("[{}]守护线程下一个执行事件时间: next={}, now={}, 需要执行的守护任务数量={}", daemonEventTask.getLotteryType().getName(), next.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), nextTimeTaskList.size());

            // 距离下一个执行的时间点的毫秒
            long waitMillis = now.until(next, ChronoUnit.MILLIS);

            // 计算等待时间
            if (waitMillis > 0) {
                if (this.isAutoTaskReloading() && waitMillis > this.getReloadInterval()) {
                    // 如果要自动重载且距离下一个执行的时间点超过了重载间隔, 则等待重载间隔时间后进行重载
                    synchronized (this) {
                        try {
                            this.wait(this.getReloadInterval());
                        } catch (InterruptedException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                    if (reloading) {
                        logger.error("[{}]守护线程接收到重载请求, 开始执行守护重载", daemonEventTask.getLotteryType().getName());
                        continue;
                    }

                    if (!running) {
                        logger.error("[{}]守护线程接收到停止请求, 开始执行守护停止", daemonEventTask.getLotteryType().getName());
                        continue;
                    }

                    logger.error("[{}]守护线程开始自动重载, 自动重载时间间隔: {}", daemonEventTask.getLotteryType().getName(), this.getReloadInterval());
                    continue;
                }

                // 否则等待执行下一个时间点的任务
                synchronized (this) {
                    try {
                        this.wait(waitMillis);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }

                if (reloading) {
                    logger.error("[{}]守护线程接收到重载请求, 开始执行守护重载", daemonEventTask.getLotteryType().getName());
                    continue;
                }

                if (!running) {
                    logger.error("[{}]守护线程接收到停止请求, 开始执行守护停止", daemonEventTask.getLotteryType().getName());
                    continue;
                }
            }

            // 执行任务分发
            this.dispatchTaskList(nextTimeTaskList);
        }
    }

    /**
     * 比较实际应该守护的事件任务列表和已持久化的任务列表
     * 将匹配的任务但时间不一致的根据实际时间进行更新
     * 将还未持久化的任务进行持久化
     */
    protected void initTask() {
        List<DaemonEventTask> persistentTaskList = this.findPersistentTaskList();

        List<DaemonEventTask> actualPendingTaskList = this.findActualPendingTaskList();

        if (persistentTaskList != null && !persistentTaskList.isEmpty()
                && actualPendingTaskList != null && !actualPendingTaskList.isEmpty()) {
            // 找到需要更新的任务列表
            List<DaemonEventTask> toUpdateTaskList = Lists.newArrayList();
            for (DaemonEventTask persistentTask : persistentTaskList) {
                for (DaemonEventTask actualPendingTask : actualPendingTaskList) {
                    if (this.isSameTask(persistentTask, actualPendingTask)
                            && !persistentTask.getExecuteTime().equals(actualPendingTask.getExecuteTime())) {
                        // 同一任务且执行时间不同, 以实际任务为准进行更新
                        persistentTask.setExecuteTime(actualPendingTask.getExecuteTime());
                        toUpdateTaskList.add(persistentTask);
                        break;
                    }
                }
            }
            if (!toUpdateTaskList.isEmpty()) {
                logger.error(String.format("初始化需要更新任务列表size=%s", toUpdateTaskList.size()));
                this.printTaskList(toUpdateTaskList);
                this.updatePersistentTaskList(toUpdateTaskList);
            }
        }

        if (actualPendingTaskList != null && !actualPendingTaskList.isEmpty()) {
            // 找到需要进行持久化插入的任务列表
            List<DaemonEventTask> toInsertTaskList = Lists.newArrayList();
            if (persistentTaskList == null || persistentTaskList.isEmpty()) {
                // 插入所有
                toInsertTaskList.addAll(actualPendingTaskList);
            } else {
                for (DaemonEventTask actualPendingTask : actualPendingTaskList) {
                    boolean found = false;
                    for (DaemonEventTask persistentTask : persistentTaskList) {
                        if (this.isSameTask(actualPendingTask, persistentTask)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // 新增待初始化任务
                        toInsertTaskList.add(actualPendingTask);
                    }
                }
            }
            if (!toInsertTaskList.isEmpty()) {
                logger.error(String.format("初始化需要新增任务列表size=%s", toInsertTaskList.size()));
                this.printTaskList(toInsertTaskList);
                this.insertPersistentTaskList(toInsertTaskList);
            }
        }

        if (persistentTaskList != null && !persistentTaskList.isEmpty()) {
            // 找到已经持久化的但是不在当前实际待执行的任务列表
            List<DaemonEventTask> toRemoveTaskList = Lists.newArrayList();
            if (actualPendingTaskList == null || actualPendingTaskList.isEmpty()) {
                // 插入所有
                toRemoveTaskList.addAll(persistentTaskList);
            } else {
                for (DaemonEventTask persistentTask : persistentTaskList) {
                    boolean found = false;
                    for (DaemonEventTask actualTask : actualPendingTaskList) {
                        if (this.isSameTask(persistentTask, actualTask)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // 新增待移除任务任务
                        toRemoveTaskList.add(persistentTask);
                    }
                }
            }
            if (!toRemoveTaskList.isEmpty()) {
                logger.error(String.format("初始化需要删除任务列表size=%s", toRemoveTaskList.size()));
                this.printTaskList(toRemoveTaskList);
                this.removePersistentTaskList(toRemoveTaskList);
            }
        }
    }

    /**
     * 打印任务列表明细日志
     * @param taskList 任务列表
     */
    protected void printTaskList(List<DaemonEventTask> taskList) {
        taskList.forEach(daemonEventTask -> logger.error("任务详情, id={}, lotteryType={}, phase={}, matchNum={}, type={}, status={}, executeTime={}",
                daemonEventTask.getId(),
                daemonEventTask.getLotteryType(),
                daemonEventTask.getPhase(),
                daemonEventTask.getMatchNum(),
                daemonEventTask.getType(),
                daemonEventTask.getStatus(),
                CoreDateUtils.formatLocalDateTime(daemonEventTask.getExecuteTime())));
    }

    /**
     * 持久化新增任务
     * @param taskList 任务列表
     */
    abstract protected void insertPersistentTaskList(List<DaemonEventTask> taskList);

    /**
     * 持久化更新任务
     * @param taskList 任务列表
     */
    abstract protected void updatePersistentTaskList(List<DaemonEventTask> taskList);

    /**
     * 持久化移除任务
     * @param taskList 任务列表
     */
    abstract protected void removePersistentTaskList(List<DaemonEventTask> taskList);

    /**
     * 分发执行任务
     * @param taskList 待执行的任务列表
     */
    abstract protected void dispatchTaskList(List<DaemonEventTask> taskList);

    /**
     * @return 已持久化的待执行的守护事件任务列表
     */
    abstract protected List<DaemonEventTask> findPersistentTaskList();

    /**
     * @return 实际需要执行的守护事件任务列表
     */
    abstract protected List<DaemonEventTask> findActualPendingTaskList();

    /**
     * @param task1 任务1
     * @param task2 任务2
     * @return 是否是同一个守护事件任务
     */
    abstract protected boolean isSameTask(DaemonEventTask task1, DaemonEventTask task2);

    /**
     * @return 下一个时间点要执行的任务列表
     */
    abstract protected List<DaemonEventTask> findNextTimeTaskList();

    public long getReloadInterval() {
        return reloadInterval;
    }

    public void setReloadInterval(long reloadInterval) {
        this.reloadInterval = reloadInterval;
    }

    public boolean isEmptyTaskReloading() {
        return emptyTaskReloading;
    }

    public void setEmptyTaskReloading(boolean emptyTaskReloading) {
        this.emptyTaskReloading = emptyTaskReloading;
    }

    public boolean isAutoTaskReloading() {
        return autoTaskReloading;
    }

    public void setAutoTaskReloading(boolean autoTaskReloading) {
        this.autoTaskReloading = autoTaskReloading;
    }
}
