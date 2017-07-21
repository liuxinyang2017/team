package com.qatang.team.data.exception;

/**
 * 守护事件任务异常对象
 * @author sunshow
 */
public class DaemonEventTaskException extends RuntimeException {

    private static final long serialVersionUID = -4035828129223130343L;

    public DaemonEventTaskException() {
        this("守护事件任务操作异常");
    }

    public DaemonEventTaskException(String message) {
        super(message);
    }

    public DaemonEventTaskException(Throwable cause) {
        super(cause);
    }

    public DaemonEventTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
