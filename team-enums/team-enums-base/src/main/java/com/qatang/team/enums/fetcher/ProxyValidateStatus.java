package com.qatang.team.enums.fetcher;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.qatang.team.exception.StatusFlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 代理验证状态
 * @author qatang
 */
public enum ProxyValidateStatus {
    ALL(-1, "全部"),
    INIT(1, "待处理"),
    WAITING_TEST(2, "待测试"),
    TESTING(3, "测试中"),
    TESTED(4, "已通过"),
    FAILED(5, "测试失败"),
    OBSOLETED(6, "已作废"),
    ;

    private static Logger logger = LoggerFactory.getLogger(ProxyValidateStatus.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, ProxyValidateStatus> _MAP;
    private static List<ProxyValidateStatus> _LIST;
    private static List<ProxyValidateStatus> _ALL_LIST;

    private static Map<ProxyValidateStatus, Set<ProxyValidateStatus>> statusFlow;

    static {
        synchronized (_LOCK) {
            Map<Integer, ProxyValidateStatus> map = new HashMap<>();
            List<ProxyValidateStatus> list = new ArrayList<>();
            List<ProxyValidateStatus> listAll = new ArrayList<>();
            for (ProxyValidateStatus values : ProxyValidateStatus.values()) {
                map.put(values.getValue(), values);
                listAll.add(values);
                if (!values.equals(ALL)) {
                    list.add(values);
                }
            }

            _MAP = Collections.unmodifiableMap(map);
            _LIST = Collections.unmodifiableList(list);
            _ALL_LIST = Collections.unmodifiableList(listAll);

            statusFlow = Maps.newHashMap();
            statusFlow.put(INIT, ImmutableSet.of(WAITING_TEST, OBSOLETED));
            statusFlow.put(WAITING_TEST, ImmutableSet.of(TESTING, OBSOLETED));
            statusFlow.put(TESTING, ImmutableSet.of(TESTED, FAILED, OBSOLETED));
            statusFlow.put(TESTED, ImmutableSet.of(WAITING_TEST, OBSOLETED));
            statusFlow.put(FAILED, ImmutableSet.of(WAITING_TEST, OBSOLETED));
        }
    }

    private int value;
    private String name;

    ProxyValidateStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static ProxyValidateStatus get(int value) {
        return _MAP.get(value);
    }

    public static List<ProxyValidateStatus> list() {
        return _LIST;
    }

    public static List<ProxyValidateStatus> listAll() {
        return _ALL_LIST;
    }

    public static void checkStatusFlow(ProxyValidateStatus from, ProxyValidateStatus to) throws StatusFlowException {
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
