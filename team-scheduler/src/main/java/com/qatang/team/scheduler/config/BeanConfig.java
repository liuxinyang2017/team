package com.qatang.team.scheduler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qatang.team.json.CustomObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wangzhiliang
 */
@Configuration
public class BeanConfig {
    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        return new CustomObjectMapper();
    }

}
