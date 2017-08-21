package com.qatang.team.enums.resouce;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public enum ResourceType {
    ALL(-1, "全部"),
    MENU(1, "菜单"),
    FUNCTION(2, "功能");

    private static Logger logger = LoggerFactory.getLogger(ResourceType.class);

    private static final Object _LOCK = new Object();

    private static Map<Integer, ResourceType> _MAP;
    private static List<ResourceType> _LIST;
    private static List<ResourceType> _ALL_LIST;

    static {
        synchronized (_LOCK) {
            Map<Integer, ResourceType> map = new HashMap<>();
            List<ResourceType> list = new ArrayList<>();
            List<ResourceType> listAll = new ArrayList<>();
            for (ResourceType resourceType : ResourceType.values()) {
                map.put(resourceType.getValue(), resourceType);
                listAll.add(resourceType);
                if (!resourceType.equals(ALL)) {
                    list.add(resourceType);
                }
            }

            _MAP = ImmutableMap.copyOf(map);
            _LIST = ImmutableList.copyOf(list);
            _ALL_LIST = ImmutableList.copyOf(listAll);
        }
    }

    private int value;
    private String name;

    ResourceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static ResourceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<ResourceType> list() {
        return _LIST;
    }

    public static List<ResourceType> listAll() {
        return _ALL_LIST;
    }
}
