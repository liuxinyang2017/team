package com.qatang.team.data.exception;

/**
 * 守护事件任务重复异常
 * @author sunshow
 */
public class DaemonEventTaskDuplicatedException extends DaemonEventTaskException {

    private static final long serialVersionUID = 1911334032737231916L;

    public DaemonEventTaskDuplicatedException() {
        this("守护事件任务重复异常");
    }

    public DaemonEventTaskDuplicatedException(String message) {
        super(message);
    }

    public DaemonEventTaskDuplicatedException(Throwable cause) {
        super(cause);
    }

    public DaemonEventTaskDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
