package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 代理数据异常
 * @author wangzhiliang
 */
public class ProxyDataException extends ClientException {

    private static final String errorCode = "400001001";
    private static final long serialVersionUID = 2779799930610976576L;

    public ProxyDataException() {
        super(errorCode, "操作代理数据错误");
    }

    public ProxyDataException(String message) {
        super(errorCode, message);
    }
}
