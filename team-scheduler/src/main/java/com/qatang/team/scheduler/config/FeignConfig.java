package com.qatang.team.scheduler.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qatang.team.core.enums.deserializer.CustomValueEnumDeserializer;
import com.qatang.team.core.enums.serializer.CustomValueEnumSerializer;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.lottery.LotteryType;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qatang
 */
@Configuration
public class FeignConfig {
    @Bean
    public Encoder feignEncoder() {
        return new JacksonEncoder(customObjectMapper());
    }

    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(customObjectMapper());
    }

    public ObjectMapper customObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule simpleModule = new SimpleModule();

        // 注册自定义enum序列化
        simpleModule.addSerializer(EnableDisableStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(YesNoStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(LotteryType.class, new CustomValueEnumSerializer<>());

        // 注册自定义enum反序列化
        simpleModule.addDeserializer(EnableDisableStatus.class, new CustomValueEnumDeserializer<EnableDisableStatus>() {});
        simpleModule.addDeserializer(YesNoStatus.class, new CustomValueEnumDeserializer<YesNoStatus>() {});
        simpleModule.addDeserializer(LotteryType.class, new CustomValueEnumDeserializer<LotteryType>() {});

        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
