package com.qatang.team.manager.core.exception;

/**
 * @author jinsheng
 * @since 2016-05-13 13:49
 */
public class ValidateFailedException extends Exception {

    private static final long serialVersionUID = 3678368103525573209L;
    private static final String MSG = "参数验证失败异常";

    private String field;

    public ValidateFailedException() {
        super(MSG);
    }

    public ValidateFailedException(String field, String message) {
        super(MSG + "：" + message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
