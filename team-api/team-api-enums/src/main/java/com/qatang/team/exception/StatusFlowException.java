package com.qatang.team.exception;

/**
 * 状态流转异常
 * Created by sunshow on 5/7/15.
 */
public class StatusFlowException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public StatusFlowException() {
        this("状态流转异常");
    }

    public StatusFlowException(String message) {
        super(message);
    }

    public StatusFlowException(Throwable cause) {
        super(cause);
    }
}
