package com.qatang.team.data.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.qatang.team.core.enums.deserializer.CustomValueEnumDeserializer;
import com.qatang.team.core.enums.serializer.CustomValueEnumSerializer;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.converter.EnableDisableStatusConverter;
import com.qatang.team.enums.converter.YesNoStatusConverter;
import com.qatang.team.enums.converter.lottery.LotteryTypeConverter;
import com.qatang.team.enums.lottery.LotteryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


/**
 * web应用配置类
 * 由框架扫描装载，取代web.xml,applicationContext.xml
 * servlet、filter映射顺序同声明顺序
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    /********************************************************
     * 设置jackson转换器
     ********************************************************/
    protected MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(customObjectMapper());
        return jsonConverter;
    }

    public ObjectMapper customObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);

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

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(new EnableDisableStatusConverter());
        registry.addConverter(new YesNoStatusConverter());
        registry.addConverter(new LotteryTypeConverter());
    }
}
