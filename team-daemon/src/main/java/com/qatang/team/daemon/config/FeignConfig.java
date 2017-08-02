package com.qatang.team.daemon.config;

import com.qatang.team.daemon.config.error.SpringWebClientErrorDecoder;
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
