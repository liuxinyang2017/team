package com.qatang.team.fetcher;

import com.qatang.team.fetcher.config.InitConfig;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wp
 * @since 2017/7/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
public class BaseTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
