package com.qatang.team.core.thread;


/**
 * @author Sunshow
 *
 */
public class ThreadContainer {
	
	private static int counter = 0;
	
	private IThreadRunnable runnable;
	private String name;
	
	public ThreadContainer(IThreadRunnable runnable) {
		this(runnable, null);
	}
	
	public ThreadContainer(IThreadRunnable runnable, String name) {
		this.runnable = runnable;
		this.name = name;
		if (this.name == null) {
			this.name = "Custom Thread " + counter;
			counter ++;
		}
	}
	
	public boolean isRunning() {
		return this.runnable.isRunning();
	}
	
	public void start() {
		if (this.isRunning()) {
			// 防止重复启动
			return;
		}
        Thread thread = new Thread(this.runnable, this.name);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void stop() {
		this.runnable.executeStop();
	}
	
	public void executeNotify() {
		this.runnable.executeNotify();
	}

	public void setBeforeRunWaitTime(long beforeRunWaitTime) {
		this.runnable.setBeforeRunWaitTime(beforeRunWaitTime);
	}

}
