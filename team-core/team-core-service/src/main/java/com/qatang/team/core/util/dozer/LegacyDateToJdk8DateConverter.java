package com.qatang.team.core.util.dozer;

import com.qatang.team.core.util.CoreDateUtils;
import org.dozer.CustomConverter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by cld on 2016/12/15.
 */
public class LegacyDateToJdk8DateConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {

        if (destinationClass == null || sourceClass == null) {
            return destination;
        }

        if (source == null) {
            destination = null;
        }
        else if (destinationClass.isAssignableFrom(LocalDateTime.class) && sourceClass.isAssignableFrom(Date.class)) {
            destination = CoreDateUtils.convertFrom((Date) source);
        }
        else if (destinationClass.isAssignableFrom(Date.class) && sourceClass.isAssignableFrom(LocalDateTime.class)) {
            destination = CoreDateUtils.convertTo((LocalDateTime) source);
        }

        return destination;
    }
}
