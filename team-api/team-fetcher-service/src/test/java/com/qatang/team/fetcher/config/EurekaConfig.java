package com.qatang.team.fetcher.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangzhiliang
 */
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
public class EurekaConfig {
}
