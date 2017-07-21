package com.qatang.team.core.exception;

/**
 * @author wangzhiliang
 */
public class ServerException extends RuntimeException {

    private static final long serialVersionUID = -8525049896841072334L;

    public ServerException() {
        this("服务端异常");
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
