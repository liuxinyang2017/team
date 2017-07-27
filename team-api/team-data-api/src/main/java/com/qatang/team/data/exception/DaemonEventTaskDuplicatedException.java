package com.qatang.team.data.exception;

import com.qatang.team.core.exception.ClientException;

/**
 * 守护事件任务重复异常
 * @author sunshow
 */
public class DaemonEventTaskDuplicatedException extends ClientException {

    private static final long serialVersionUID = 1911334032737231916L;

    private static final String errorCode = "400001004";

    public DaemonEventTaskDuplicatedException() {
        super(errorCode, "守护事件任务重复异常");
    }

    public DaemonEventTaskDuplicatedException(String message) {
        super(errorCode, message);
    }
}
