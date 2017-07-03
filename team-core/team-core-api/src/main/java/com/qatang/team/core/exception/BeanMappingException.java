package com.qatang.team.core.exception;

/**
 * Bean对象映射异常
 * Created by sunshow on 5/7/15.
 */
public class BeanMappingException extends RuntimeException {

    private static final long serialVersionUID = -3616240376550557648L;

    public BeanMappingException() {
        this("Bean对象映射异常");
    }

    public BeanMappingException(String message) {
        super(message);
    }

    public BeanMappingException(Throwable cause) {
        super(cause);
    }

    public BeanMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
