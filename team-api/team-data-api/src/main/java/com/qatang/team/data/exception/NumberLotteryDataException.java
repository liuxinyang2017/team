package com.qatang.team.data.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 数字彩彩果
 * @author qatang
 */
public class NumberLotteryDataException extends ClientException {

    private static final long serialVersionUID = 2377905512105168859L;

    private static final String errorCode = "400001001";

    public NumberLotteryDataException() {
        super(errorCode, "数字彩彩果异常");
    }

    public NumberLotteryDataException(String message) {
        super(errorCode, message);
    }
}
