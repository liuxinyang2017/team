package com.qatang.team.daemon.exception.executor;

/**
 * 守护事件执行器异常对象
 * @author sunshow
 */
public class DaemonEventExecutorException extends RuntimeException {

    private static final long serialVersionUID = 4391402813592337284L;

    public DaemonEventExecutorException() {
        this("守护事件执行器操作异常");
    }

    public DaemonEventExecutorException(String message) {
        super(message);
    }

    public DaemonEventExecutorException(Throwable cause) {
        super(cause);
    }

    public DaemonEventExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
}
