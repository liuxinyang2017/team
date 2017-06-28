package com.qatang.team.data.exception;

/**
 * 数字彩详情
 * @author qatang
 */
public class NumberLotteryDetailDataException extends RuntimeException {

    private static final long serialVersionUID = 5919282414358359258L;

    public NumberLotteryDetailDataException() {
        this("数字彩详情");
    }

    public NumberLotteryDetailDataException(String message) {
        super(message);
    }

    public NumberLotteryDetailDataException(Throwable cause) {
        super(cause);
    }
}
