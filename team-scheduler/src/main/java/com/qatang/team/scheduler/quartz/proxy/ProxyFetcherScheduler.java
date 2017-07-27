package com.qatang.team.scheduler.quartz.proxy;

import com.qatang.team.scheduler.executor.proxy.fetcher.IProxyFetcherExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 代理抓取定时
 * @author qatang
 */
@Component
public class ProxyFetcherScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IProxyFetcherExecutor proxyFetcherExecutor;

    @Autowired
    public ProxyFetcherScheduler(@Qualifier("commonProxyFetcherExecutor") IProxyFetcherExecutor proxyFetcherExecutor) {
        this.proxyFetcherExecutor = proxyFetcherExecutor;
    }

//    @Scheduled(fixedDelay = 6000)
    public void run() {
        try {
            proxyFetcherExecutor.executeFetcher();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
