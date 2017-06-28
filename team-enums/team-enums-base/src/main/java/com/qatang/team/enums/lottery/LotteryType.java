package com.qatang.team.enums.lottery;

import java.util.*;

/**
 * 彩种编码
 * @author qatang
 */
public enum LotteryType {
    ALL(-1, "全部"),
    JC_ZQ(1, "竞彩足球"),
    JC_LQ(2, "竞彩篮球"),
    ZC_SPF14(3, "传足14场"),
    ZC_SPF9(4, "传足9场"),
    ZC_JQS4(5, "传足4场进球"),
    ZC_BQC6(6, "传足6场半全场"),
    TC_BJDC(7, "北京单场"),
    JC_GJ(8, "竞彩猜冠军"),
    JC_GYJ(9, "竞彩猜冠亚军"),
    TC_BJDC_SFGG(10, "胜负过关"),

    TC_DLT(21, "超级大乐透"),

    FC_SSQ(51, "双色球"),

    DEFAULT(0, "Default"),
    ;

    private static final Object _LOCK = new Object();

    private static Map<Integer, LotteryType> _MAP;
    private static List<LotteryType> _LIST;
    private static List<LotteryType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, LotteryType> map = new HashMap<>();
            List<LotteryType> list = new ArrayList<>();
            List<LotteryType> listAll = new ArrayList<>();
            for (LotteryType values : LotteryType.values()) {
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

    LotteryType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static LotteryType get(int value) {
        return _MAP.get(value);
    }

    public static List<LotteryType> list() {
        return _LIST;
    }

    public static List<LotteryType> listAll() {
        return _ALL_LIST;
    }
}
