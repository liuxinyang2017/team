package com.qatang.team.enums.fetcher;

import java.util.*;

/**
 * 代理验证器类型
 * @author qatang
 */
public enum ProxyValidatorType {
    ALL(-1, "全部"),
    V_BAIDU(1, "百度"),
    V_FUCAI(2, "福彩官网"),
    ;

    private static final Object _LOCK = new Object();

    private static Map<Integer, ProxyValidatorType> _MAP;
    private static List<ProxyValidatorType> _LIST;
    private static List<ProxyValidatorType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, ProxyValidatorType> map = new HashMap<>();
            List<ProxyValidatorType> list = new ArrayList<>();
            List<ProxyValidatorType> listAll = new ArrayList<>();
            for (ProxyValidatorType values : ProxyValidatorType.values()) {
                map.put(values.getValue(), values);
                listAll.add(values);
                if (!values.equals(ALL)) {
                    list.add(values);
                }
            }

            _MAP = Collections.unmodifiableMap(map);
            _LIST = Collections.unmodifiableList(list);
            _ALL_LIST = Collections.unmodifiableList(listAll);
        }
    }

    private int value;
    private String name;

    ProxyValidatorType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ProxyValidatorType get(int value) {
        return _MAP.get(value);
    }

    public static List<ProxyValidatorType> list() {
        return _LIST;
    }

    public static List<ProxyValidatorType> listAll() {
        return _ALL_LIST;
    }
}
