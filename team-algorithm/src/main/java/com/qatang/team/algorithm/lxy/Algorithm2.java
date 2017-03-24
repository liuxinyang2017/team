package com.qatang.team.algorithm.lxy;

/**
 * Created by liuxinyang on 2017/3/21.
 * 2. 请实现一个函数，把字符串中的每个空格替换成"%20"，例如“We are happy.“，则输出”We%20are%20happy.“（不许用任何第三方jar）
 */
public class Algorithm2 {

    public static void main(String[] args) {
        String str = "We are happy.";
        System.out.println(replaceBlank(str));
    }

    private static String replaceBlank(String str) {
        char[] chars = str.toCharArray();
        int length = chars.length;
        int blankCount = 0;
        for (int i = 0; i < length; i++) {
            if (chars[i] == ' ') {
                blankCount++;
            }
        }

        int newLength = length + blankCount * 2;
        if (length == newLength) {
            return str;
        }

        int index = length - 1;
        int newIndex = newLength - 1;
        char[] newChars = new char[newLength];
        while (index >= 0) {
            if (chars[index] == ' ') {
                newChars[newIndex--] = '0';
                newChars[newIndex--] = '2';
                newChars[newIndex--] = '%';
            } else {
                newChars[newIndex--] = chars[index];
            }
            index--;
        }
        return String.valueOf(newChars);
    }
}
