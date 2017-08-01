package com.qatang.team.scheduler.config;

import com.qatang.team.scheduler.error.SpringWebClientErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qatang
 */
@Configuration
public class FeignConfig {

    @Bean
    public SpringWebClientErrorDecoder springWebClientErrorDecoder() {
        return new SpringWebClientErrorDecoder();
    }
}
