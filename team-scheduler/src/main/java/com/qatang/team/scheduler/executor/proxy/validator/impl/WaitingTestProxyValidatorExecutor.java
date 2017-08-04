package com.qatang.team.scheduler.executor.proxy.validator.impl;

import com.qatang.team.constants.GlobalConstants;
import com.qatang.team.core.retrymodel.ApiRetryCallback;
import com.qatang.team.core.retrymodel.ApiRetryTaskExecutor;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.fetcher.bean.ProxyData;
import com.qatang.team.fetcher.bean.ProxyValidateLog;
import com.qatang.team.fetcher.service.ProxyDataApiService;
import com.qatang.team.fetcher.service.ProxyValidateLogApiService;
import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.validator.IProxyValidator;
import com.qatang.team.proxy.validator.ProxyValidatorFactory;
import com.qatang.team.scheduler.executor.proxy.validator.AbstractProxyValidatorExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author qatang
 */
@Component("waitingTestProxyValidatorExecutor")
public class WaitingTestProxyValidatorExecutor extends AbstractProxyValidatorExecutor {

    @Autowired
    private ProxyDataApiService proxyDataApiService;

    @Autowired
    private ProxyValidateLogApiService proxyValidateLogApiService;

    @Override
    public void executeValidator(ProxyData proxyData) {
        if (proxyData == null || !proxyData.getProxyValidateStatus().equals(ProxyValidateStatus.WAITING_TEST)) {
            logger.info(String.format("代理测试定时：代理数据为空或代理数据测试状态不是(%s)", ProxyValidateStatus.WAITING_TEST.getName()));
            return;
        }

        // 更新proxyData状态，从 待测试 更新为 测试中
        proxyData = proxyDataApiService.updateTestingStatus(proxyData.getId());

        boolean pass = true;
        for (ProxyValidatorType proxyValidatorType : GlobalConstants.PROXY_VALIDATOR_TYPE_LIST) {
            IProxyValidator proxyValidator = ProxyValidatorFactory.getValidator(proxyValidatorType);
            if (proxyValidator == null) {
                logger.error(String.format("代理测试定时：未找到(%s)的代理检测器，跳过该代理检测器", proxyValidatorType.getName()));
                continue;
            }

            ProxyInfo proxyInfo = new ProxyInfo(Proxy.Type.HTTP, proxyData.getHost(), proxyData.getPort(), proxyData.getUsername(), proxyData.getPassword());
            // 重试3次，每次间隔1秒
            LocalDateTime begin = LocalDateTime.now();
            YesNoStatus success = YesNoStatus.YES;
            String message = "代理测试通过";
            try {
                ApiRetryTaskExecutor.invoke(new ApiRetryCallback<Void>() {

                    @Override
                    protected Void execute() throws Exception {
                        proxyValidator.validate(proxyInfo);
                        return null;
                    }
                });
            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
                success = YesNoStatus.NO;
                message = "代理测试失败";
                pass = false;
            }

            // 入库
            ProxyValidateLog proxyValidateLog = generateProxyValidateLog(proxyData, proxyValidatorType, begin, success, message);
            proxyValidateLogApiService.create(proxyValidateLog);
            logger.info(String.format("代理测试定时：代理测试日志入库成功，proxyValidatorType=%s，proxy=%s", proxyValidatorType.getName(), proxyInfo.getUrlStr()));
        }

        // 如果所有代理检测器测试全部通过
        if (pass) {
            proxyDataApiService.updatePassStatus(proxyData.getId());
            logger.info(String.format("代理测试定时：代理测试通过，代理数据状态更新为：%s，proxyId=%s，", ProxyValidateStatus.PASS.getName(), proxyData.getId()));
        } else {
            proxyDataApiService.updateFailedStatus(proxyData.getId());
            logger.info(String.format("代理测试定时：代理测试失败，代理数据状态更新为：%s，proxyId=%s", ProxyValidateStatus.FAILED.getName(), proxyData.getId()));
        }
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
