package com.qatang.team.enums.fetcher;

import java.util.*;

/**
 * 抓取数据来源类型
 * @author qatang
 */
public enum FetcherType {
    ALL(-1, "全部"),
    F_OFFICIAL(1, "官方"),
    F_500W(2, "500万"),
    F_AICAI(3, "爱彩"),
    F_OKOOO(4, "澳客"),
    ;

    private static final Object _LOCK = new Object();

    private static Map<Integer, FetcherType> _MAP;
    private static List<FetcherType> _LIST;
    private static List<FetcherType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, FetcherType> map = new HashMap<>();
            List<FetcherType> list = new ArrayList<>();
            List<FetcherType> listAll = new ArrayList<>();
            for (FetcherType values : FetcherType.values()) {
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

    FetcherType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static FetcherType get(int value) {
        return _MAP.get(value);
    }

    public static List<FetcherType> list() {
        return _LIST;
    }

    public static List<FetcherType> listAll() {
        return _ALL_LIST;
    }
}
