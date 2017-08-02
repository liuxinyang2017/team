package com.qatang.team.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wangzhiliang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class TeamWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamWebApplication.class, args);
    }

}
