package com.qatang.team.data.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 数字彩详情
 * @author qatang
 */
public class NumberLotteryDetailDataException extends ClientException {

    private static final long serialVersionUID = 5919282414358359258L;

    private static final String errorCode = "400001002";

    public NumberLotteryDetailDataException() {
        super(errorCode, "数字彩彩果详情异常");
    }

    public NumberLotteryDetailDataException(String message) {
        super(errorCode, message);
    }
}
