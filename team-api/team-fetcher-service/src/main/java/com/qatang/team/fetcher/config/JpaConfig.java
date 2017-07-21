package com.qatang.team.fetcher.config;

import com.qatang.team.core.config.AbstractJpaConfig;
import com.qatang.team.core.repository.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author qatang
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.qatang.team.fetcher.repository",
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JpaConfig extends AbstractJpaConfig {
    @Override
    protected String entityPackagesToScan() {
        return "com.qatang.team.fetcher.entity";
    }
}
