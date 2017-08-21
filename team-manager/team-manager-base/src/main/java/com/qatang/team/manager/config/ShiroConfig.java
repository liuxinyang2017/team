package com.qatang.team.manager.config;


import com.qatang.team.manager.shiro.authentication.RetryLimitHashedCredentialsMatcher;
import com.qatang.team.manager.shiro.realm.UserRealm;
import com.qatang.team.manager.shiro.session.RedisSessionManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author jinsheng
 * @since 2016-04-27 14:45
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private Environment env;

    @Bean
    public CacheManager cacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager());
        credentialsMatcher.setHashAlgorithmName(env.getProperty("password.algorithmName"));
        credentialsMatcher.setHashIterations(Integer.valueOf(env.getProperty("password.hashIterations")));
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean
    public Realm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        userRealm.setCachingEnabled(false);
        return userRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setSessionManager(new RedisSessionManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/signin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        StringBuilder sb = new StringBuilder();
        sb.append("/api/** = anon").append("\n");
        sb.append("/static/** = anon").append("\n");
        sb.append("/plugins/** = anon").append("\n");
        sb.append("/favicon.ico = anon").append("\n");
        sb.append("/signin = anon").append("\n");
        sb.append("/user/password/forget = anon").append("\n");
        sb.append("/signout = logout").append("\n");
        sb.append("/dashboard = user").append("\n");
        sb.append("/** = user");
        shiroFilterFactoryBean.setFilterChainDefinitions(sb.toString());
        return shiroFilterFactoryBean;
    }

    @PostConstruct
    public void postConstruct() {
        SecurityUtils.setSecurityManager(securityManager());
    }
}
