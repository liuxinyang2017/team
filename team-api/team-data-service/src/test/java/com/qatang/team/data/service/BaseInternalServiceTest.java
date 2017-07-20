package com.qatang.team.data.service;

import com.qatang.team.data.InitTestConfig;
import com.qatang.team.data.config.JpaConfig;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author jinsheng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
@Import({InitTestConfig.class, JpaConfig.class})
public class BaseInternalServiceTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
