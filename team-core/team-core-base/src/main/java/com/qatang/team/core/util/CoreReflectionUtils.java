package com.qatang.team.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具
 * Created by sunshow on 5/21/15.
 */
public class CoreReflectionUtils {

    /**
     * 获取一个类中包含指定注解类型的字段集合
     */
    public static <E, T extends Annotation> Map<Field, T> getFieldsAnnotatedWith(Class<E> clazz, Class<T> annotationType) {
        Field[] fields = clazz.getDeclaredFields();
        Map<Field, T> fieldAnnotationMap = new HashMap<>();
        for (Field field : fields) {
            T annotation = field.getDeclaredAnnotation(annotationType);
            if (annotation != null) {
                fieldAnnotationMap.put(field, annotation);
            }
        }
        return fieldAnnotationMap;
    }
}
