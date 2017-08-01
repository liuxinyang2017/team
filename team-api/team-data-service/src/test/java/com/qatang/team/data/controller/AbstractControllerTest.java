package com.qatang.team.data.controller;

import com.qatang.team.data.BaseTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author jinsheng
 */
@EnableFeignClients({
        "com.qatang.team"
})
public class AbstractControllerTest extends BaseTest {
}
