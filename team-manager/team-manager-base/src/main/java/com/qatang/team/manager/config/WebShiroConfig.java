package com.qatang.team.manager.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.apache.shiro.mgt.SecurityManager;

/**
 * @author jinsheng
 * @since 2016-04-27 14:36
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class WebShiroConfig {

    @Autowired
    private SecurityManager securityManager;

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
