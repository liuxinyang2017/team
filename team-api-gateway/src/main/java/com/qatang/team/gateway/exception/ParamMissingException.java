package com.qatang.team.gateway.exception;

/**
 * 不合法的参数
 * @author wangzhiliang
 */
public class ParamMissingException extends GatewayException {

    private static final long serialVersionUID = -2640882354891300740L;

    public ParamMissingException() {
        this(GatewayExceptionType.PARAM_MISSING.getName());
    }

    public ParamMissingException(String message) {
        super(GatewayExceptionType.PARAM_MISSING.getValue(), message);
    }
}
