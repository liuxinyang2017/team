package com.qatang.team.data.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 守护事件任务异常对象
 * @author sunshow
 */
public class DaemonEventTaskException extends ClientException {

    private static final long serialVersionUID = -4035828129223130343L;

    private static final String errorCode = "400001003";

    public DaemonEventTaskException() {
        super(errorCode, "守护事件任务异常");
    }

    public DaemonEventTaskException(String message) {
        super(errorCode, message);
    }
}
