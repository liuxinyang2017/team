package com.qatang.team.core.exception;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author wangzhiliang
 */
public class ClientExceptionRegistry {
    private static final ClientExceptionRegistry INSTANCE = new ClientExceptionRegistry();

    public static ClientExceptionRegistry instance() {
        return INSTANCE;
    }

    private final Table<String, String, Class<? extends ClientException>> exceptions = HashBasedTable.create();

    private ClientExceptionRegistry() {

    }

    public Class<? extends ClientException> remove(String module, String code) {
        return exceptions.remove(module, code);
    }

    public Class<? extends ClientException> get(String module, String code) {
        return exceptions.get(module, code);
    }

    public void put(String module, Class<? extends ClientException> clazz) {
        Assert.notNull(clazz, "ClientException类为空");

        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName() + "未实现公有的默认构造方法");
        }

        Method method = ReflectionUtils.findMethod(clazz, "getErrorCode");
        Assert.notNull(method, "ClientException类未实现getErrorCode方法");
        String errorCode = (String) ReflectionUtils.invokeMethod(method, instance);

        Assert.hasText(errorCode, clazz.getName() + "异常码不能为空");
        Assert.isTrue(!this.exceptions.contains(module, errorCode), "配置异常编码冲突:" + errorCode);
        this.exceptions.put(module, errorCode, clazz);
    }
}
