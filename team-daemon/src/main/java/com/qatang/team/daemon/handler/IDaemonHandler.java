package com.qatang.team.daemon.handler;

import com.qatang.team.core.thread.IThreadRunnable;

/**
 * 通用守护处理器
 * @author qatang
 * @since 2016-05-23 10:50
 */
public interface IDaemonHandler extends IThreadRunnable {

    /**
     * 重载守护线程
     */
    void executeReload();
}
