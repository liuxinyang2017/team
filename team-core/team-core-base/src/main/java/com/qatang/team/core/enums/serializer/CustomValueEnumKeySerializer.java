package com.qatang.team.core.enums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 自定义enum使用value序列化
 * @author sunshow
 */
public class CustomValueEnumKeySerializer<T> extends JsonSerializer<T> {

    protected static final transient Logger logger = LoggerFactory.getLogger(CustomValueEnumKeySerializer.class);
	
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		try {
            gen.writeFieldName(BeanUtils.getProperty(value, "value"));
		} catch (Exception e) {
            logger.error("自定义enum序列化出错, value={}", value);
			logger.error(e.getMessage(), e);
		}
	}
}
