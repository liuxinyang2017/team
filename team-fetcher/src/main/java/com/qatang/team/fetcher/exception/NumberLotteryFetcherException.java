package com.qatang.team.fetcher.exception;

/**
 * 数字彩抓取器异常
 * Created by sunshow on 5/7/15.
 */
public class NumberLotteryFetcherException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public NumberLotteryFetcherException() {
        this("数字彩抓取器异常");
    }

    public NumberLotteryFetcherException(String message) {
        super(message);
    }

    public NumberLotteryFetcherException(Throwable cause) {
        super(cause);
    }
}
