package com.qatang.team.proxy.validator;

import com.qatang.team.proxy.bean.ProxyInfo;
import com.qatang.team.proxy.exception.ProxyException;

/**
 * @author qatang
 */
public interface IProxyValidator {

    /**
     * 代理测试
     * @param proxyInfo 代理信息
     * @throws ProxyException 异常
     */
    void validate(ProxyInfo proxyInfo) throws ProxyException;
}
