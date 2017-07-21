package com.qatang.team.generator.phase.exception;

/**
 * 彩期生成器异常
 * Created by sunshow on 5/7/15.
 */
public class GeneratorException extends RuntimeException {

    private static final long serialVersionUID = -8361622555519471271L;

    public GeneratorException() {
        this("彩期生成器异常");
    }

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }
}
