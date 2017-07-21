package com.qatang.team.core.exception;

/**
 * @author wangzhiliang
 */
public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 6231057742589842996L;

    private String errorCode;

    public ClientException() {
        this("客户端异常");
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(String errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
