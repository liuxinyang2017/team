package com.qatang.team.manager.core.form;

/**
 * @author jinsheng
 * @since 2016-04-28 14:31
 */
public abstract class AbstractForm implements IForm {

    private static final String REGEX_WHOLE_NUMBER_STRING = "^\\d+(,\\d+)*$"; // 逗号隔开的正整数字符串
    private static final String REGEX_PHONE_NUMBER = "^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"; // 手机号
    private static final String REGEX_TEL_NUMBER = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$"; // 固定电话
    private static final String REGEX_DATE_AT = "^(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1})$"; // 时间（00:00）
    private static final String REGEX_DATE_RANGE = "^(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})-(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})$"; // 时段（00:00:00-23:59:59）
    private static final String REGEX_DATE_RANGE_SPLIT_BY_COMMA = "^((0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})-(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1}))(,(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1})-(0\\d{1}|1\\d{1}|2[0-3]):([0-5]\\d{1}):([0-5]\\d{1}))*$"; // 时段按逗号分隔（00:00:00-23:59:59,00:00:00-23:59:59）
    private static final String REGEX_COMMA_STRING = "^(\\w|[\\u4e00-\\u9fa5])+(,(\\w|[\\u4e00-\\u9fa5])+)*$"; // 以逗号分隔的字符串(不支持中文逗号)
    private static final String REGEX_WHOLE_NUMBER = "^[1-9][0-9]*$"; // 正整数
    private static final String REGEX_INTEGER = "^[0-9]*$"; // 整数
    private static final String REGEX_COORDINATES = "^[0-9]+\\.[0-9]*$"; // 经纬度
    private static final String REGEX_PERCENTAGE = "^[0-9]{1,2}(.[0-9]{1,2})?$"; // 百分数
    private static final String REGEX_MONEY = "^(\\d+|[1-9])(.\\d{0,2})?$"; // 金额(小数点后两位)

    public String getRegexWholeNumberString() {
        return REGEX_WHOLE_NUMBER_STRING;
    }

    public String getRegexPhoneNumber() {
        return REGEX_PHONE_NUMBER;
    }

    public String getRegexTelNumber() {
        return REGEX_TEL_NUMBER;
    }

    public String getRegexDateAt() {
        return REGEX_DATE_AT;
    }

    public String getRegexDateRange() {
        return REGEX_DATE_RANGE;
    }

    public String getRegexDateRangeSplitByComma() {
        return REGEX_DATE_RANGE_SPLIT_BY_COMMA;
    }

    public String getRegexCommaString() {
        return REGEX_COMMA_STRING;
    }

    public String getRegexWholeNumber() {
        return REGEX_WHOLE_NUMBER;
    }

    public String getRegexInteger() {
        return REGEX_INTEGER;
    }

    public String getRegexCoordinates() {
        return REGEX_COORDINATES;
    }

    public String getRegexPercentage() {
        return REGEX_PERCENTAGE;
    }

    public String getRegexMoney() {
        return REGEX_MONEY;
    }
}
