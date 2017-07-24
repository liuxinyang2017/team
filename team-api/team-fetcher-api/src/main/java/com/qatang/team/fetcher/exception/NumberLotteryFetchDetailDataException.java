package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 数字彩开奖详情抓取数据异常对象
 * @author wp
 * @since 2017/7/23
 */
public class NumberLotteryFetchDetailDataException extends ClientException {
    private static final long serialVersionUID = -5871171740714585918L;

    private static final String errorCode = "400001003";

    public NumberLotteryFetchDetailDataException() {
        super(errorCode, "数字彩开奖详情抓取错误");
    }

    public NumberLotteryFetchDetailDataException(String message) {
        super(errorCode, message);
    }
}
