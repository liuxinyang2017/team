package com.qatang.team.scheduler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qatang.team.json.CustomObjectMapper;
import com.qatang.team.scheduler.error.SpringWebClientErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhiliang
 */
@Configuration
public class BeanConfig {
    @Bean
    public ObjectMapper customObjectMapper() {
        return new CustomObjectMapper();
    }

}
