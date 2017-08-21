package com.qatang.team.manager.core.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jinsheng
 * @since 2016-05-13 13:51
 */
public abstract class AbstractValidator<T> implements IValidator<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
