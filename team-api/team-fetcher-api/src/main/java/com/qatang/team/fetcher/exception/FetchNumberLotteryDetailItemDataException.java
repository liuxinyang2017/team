package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 数字彩开奖详情抓取数据异常异常对象
 * @author wp
 * @since 2017/8/4
 */
public class FetchNumberLotteryDetailItemDataException extends ClientException {
    private static final long serialVersionUID = 8359745473182460889L;

    private static final String errorCode = "400001006";

    public FetchNumberLotteryDetailItemDataException() {
        super(errorCode, "数字彩开奖详情抓取数据内部子对象错误");
    }

    public FetchNumberLotteryDetailItemDataException(String message) {
        super(errorCode, message);
    }
}
