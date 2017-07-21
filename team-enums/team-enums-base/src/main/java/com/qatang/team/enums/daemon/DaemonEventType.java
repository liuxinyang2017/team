package com.qatang.team.enums.daemon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 守护事件类型
 * @author qatang
 * @since 2016-02-27 10:12
 */
public enum DaemonEventType {
    ALL(-1, "全部"),
    DEFAULT(0, "默认"),

    PHASE_OPEN(1, "彩期开启"),
    PHASE_SALE_OPEN(2, "彩期开售"),
    PHASE_TERMINAL_OPEN(3, "彩期开始出票"),
    PHASE_SALE_CLOSE(4, "彩期停售"),
    PHASE_TERMINAL_CLOSE(5, "彩期停止出票"),
    PHASE_CLOSE(6, "彩期关闭"),
    PHASE_CURRENT_SWITCH(7, "彩期当前期切换"),
    PHASE_DRAW(8, "彩期开奖"),
    PHASE_PRIZE(9, "彩期派奖"),

    MATCH_OPEN(101, "场次开启"),
    MATCH_SALE_OPEN(102, "场次销售开启"),
    MATCH_TERMINAL_OPEN(103, "场次开始出票"),
    MATCH_SALE_CLOSE(104, "场次销售关闭"),
    MATCH_TERMINAL_CLOSE(105, "场次停止出票"),
    MATCH_CLOSE(106, "场次关闭"),
    ;

    private static Logger logger = LoggerFactory.getLogger(DaemonEventType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, DaemonEventType> _MAP;
    private static List<DaemonEventType> _LIST;
    private static List<DaemonEventType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, DaemonEventType> map = new HashMap<>();
            List<DaemonEventType> list = new ArrayList<>();
            List<DaemonEventType> listAll = new ArrayList<>();
            for (DaemonEventType phaseEventType : DaemonEventType.values()) {
                map.put(phaseEventType.getValue(), phaseEventType);
                listAll.add(phaseEventType);

                if (!phaseEventType.equals(ALL)) {
                    list.add(phaseEventType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    DaemonEventType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static DaemonEventType get(int value){
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<DaemonEventType> list() {
        return _LIST;
    }

    public static List<DaemonEventType> listAll() {
        return _ALL_LIST;
    }
}
