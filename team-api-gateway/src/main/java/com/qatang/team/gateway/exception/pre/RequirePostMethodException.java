package com.qatang.team.gateway.exception.pre;

import com.qatang.team.gateway.exception.GatewayException;
import com.qatang.team.gateway.exception.GatewayExceptionType;

/**
 * 需要使用post请求
 * @author wangzhiliang
 */
public class RequirePostMethodException extends GatewayException {

    private static final long serialVersionUID = 1986079255070348791L;

    public RequirePostMethodException() {
        super(GatewayExceptionType.REQUIRE_POST_METHOD.getValue(), GatewayExceptionType.REQUIRE_POST_METHOD.getName());
    }
}
