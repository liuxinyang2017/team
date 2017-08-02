package com.qatang.team.web.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangzhiliang
 */
public class TestCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommnadKey");

    protected TestCommand(RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForEntity("http://TEAM-DATA-SERVICE/service/test", String.class).getBody();
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }
}
