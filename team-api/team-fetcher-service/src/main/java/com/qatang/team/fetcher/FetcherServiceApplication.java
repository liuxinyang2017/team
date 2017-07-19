package com.qatang.team.fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wangzhiliang
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FetcherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetcherServiceApplication.class, args);
    }

}
