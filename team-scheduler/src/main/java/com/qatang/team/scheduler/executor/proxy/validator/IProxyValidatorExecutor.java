package com.qatang.team.scheduler.executor.proxy.validator;

import com.qatang.team.fetcher.bean.ProxyData;

/**
 * @author qatang
 */
public interface IProxyValidatorExecutor {
    /**
     * 执行代理测试
     */
    void executeValidator(ProxyData proxyData);
}
