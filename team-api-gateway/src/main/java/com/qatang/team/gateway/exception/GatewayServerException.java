package com.qatang.team.gateway.exception;

/**
 * @author wangzhiliang
 */
public class GatewayServerException extends GatewayException {

    private static final long serialVersionUID = -2962086979322031317L;

    public GatewayServerException() {
        super(GatewayExceptionType.INTERNAL_SERVER_ERROR.getValue(), GatewayExceptionType.INTERNAL_SERVER_ERROR.getName());
    }
}
