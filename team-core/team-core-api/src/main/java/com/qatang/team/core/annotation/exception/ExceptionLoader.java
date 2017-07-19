package com.qatang.team.core.annotation.exception;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * 异常加载
 * @author wangzhiliang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionLoader {

    /**
     * 要扫描exception定义的包路径
     * @return
     */
    String value() default StringUtils.EMPTY;
}
