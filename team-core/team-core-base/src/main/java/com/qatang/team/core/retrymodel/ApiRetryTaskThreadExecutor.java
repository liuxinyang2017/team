package com.qatang.team.core.retrymodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 通过线程的方式异步执行重试回调任务
 * @author Sunshow
 *
 */
public class ApiRetryTaskThreadExecutor {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private ExecutorService executor;

	public ApiRetryTaskThreadExecutor() {
        this(Executors.newCachedThreadPool());
    }

    public ApiRetryTaskThreadExecutor(ExecutorService executor) {
        this.executor = executor;
    }

	public synchronized void init() {
		logger.info("初始化线程池");
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
	}
	
	public synchronized void destroy() {
		logger.info("销毁线程池");
		if (executor != null && !executor.isShutdown()) {
			executor.shutdown();
		}
		executor = null;
	}
	
	/**
	 * 异步执行并返回Future对象供调用者进行后续操作
     * @param callback 回调
     * @param <V> 返回值的类型
     * @return Future对象
     */
	public synchronized <V> Future<V> invokeAsync(ApiRetryCallback<V> callback) {
		if (executor == null) {
			logger.error("线程池未初始化");
            throw new RuntimeException("线程池未初始化或线程池已关闭");
		}
		if (executor.isShutdown()) {
			logger.error("线程池已被关闭");
			return null;
		}
		logger.info("开始执行重试任务：{}，重试次数：{}，重试间隔：{}", callback.getName(), callback.getRetry(), callback.getRetryInterval());
		return executor.submit(callback);
	}
	
    /**
     * 异步执行并支持获取返回结果
     * @param callback 回调
     * @param timeout 等待返回结果超时时间, 如果为0则阻塞等待直到返回结果
     * @param <V> 返回值的类型
     * @return 回调的执行结果
     */
	public <V> V invokeAsyncWithResult(ApiRetryCallback<V> callback, long timeout) {
		Future<V> future = this.invokeAsync(callback);
		if (future == null) {
			throw new RuntimeException("线程池未初始化或线程池已关闭");
		}
		try {
			V result;
			if (timeout == 0) {
				result = future.get();
			} else {
				result = future.get(timeout, TimeUnit.MILLISECONDS);
			}
			return result;
		} catch (Exception e) {
			logger.error("异步读取重试任务的返回结果失败", e);
            throw new RuntimeException(e);
		}
	}
}
