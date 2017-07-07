package com.qatang.team.fetcher.exception;

/**
 * 抓取器异常
 * Created by sunshow on 5/7/15.
 */
public class FetcherException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public FetcherException() {
        this("抓取器异常");
    }

    public FetcherException(String message) {
        super(message);
    }

    public FetcherException(Throwable cause) {
        super(cause);
    }
}
