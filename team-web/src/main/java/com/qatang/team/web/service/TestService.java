package com.qatang.team.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangzhiliang
 */
@Service
public class TestService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "testFallback")
    public String testService() {
        return restTemplate.getForEntity("http://TEAM-DATA-SERVICE/service/test", String.class).getBody();
    }

    public String testFallback() {
        return "fallback";
    }
}
