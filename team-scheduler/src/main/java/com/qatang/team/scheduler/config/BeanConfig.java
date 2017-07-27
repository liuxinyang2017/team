package com.qatang.team.scheduler.config;

import com.qatang.team.scheduler.error.SpringWebClientErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhiliang
 */
@Configuration
public class BeanConfig {
    @Bean
    public SpringWebClientErrorDecoder springWebClientErrorDecoder() {
        return new SpringWebClientErrorDecoder();
    }
}
