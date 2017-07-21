package com.qatang.team.core.thread;

/**
 * @author Sunshow
 *
 */
public interface IThreadRunnable extends Runnable {
	
	/**
	 * 得到线程运行状态
	 * @return
	 */
	boolean isRunning();
	
	/**
	 * 唤醒线程执行
	 */
	void executeNotify();
	
	/**
	 * 通知线程执行退出
	 */
	void executeStop();
	
	/**
	 * 设置启动延时，单位：毫秒
	 * @param beforeRunWaitTime
	 */
	void setBeforeRunWaitTime(long beforeRunWaitTime);
}
