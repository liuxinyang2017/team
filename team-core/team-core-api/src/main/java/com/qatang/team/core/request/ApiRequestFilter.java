package com.qatang.team.core.request;

import com.qatang.team.enums.common.OperatorType;

import java.io.Serializable;
import java.util.List;

/**
 * 请求过滤条件
 * Created by sunshow on 5/19/15.
 */
public class ApiRequestFilter implements Serializable {
    private static final long serialVersionUID = 6145292948839762699L;

    private String field;
    private Object value;
    private OperatorType operatorType;
    private List<Object> valueList;

    public ApiRequestFilter(OperatorType operatorType, String field) {
        this.field = field;
        this.operatorType = operatorType;
    }

    public ApiRequestFilter(OperatorType operatorType, String field, Object value) {
        this.field = field;
        this.value = value;
        this.operatorType = operatorType;
    }

    public ApiRequestFilter(OperatorType operatorType, String field, List<Object> valueList) {
        this.field = field;
        this.valueList = valueList;
        this.operatorType = operatorType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public List<Object> getValueList() {
        return valueList;
    }

    public void setValueList(List<Object> valueList) {
        this.valueList = valueList;
    }
}
