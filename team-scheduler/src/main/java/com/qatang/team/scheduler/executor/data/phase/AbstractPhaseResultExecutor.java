package com.qatang.team.scheduler.executor.data.phase;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.retrymodel.ApiRetryCallback;
import com.qatang.team.core.retrymodel.ApiRetryTaskExecutor;
import com.qatang.team.data.bean.NumberLotteryData;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.enums.lottery.PhaseStatus;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.service.FetcherLogApiService;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import com.qatang.team.fetcher.service.ProxyValidateLogApiService;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.validator.IProxyValidator;
import com.qatang.team.proxy.validator.ProxyValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.Proxy;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author qatang
 */
public abstract class AbstractPhaseResultExecutor implements IPhaseResultExecutor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ProxyDataApiService proxyDataApiService;

    @Autowired
    protected FetcherLogApiService fetcherLogApiService;

    @Override
    public void executeFetcher(NumberLotteryData numberLotteryData) {
        if (numberLotteryData == null || !numberLotteryData.getPhaseStatus().equals(PhaseStatus.CLOSED)) {
            logger.info(String.format("数字彩开奖结果抓取定时：彩期数据为空或彩期数据测试状态不是(%s)", ProxyValidateStatus.WAITING_TEST.getName()));
            return;
        }

        // 按分数降序，id升序，获取10个可用代理列表
        // 随机排序

        // 获取可用fetcherType列表


    }

    private ProxyValidateLog generateProxyValidateLog(ProxyData proxyData, ProxyValidatorType proxyValidatorType, LocalDateTime begin, YesNoStatus success, String message) {
        LocalDateTime end = LocalDateTime.now();

        ProxyValidateLog proxyValidateLog = new ProxyValidateLog();
        proxyValidateLog.setProxyId(proxyData.getId());
        proxyValidateLog.setHost(proxyData.getHost());
        proxyValidateLog.setPort(proxyData.getPort());
        proxyValidateLog.setProxyValidatorType(proxyValidatorType);
        proxyValidateLog.setBeginTestTime(begin);
        proxyValidateLog.setEndTestTime(end);
        proxyValidateLog.setSpentMills(begin.until(end, ChronoUnit.MILLIS));
        proxyValidateLog.setSuccess(success);
        proxyValidateLog.setMessage(message);
        return proxyValidateLog;
    }
}
