package com.qatang.team.core.annotation.request;

import java.lang.annotation.*;

/**
 * 声明API Bean的属性可用于更新
 * @author sunshow
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestApiFieldUpdatable {
}
