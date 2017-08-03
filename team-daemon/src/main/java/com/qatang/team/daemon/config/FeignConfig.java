package com.qatang.team.daemon.config;

import com.qatang.team.daemon.config.error.SpringWebClientErrorDecoder;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

/**
 * @author qatang
 */
@Configuration
public class FeignConfig {

    @Bean
    public SpringWebClientErrorDecoder springWebClientErrorDecoder() {
        return new SpringWebClientErrorDecoder();
    }

    /**
     * feign get方式请求入参LocalDateTime类型转换
     * @return
     */
    @Bean
    public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
        return formatterRegistry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(formatterRegistry);
        };
    }
}
