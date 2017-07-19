package com.qatang.team.core.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qatang
 */
public class CoreCollectionUtils {

    /**
     * 按照list中的属性值去重
     * @param list 数据列表
     * @param keyExtractor 属性值
     * @param <T> 实际对象类型
     * @return 去重后的列表
     */
    public static <T> List<T> distinctByKey(List<T> list, Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return list.stream().filter(t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null).collect(Collectors.toList());
    }
}
