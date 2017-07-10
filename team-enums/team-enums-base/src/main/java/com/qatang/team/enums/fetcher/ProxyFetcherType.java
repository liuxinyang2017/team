package com.qatang.team.enums.fetcher;

import java.util.*;

/**
 * 代理抓取数据来源类型
 * @author qatang
 */
public enum ProxyFetcherType {
    ALL(-1, "全部"),
    P_XICI(1, "西刺代理"),
    P_KUAI(2, "快代理"),
    P_CZ88(3, "纯真代理"),
    P_MIMVP(4, "米扑代理"),
    ;

    private static final Object _LOCK = new Object();

    private static Map<Integer, ProxyFetcherType> _MAP;
    private static List<ProxyFetcherType> _LIST;
    private static List<ProxyFetcherType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, ProxyFetcherType> map = new HashMap<>();
            List<ProxyFetcherType> list = new ArrayList<>();
            List<ProxyFetcherType> listAll = new ArrayList<>();
            for (ProxyFetcherType values : ProxyFetcherType.values()) {
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

    ProxyFetcherType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ProxyFetcherType get(int value) {
        return _MAP.get(value);
    }

    public static List<ProxyFetcherType> list() {
        return _LIST;
    }

    public static List<ProxyFetcherType> listAll() {
        return _ALL_LIST;
    }
}
