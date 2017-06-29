package com.qatang.team.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qatang
 */
public abstract class AbstractJpaConfig {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.maxActive}")
    private String maxActive;

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.query.substitutions}")
    private String querySubstitutions;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.connection.autocommit}")
    private String connectionAutocommit;

    @Value("${hibernate.jdbc.batch_size}")
    private String batchSize;

    @Value("${hibernate.id.new_generator_mappings}")
    private String idNewGeneratorMappings = "true";

    abstract protected String entityPackagesToScan();

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.query.substitutions", querySubstitutions);

        if (StringUtils.containsAny(hbm2ddl, "validate", "update", "create", "create-drop")) {
            properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
        }

        properties.setProperty("hibernate.connection.autocommit", connectionAutocommit);
        properties.setProperty("hibernate.jdbc.batch_size", batchSize);
        properties.setProperty("hibernate.id.new_generator_mappings", idNewGeneratorMappings);
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(entityPackagesToScan());

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @PreDestroy
    public void preDestroy() {
        dataSource().close();
    }

    @PostConstruct
    public void postConstruct() {
        try {
            dataSource().init();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Bean
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(Integer.parseInt(maxActive));
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        EntityManagerFactory factory = entityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
