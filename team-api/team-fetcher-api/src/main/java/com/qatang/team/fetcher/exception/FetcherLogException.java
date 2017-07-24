package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 抓取日志异常
 * @author wp
 * @since 2017/7/20
 */
public class FetcherLogException extends ClientException {
    private static final long serialVersionUID = 6116210485036274685L;

    private static final String errorCode = "400001002";

    public FetcherLogException() {
        super(errorCode, "抓取日志错误");
    }

    public FetcherLogException(String message) {
        super(errorCode, message);
    }
}
