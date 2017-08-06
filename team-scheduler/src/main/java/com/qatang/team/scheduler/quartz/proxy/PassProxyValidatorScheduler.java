package com.qatang.team.scheduler.quartz.proxy;

import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import com.qatang.team.scheduler.executor.proxy.validator.IProxyValidatorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 已通过代理评分定时
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.pass.proxy.validator.on")
public class PassProxyValidatorScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("passProxyValidatorExecutor")
    private IProxyValidatorExecutor passProxyValidatorExecutor;

    @Autowired
    private ProxyDataApiService proxyDataApiService;

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 30 * 1000L)
    public void run() {
        try {
            logger.info(String.format("已通过代理评分定时：开始处理所有状态为(%s)的代理数据", ProxyValidateStatus.PASS.getName()));
            // 查询所有状态为 已通过 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.PASS);
            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<ProxyData> proxyDataList = ApiPageRequestHelper.request(pageableWrapper, proxyDataApiService::findAll);
            if (proxyDataList != null && !proxyDataList.isEmpty()) {
                logger.info(String.format("已通过代理评分定时：开始进行测试，查询到状态为(%s)的代理数据(%s)条", ProxyValidateStatus.PASS.getName(), proxyDataList.size()));
                CountDownLatch latch = new CountDownLatch(proxyDataList.size());
                proxyDataList.forEach(proxyData -> this.doExecute(proxyData, latch));
                latch.await();
                logger.info(String.format("已通过代理评分定时：完成(%s)条代理数据的测试", proxyDataList.size()));
            } else {
                logger.info(String.format("已通过代理评分定时：未查询到状态为(%s)的代理数据", ProxyValidateStatus.PASS.getName()));
            }
            logger.info(String.format("已通过代理评分定时：结束处理所有状态为(%s)的代理数据", ProxyValidateStatus.PASS.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doExecute(ProxyData proxyData, CountDownLatch latch) {
        executor.submit(() -> {
            try {
                proxyDataApiService.updateBeginTestTime(proxyData.getId(), LocalDateTime.now());
                // 开始测试
                passProxyValidatorExecutor.executeValidator(proxyData);
                proxyDataApiService.updateEndTestTime(proxyData.getId(), LocalDateTime.now());
            } catch (Exception e) {
                logger.error("已通过代理评分定时：由于异常中断测试流程");
                logger.error(e.getMessage(), e);
            }
            latch.countDown();
        });
    }
}
