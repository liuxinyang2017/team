package com.qatang.team.core.retrymodel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 重试回调模型
 * @author Sunshow
 *
 */
public abstract class ApiRetryCallback<V> implements Callable<V> {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * 任务名称
	 */
	private String name;
	
	/**
	 * 出错重试次数
	 */
	private int retry = 5;
	
	private long retryInterval = 100;
	
	/**
	 * 是否处于重试状态
	 */
	private boolean inRetry = false;

    public ApiRetryCallback(String name) {
        this(name, 5);
    }

    public ApiRetryCallback(String name, int retry) {
        this(name, retry, 100, TimeUnit.MILLISECONDS);
    }

    public ApiRetryCallback(String name, int retry, long retryInterval, TimeUnit retryIntervalTimeUnit) {
        this.name = name;
        this.retry = retry;
        this.retryInterval = TimeUnit.MILLISECONDS.convert(retryInterval, retryIntervalTimeUnit);
    }

    public ApiRetryCallback() {
        // 随便起个名
        this(String.format("重试调用任务[%s]", System.currentTimeMillis()));
    }
	
	@Override
	public V call() throws Exception {
		boolean warned = false;
		int retryCount = 0;
        Exception executeException;

		do {
			if (retryCount > 0) {
				// 第一次尝试不输出错误日志
				logger.error("重试{}第{}次", name, retryCount);
				inRetry = true;
			}

			try {
				return this.execute();
			} catch (Exception e) {
                executeException = e;
				logger.error("任务执行失败", e);
			}

            try {
                Pair<Boolean, V> pair = this.stopStrategy();
                if (pair.getLeft()) {
                    return pair.getRight();
                }
            } catch (Exception e) {
                logger.error("判断是否退出执行出错, 默认继续重试", e);
            }

            if (!warned) {
				try {
					this.sendPreWarning();
				} catch (Exception e) {
					logger.error("预警处理失败", e);
				}
				warned = true;
			}
			retryCount ++;
			synchronized (this) {
				try {
					this.wait(retryInterval);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} while (this.checkRetry() && retryCount < retry);
		
		try {
			this.sendWarning();
		} catch (Exception e) {
			logger.error("报警处理失败", e);
		}
		
		try {
			errorHandler();
		} catch (Exception e) {
			logger.error("错误处理失败", e);
		}

		throw executeException;
	}
	
	abstract protected V execute() throws Exception;

    /**
     * 是否满足退出条件
     * 在每次调用失败准备重试前执行
     * @return 如果可以退出则左值返回true, 右值为最终返回值
     */
    protected Pair<Boolean, V> stopStrategy() {
        return ImmutablePair.of(false, null);
    }
	
	/**
	 * 发送警告的方法，在重试所有次数最终执行失败时被调用
	 * 默认不发送警告，需要发送警告的重试方法需要覆盖此实现
	 */
	protected void sendWarning() {
		
	}
	
	/**
	 * 发送预警，在第一次失败时被调用
	 */
	protected void sendPreWarning() {
		
	}
	
	/**
	 * 重试完成后最终没有执行成功的错误处理器
	 * 默认不做处理
	 */
	protected void errorHandler() {
		
	}

    protected boolean checkRetry() {
        return true;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public long getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}

	public boolean isInRetry() {
		return inRetry;
	}
}
