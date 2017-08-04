package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 数字彩开奖结果抓取数据异常对象
 * @author wp
 * @since 2017/7/23
 */
public class FetchNumberLotteryResultDataException extends ClientException {
    private static final long serialVersionUID = 2655846652439515538L;

    private static final String errorCode = "400001004";

    public FetchNumberLotteryResultDataException() {
        super(errorCode, "数字彩开奖结果抓取数据错误");
    }

    public FetchNumberLotteryResultDataException(String message) {
        super(errorCode, message);
    }
}
