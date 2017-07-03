package com.qatang.team.core.annotation.request;

import java.lang.annotation.*;

/**
 * 声明API Bean的属性在用于查询时被自动忽略
 * @author sunshow
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestApiFieldFilterIgnore {
}
