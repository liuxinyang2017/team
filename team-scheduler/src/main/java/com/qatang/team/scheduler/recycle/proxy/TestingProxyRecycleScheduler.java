package com.qatang.team.scheduler.recycle.proxy;

import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.scheduler.executor.proxy.validator.IProxyValidatorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 测试中代理回收定时
 * @author qatang
 */
@Component
public class TestingProxyRecycleScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IProxyValidatorExecutor proxyValidatorExecutor;

    @Autowired
    public TestingProxyRecycleScheduler(@Qualifier("commonProxyValidatorExecutor") IProxyValidatorExecutor proxyValidatorExecutor) {
        this.proxyValidatorExecutor = proxyValidatorExecutor;
    }

    @Scheduled(fixedDelay = 6000)
    public void run() {
        try {
            // 查询所有状态为 测试中 且 创建时间小于一天前 的代理数据对象

            // 循环 更新状态为 待测试
            ProxyData proxyData = null;

            // 开始测试
            proxyValidatorExecutor.executeValidator(proxyData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
