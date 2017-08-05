package com.qatang.team.scheduler.recycle.proxy;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 失败代理回收定时
 * @author qatang
 */
@Component
@ConditionalOnProperty("scheduler.failed.proxy.recycle.on")
public class FailedProxyRecycleScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProxyDataApiService proxyDataApiService;

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000L, initialDelay = 1200 * 1000L)
    public void run() {
        try {
            logger.info(String.format("失败代理回收定时：开始处理所有状态为(%s)的代理数据", ProxyValidateStatus.FAILED.getName()));
            // 查询所有状态为 失败 且 失败次数小于等于3 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.FAILED)
                    .filterLessThan(QProxyData.failedCount, GlobalConstants.PROXY_FAILED_TEST_COUNT)
                    .filterLessThan(QProxyData.createdTime, LocalDateTime.now().minusDays(1));
            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<ProxyData> proxyDataList = ApiPageRequestHelper.request(pageableWrapper, proxyDataApiService::findAll);
            if (proxyDataList != null && !proxyDataList.isEmpty()) {
                logger.info(String.format("失败代理回收定时：查询到状态为(%s)的代理数据(%s)条", ProxyValidateStatus.FAILED.getName(), proxyDataList.size()));
                proxyDataList.forEach(proxyData -> {
                    // 循环 更新状态为 待测试
                    proxyDataApiService.updateWaitingTestStatus(proxyData.getId());
                });
                logger.info(String.format("失败代理回收定时：完成(%s)条代理数据状态更新为(%s)", proxyDataList.size(), ProxyValidateStatus.WAITING_TEST.getName()));
            } else {
                logger.info(String.format("失败代理回收定时：未查询到状态为(%s)的代理数据", ProxyValidateStatus.FAILED.getName()));
            }
            logger.info(String.format("失败代理回收定时：结束处理所有状态为(%s)的代理数据", ProxyValidateStatus.FAILED.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
