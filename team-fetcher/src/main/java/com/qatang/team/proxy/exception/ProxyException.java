package com.qatang.team.proxy.exception;

/**
 * 代理异常
 * Created by sunshow on 5/7/15.
 */
public class ProxyException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public ProxyException() {
        this("代理异常");
    }

    public ProxyException(String message) {
        super(message);
    }

    public ProxyException(Throwable cause) {
        super(cause);
    }
}
