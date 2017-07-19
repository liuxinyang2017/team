package com.qatang.team.fetcher.exception;

/**
 * 数字彩彩果
 * @author qatang
 */
public class ProxyDataException extends RuntimeException {

    private static final long serialVersionUID = 3679513265724469143L;

    public ProxyDataException() {
        this("数字彩彩果");
    }

    public ProxyDataException(String message) {
        super(message);
    }

    public ProxyDataException(Throwable cause) {
        super(cause);
    }
}
