package com.qatang.team.scheduler.exception;

/**
 * 定时器异常
 * Created by sunshow on 5/7/15.
 */
public class SchedulerException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public SchedulerException() {
        this("定时器异常");
    }

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(Throwable cause) {
        super(cause);
    }
}
