package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 代理验证日志异常
 * @author wp
 * @since 2017/7/23
 */
public class ProxyValidateLogException extends ClientException {
    private static final long serialVersionUID = 2513277840768002803L;

    private static final String errorCode = "400001005";

    public ProxyValidateLogException() {
        super(errorCode, "代理验证日志异常错误");
    }

    public ProxyValidateLogException(String message) {
        super(errorCode, message);
    }
}
