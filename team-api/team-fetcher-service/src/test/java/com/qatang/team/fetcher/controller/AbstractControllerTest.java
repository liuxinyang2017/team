package com.qatang.team.fetcher.controller;

import com.qatang.team.fetcher.BaseTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author wp
 * @since 2017/7/26
 */
@EnableFeignClients({
        "com.qatang.team.fetcher"
})
public class AbstractControllerTest extends BaseTest {
}
