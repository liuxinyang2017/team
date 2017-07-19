package com.qatang.team.fetcher.exception;

import com.qatang.team.core.exception.ClientExceptionRegistry;
import com.zhangyu.saas.core.api.annotation.exception.ExceptionLoader;
import com.zhangyu.saas.core.api.exception.SaasClientException;
import com.zhangyu.saas.core.api.exception.SaasClientExceptionRegistry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * Created by wangzhiliang on 2016/12/14.
 */
@Component
@ExceptionLoader(value = "com.qatang.team.fetcher.exception")
public class FetcherClientExceptionLoader {

    private transient final Logger logger = LoggerFactory.getLogger(getClass());

    private ClientExceptionRegistry clientExceptionRegistry = ClientExceptionRegistry.instance();

    public static final String MODULE = "FETCHER_CLIENT";

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void load() {
        String basePackage = getClass().getAnnotation(ExceptionLoader.class).value();
        if (StringUtils.isBlank(basePackage)) {
            throw new RuntimeException("ExceptionLoader未配置扫描包路径");
        }

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(SaasClientException.class));
        Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(basePackage);

        try {
            for (BeanDefinition beanDefinition : beanDefinitionSet) {
                String className = beanDefinition.getBeanClassName();
                Class<SaasClientException> clazz = (Class<SaasClientException>) Class.forName(className);
                saasClientExceptionRegistry.put(MODULE, clazz);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("加载SaasClientException出错");
        }
    }
}
