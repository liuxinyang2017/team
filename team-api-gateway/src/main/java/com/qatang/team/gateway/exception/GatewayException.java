package com.qatang.team.gateway.exception;

/**
 * @author wangzhiliang
 */
public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 2521612094747810330L;

    private Integer errorCode;

    public GatewayException() {
        this("网关异常");
    }

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(Integer errorCode, String message) {
        this(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
