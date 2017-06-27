package com.qatang.team.data.exception;

/**
 * 数字彩彩果
 * @author qatang
 */
public class NumberLotteryDataException extends RuntimeException {

    private static final long serialVersionUID = 2377905512105168859L;

    public NumberLotteryDataException() {
        this("数字彩彩果");
    }

    public NumberLotteryDataException(String message) {
        super(message);
    }

    public NumberLotteryDataException(Throwable cause) {
        super(cause);
    }
}
