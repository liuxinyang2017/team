package com.qatang.team.gateway.exception;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则校验异常类型
 * @author wangzhiliang
 */
public enum GatewayExceptionType {
    SUCCESS(0, "success"),

    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    INVALID_SIGNATURE(40001, "不合法的签名"),
    INVALID_APPID(40002, "不合法的AppID"),
    INVALID_REQUEST_HEADER(40003, "不合法的请求头"),

    PARAM_MISSING(41001, "缺少参数"),

    REQUIRE_POST_METHOD(43001, "需要使用POST请求"),

    MODULE_UNAUTHORIZED(50001, "服务未授权"),
    ;

    private static Logger logger = LoggerFactory.getLogger(GatewayExceptionType.class);

    private static final Object _LOCK = new Object();

    private static Map<String, GatewayExceptionType> _NAME_MAP;
    private static Map<Integer, GatewayExceptionType> _MAP;
    private static List<GatewayExceptionType> _LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, GatewayExceptionType> map = new HashMap<>();
            Map<String, GatewayExceptionType> nameMap = new HashMap<>();
            List<GatewayExceptionType> list = new ArrayList<>();
            for (GatewayExceptionType type : GatewayExceptionType.values()) {
                map.put(type.getValue(), type);
                nameMap.put(type.getName(), type);
                list.add(type);
            }

            _MAP = ImmutableMap.copyOf(map);
            _NAME_MAP = ImmutableMap.copyOf(nameMap);
            _LIST = ImmutableList.copyOf(list);
        }
    }

    private int value;
    private String name;


    GatewayExceptionType(int value, String name){
        this.value = value;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }



    public static GatewayExceptionType get(int value) {
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static GatewayExceptionType get(String name) {
        try {
            return _NAME_MAP.get(name);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<GatewayExceptionType> list() {
        return _LIST;
    }
}
