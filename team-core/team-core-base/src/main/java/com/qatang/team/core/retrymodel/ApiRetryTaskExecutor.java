package com.qatang.team.core.retrymodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过线程的方式异步执行重试回调任务
 * @author Sunshow
 *
 */
public class ApiRetryTaskExecutor {
	
	protected transient static final Logger logger = LoggerFactory.getLogger(ApiRetryTaskExecutor.class);
	
    /**
	 * 执行并获取返回结果
     * @param callback 回调
     * @param <V> 返回值的类型
	 * @return 回调的执行结果
     */
	public static <V> V invoke(ApiRetryCallback<V> callback) {
		try {
			return callback.call();
		} catch (Exception e) {
			logger.error("异步读取重试任务的返回结果失败", e);
            throw new RuntimeException(e);
		}
	}
}
