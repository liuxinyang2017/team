package com.qatang.team.enums.lottery;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.qatang.team.exception.StatusFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 彩期状态
 * @author qatang
 */
public enum PhaseStatus {
    ALL(-1, "全部"),
    //    DEFAULT(0, "默认"),
    PENDING(1, "待处理"),
    OPEN_NOT(2, "未开启"),
    OPEN(3, "销售中"),
    CLOSED(4, "已关闭"),
    RESULT_SET(5, "结果已公布"),
    RESULT_DETAIL_SET(6, "详情已公布"),
    DISABLED(7, "不可用"),
    ;

    private static Logger logger = LoggerFactory.getLogger(PhaseStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, PhaseStatus> _MAP;
    private static List<PhaseStatus> _LIST;
    private static List<PhaseStatus> _ALL_LIST;

    private static Map<PhaseStatus, Set<PhaseStatus>> statusFlow;

    static {
        synchronized (_LOCK) {
            Map<Integer, PhaseStatus> map = new HashMap<>();
            List<PhaseStatus> list = new ArrayList<>();
            List<PhaseStatus> listAll = new ArrayList<>();
            for (PhaseStatus item : PhaseStatus.values()) {
                map.put(item.getValue(), item);
                listAll.add(item);
                if (!item.equals(ALL)) {
                    list.add(item);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);

            Map<PhaseStatus, Set<PhaseStatus>> statusFlowMap = Maps.newHashMap();
            statusFlowMap.put(PENDING, ImmutableSet.of(OPEN_NOT, DISABLED));
            statusFlowMap.put(OPEN_NOT, ImmutableSet.of(OPEN, CLOSED, DISABLED));
            statusFlowMap.put(OPEN, ImmutableSet.of(CLOSED, DISABLED));
            statusFlowMap.put(CLOSED, ImmutableSet.of(RESULT_SET, DISABLED));
            statusFlowMap.put(RESULT_SET, ImmutableSet.of(RESULT_DETAIL_SET, DISABLED));
            statusFlow = ImmutableMap.copyOf(statusFlowMap);
        }
    }

    private int value;
    private String name;

    PhaseStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static PhaseStatus get(int value){
        try {
            return _MAP.get(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<PhaseStatus> list() {
        return _LIST;
    }

    public static List<PhaseStatus> listAll() {
        return _ALL_LIST;
    }

    public static void checkStatusFlow(PhaseStatus from, PhaseStatus to) throws StatusFlowException {
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
