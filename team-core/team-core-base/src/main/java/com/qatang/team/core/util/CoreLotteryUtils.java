package com.qatang.team.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author qatang
 */
public class CoreLotteryUtils {
    /**
     * 将双色球开奖号码排序转换成固定格式
     * @param red 红球
     * @param blue 篮球
     * @return 01,02,03,04,05,06|07
     */
    public static String formatSsq(String[] red, String blue) {
        Arrays.sort(red);
        return StringUtils.join(red, ",").concat("|").concat(blue);
    }
}
