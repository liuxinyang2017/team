package com.qatang.team.scheduler.quartz.proxy;

import com.qatang.team.core.component.request.ApiPageRequestHelper;
import com.qatang.team.core.request.ApiRequest;
import com.qatang.team.core.request.ApiRequestPage;
import com.qatang.team.core.response.ApiResponse;
import com.qatang.team.core.wrapper.PageableWrapper;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.data.bean.QNumberLotteryData;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.QProxyData;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import com.qatang.team.scheduler.exception.SchedulerException;
import com.qatang.team.scheduler.executor.proxy.validator.IProxyValidatorExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 代理测试定时
 * @author qatang
 */
@Component
public class ProxyValidatorScheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IProxyValidatorExecutor proxyValidatorExecutor;

    @Autowired
    private ProxyDataApiService proxyDataApiService;

    @Autowired
    public ProxyValidatorScheduler(@Qualifier("commonProxyValidatorExecutor") IProxyValidatorExecutor proxyValidatorExecutor) {
        this.proxyValidatorExecutor = proxyValidatorExecutor;
    }

//    @Scheduled(fixedDelay = 6000)
    public void run() {
        try {
            logger.info(String.format("代理测试定时：开始处理所有状态为(%s)的代理数据", ProxyValidateStatus.PENDING.getName()));
            // 查询所有状态为 待处理 的代理数据对象
            ApiRequest apiRequest = ApiRequest.newInstance()
                    .filterEqual(QProxyData.proxyValidateStatus, ProxyValidateStatus.PENDING);
            ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                    .paging(0, 100)
                    .addOrder(QProxyData.id, PageOrderType.ASC);
            PageableWrapper pageableWrapper = new PageableWrapper(apiRequest, apiRequestPage);

            List<ProxyData> proxyDataList = ApiPageRequestHelper.request(pageableWrapper, proxyDataApiService::findAll);
            if (proxyDataList != null && !proxyDataList.isEmpty()) {
                logger.info(String.format("代理测试定时：查询到状态为(%s)的代理数据(%s)条，开始进行测试", ProxyValidateStatus.PENDING.getName(), proxyDataList.size()));
                proxyDataList.forEach(proxyData -> {
                    // 循环 更新状态为 待测试
                    proxyData = proxyDataApiService.updateWaitingTestStatus(proxyData.getId());

                    // 开始测试
                    proxyValidatorExecutor.executeValidator(proxyData);
                });
                logger.info(String.format("代理测试定时：完成代理数据(%s)条的测试", proxyDataList.size()));
            } else {
                logger.info(String.format("代理测试定时：未查询到状态为(%s)的代理数据", ProxyValidateStatus.PENDING.getName()));
            }
            logger.info(String.format("代理测试定时：结束处理所有状态为(%s)的代理数据", ProxyValidateStatus.PENDING.getName()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
