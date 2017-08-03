package com.qatang.team.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qatang.team.core.enums.deserializer.CustomValueEnumDeserializer;
import com.qatang.team.core.enums.serializer.CustomValueEnumSerializer;
import com.qatang.team.enums.EnableDisableStatus;
import com.qatang.team.enums.YesNoStatus;
import com.qatang.team.enums.common.OperatorType;
import com.qatang.team.enums.common.PageOrderType;
import com.qatang.team.enums.daemon.DaemonEventStatus;
import com.qatang.team.enums.daemon.DaemonEventType;
import com.qatang.team.enums.fetcher.FetcherType;
import com.qatang.team.enums.fetcher.ProxyFetcherType;
import com.qatang.team.enums.fetcher.ProxyValidateStatus;
import com.qatang.team.enums.fetcher.ProxyValidatorType;
import com.qatang.team.enums.lottery.LotteryType;
import com.qatang.team.enums.lottery.PhaseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qatang
 */
public class CustomObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -3497209367832192953L;

    public CustomObjectMapper() {
        super();
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 注册时间序列化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 注册时间反序列化
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        this.registerModule(javaTimeModule);

        SimpleModule simpleModule = new SimpleModule();
        // 注册自定义enum序列化
        simpleModule.addSerializer(EnableDisableStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(YesNoStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(OperatorType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(PageOrderType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(LotteryType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(PhaseStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(DaemonEventStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(DaemonEventType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(FetcherType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(ProxyFetcherType.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(ProxyValidateStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(ProxyValidatorType.class, new CustomValueEnumSerializer<>());

        // 注册自定义enum反序列化
        simpleModule.addDeserializer(EnableDisableStatus.class, new CustomValueEnumDeserializer<EnableDisableStatus>() {});
        simpleModule.addDeserializer(YesNoStatus.class, new CustomValueEnumDeserializer<YesNoStatus>() {});
        simpleModule.addDeserializer(OperatorType.class, new CustomValueEnumDeserializer<OperatorType>() {});
        simpleModule.addDeserializer(PageOrderType.class, new CustomValueEnumDeserializer<PageOrderType>() {});
        simpleModule.addDeserializer(LotteryType.class, new CustomValueEnumDeserializer<LotteryType>() {});
        simpleModule.addDeserializer(PhaseStatus.class, new CustomValueEnumDeserializer<PhaseStatus>() {});
        simpleModule.addDeserializer(DaemonEventStatus.class, new CustomValueEnumDeserializer<DaemonEventStatus>() {});
        simpleModule.addDeserializer(DaemonEventType.class, new CustomValueEnumDeserializer<DaemonEventType>() {});
        simpleModule.addDeserializer(FetcherType.class, new CustomValueEnumDeserializer<FetcherType>() {});
        simpleModule.addDeserializer(ProxyFetcherType.class, new CustomValueEnumDeserializer<ProxyFetcherType>() {});
        simpleModule.addDeserializer(ProxyValidateStatus.class, new CustomValueEnumDeserializer<ProxyValidateStatus>() {});
        simpleModule.addDeserializer(ProxyValidatorType.class, new CustomValueEnumDeserializer<ProxyValidatorType>() {});

        this.registerModule(simpleModule);
    }
}
