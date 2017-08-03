package com.qatang.team.scheduler.recycle.proxy;

import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.util.CoreDateUtils;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 测试中代理回收定时
 * @author qatang
 */
@Component
public class TestingProxyRecycleScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProxyDataApiService proxyDataApiService;

    @Scheduled(fixedDelay = 60 * 60 * 1000L, initialDelay = 35 * 1000L)
    public void run() {
        try {

            logger.info(String.format("测试中代理回收定时：开始处理所有状态为(%s)的代理数据", ProxyValidateStatus.TESTING.getName()));
            // 查询所有状态为 测试中 且 创建时间小于一天前 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.PENDING)
                    .filterLessThan(QProxyData.createdTime, LocalDateTime.now().minusDays(1));
            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<ProxyData> proxyDataList = ApiPageRequestHelper.request(pageableWrapper, proxyDataApiService::findAll);
            if (proxyDataList != null && !proxyDataList.isEmpty()) {
                logger.info(String.format("测试中代理回收定时：查询到状态为(%s)的代理数据(%s)条", ProxyValidateStatus.TESTING.getName(), proxyDataList.size()));
                proxyDataList.forEach(proxyData -> {
                    // 循环 更新状态为 待测试
                    proxyDataApiService.updateWaitingTestStatus(proxyData.getId());
                });
                logger.info(String.format("测试中代理回收定时：完成(%s)条代理数据状态更新为(%s)", proxyDataList.size(), ProxyValidateStatus.WAITING_TEST.getName()));
            } else {
                logger.info(String.format("测试中代理回收定时：未查询到状态为(%s)的代理数据", ProxyValidateStatus.TESTING.getName()));
            }
            logger.info(String.format("测试中代理回收定时：结束处理所有状态为(%s)的代理数据", ProxyValidateStatus.TESTING.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
