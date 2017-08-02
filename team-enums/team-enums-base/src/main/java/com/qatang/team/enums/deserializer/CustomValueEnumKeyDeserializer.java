package com.qatang.team.enums.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * author: sunshow.
 */
public class CustomValueEnumKeyDeserializer<T> extends KeyDeserializer {
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    protected Class<T> getActualType() {
        ParameterizedType paramType = (ParameterizedType)this.getClass().getGenericSuperclass();

        return  (Class<T>)paramType.getActualTypeArguments()[0];
    }

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Integer value = null;
        try {
            value = Integer.valueOf(key);
        } catch (NumberFormatException e) {
            logger.error("解析自定义enum出错, 无效的值: " + key, e);
        }

        if (value != null) {
            Method getMethod = ReflectionUtils.findMethod(this.getActualType(), "get", int.class);
            if (getMethod == null) {
                logger.error("未找到反序列化方法");
                return null;
            }
            return ReflectionUtils.invokeMethod(getMethod, null, value);
        }

        return null;
    }
}
