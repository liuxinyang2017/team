package com.qatang.team.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import static com.qatang.team.manager.core.constants.GlobalConstants.*;

/**
 * @author jinsheng
 * @since 2016-04-29 11:45
 */
@EnableRedisHttpSession(redisNamespace="zy:arsenal", maxInactiveIntervalInSeconds = MAX_INACTIVE_INTERVAL_IN_SECONDS)
public class HttpSessionConfig {
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(CUSTOM_COOKIE_NAME);
        serializer.setCookiePath(CUSTOM_COOKIE_PATH);
        serializer.setDomainNamePattern(DOMAIN_NAME_PATTERN);
        return serializer;
    }
}
