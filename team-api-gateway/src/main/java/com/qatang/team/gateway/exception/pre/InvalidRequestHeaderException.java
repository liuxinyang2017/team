package com.qatang.team.gateway.exception.pre;

import com.qatang.team.gateway.exception.GatewayException;
import com.qatang.team.gateway.exception.GatewayExceptionType;

/**
 * 不合法的请求头
 * @author wangzhiliang
 */
public class InvalidRequestHeaderException extends GatewayException {

    private static final long serialVersionUID = -2737499713078850253L;

    public InvalidRequestHeaderException() {
        super(GatewayExceptionType.INVALID_REQUEST_HEADER.getValue(), GatewayExceptionType.INVALID_REQUEST_HEADER.getName());
    }
}
