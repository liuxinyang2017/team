package com.qatang.team.core.thread;

/**
 * 带执行间隔的线程执行体
 */
public abstract class AbstractIntervalThreadRunnable extends AbstractThreadRunnable {

    /**
     * 执行间隔
     */
    private long interval;

	@Override
	protected void executeRun() {
		while (running) {
            try {
                this.execute();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
		}
	}

	abstract protected void execute() throws Exception;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
   
