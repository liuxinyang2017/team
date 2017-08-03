package com.qatang.team.enums.fetcher;

import java.util.*;

/**
 * 抓取数据类型
 * @author qatang
 */
public enum FetcherDataType {
    ALL(-1, "全部"),
    D_RESULT(1, "开奖结果"),
    D_DETAIL(2, "开奖详情"),
    D_MATCH(3, "赛程"),
    D_MATCH_RESULT(4, "赛果"),
    D_MATCH_SP(5, "SP"),
    ;

    private static final Object _LOCK = new Object();

    private static Map<Integer, FetcherDataType> _MAP;
    private static List<FetcherDataType> _LIST;
    private static List<FetcherDataType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, FetcherDataType> map = new HashMap<>();
            List<FetcherDataType> list = new ArrayList<>();
            List<FetcherDataType> listAll = new ArrayList<>();
            for (FetcherDataType values : FetcherDataType.values()) {
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

    FetcherDataType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static FetcherDataType get(int value) {
        return _MAP.get(value);
    }

    public static List<FetcherDataType> list() {
        return _LIST;
    }

    public static List<FetcherDataType> listAll() {
        return _ALL_LIST;
    }
}
