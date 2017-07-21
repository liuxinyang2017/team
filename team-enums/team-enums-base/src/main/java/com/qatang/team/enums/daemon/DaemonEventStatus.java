package com.qatang.team.enums.daemon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.qatang.team.exception.StatusFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 赛程事件状态
 * @author qatang
 * @since 2016-03-17 15:54
 */
public enum DaemonEventStatus {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    PENDING(1, "未执行"),
    SENDING(2, "已派发未执行"),
    EXECUTING(3, "正在执行"),
    EXECUTED(4, "已执行"),
    OBSOLETED(5, "已作废"),
    TIMEOUT(6, "已超时"),
    ;

    private static Logger logger = LoggerFactory.getLogger(DaemonEventStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, DaemonEventStatus> _MAP;
    private static List<DaemonEventStatus> _LIST;
    private static List<DaemonEventStatus> _ALL_LIST;

    private static Map<DaemonEventStatus, Set<DaemonEventStatus>> statusFlow;

    static {
        synchronized (_LOCK) {
            Map<Integer, DaemonEventStatus> map = new HashMap<>();
            List<DaemonEventStatus> list = new ArrayList<>();
            List<DaemonEventStatus> listAll = new ArrayList<>();
            for (DaemonEventStatus matchEventStatus : DaemonEventStatus.values()) {
                map.put(matchEventStatus.getValue(), matchEventStatus);
                listAll.add(matchEventStatus);
                if (!matchEventStatus.equals(ALL)) {
                    list.add(matchEventStatus);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }

        statusFlow = ImmutableMap.of(
                PENDING, ImmutableSet.of(SENDING, EXECUTING, OBSOLETED, TIMEOUT),
                SENDING, ImmutableSet.of(PENDING, EXECUTING, OBSOLETED, TIMEOUT),
                EXECUTING, ImmutableSet.of(PENDING, SENDING, EXECUTED, OBSOLETED)
        );
    }

    private int value;
    private String name;

    DaemonEventStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static DaemonEventStatus get(int value){
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<DaemonEventStatus> list() {
        return _LIST;
    }

    public static List<DaemonEventStatus> listAll() {
        return _ALL_LIST;
    }

    public static void checkStatusFlow(DaemonEventStatus from, DaemonEventStatus to) throws StatusFlowException {
        try {
            if (!statusFlow.get(from).contains(to)) {
                throw new StatusFlowException(String.format("不允许的状态变更, 原始状态: %s, 目标状态: %s", from, to));
            }
        } catch (StatusFlowException e) {
            logger.error("不允许的状态变更, 原始状态: {}, 目标状态: {}", from, to);
            throw e;
        } catch (Exception e) {
            logger.error("不允许的状态变更, 原始状态: {}, 目标状态: {}", from, to);
            throw new StatusFlowException(e);
        }
    }
}
