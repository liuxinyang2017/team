package com.qatang.team.algorithm.jinsheng;

/**
 * @author jinsheng
 */
public class ReplaceTest {
    public static String replaceBlank(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        int newLength = length;

        byte blank = " ".getBytes()[0];
        byte[] replaceBytes = "%20".getBytes();
        byte percent = replaceBytes[0];
        byte two = replaceBytes[1];
        byte zero = replaceBytes[2];

        for (byte b : bytes) {
            if (b == blank) {
                newLength = newLength + 2;
            }
        }

        byte[] newBytes = new byte[newLength];

        for (int i = bytes.length - 1, j = newBytes.length - 1; i >= 0;i --) {
            if (bytes[i] == blank) {
                newBytes[j--] = zero;
                newBytes[j--] = two;
                newBytes[j--] = percent;
                continue;
            }
            newBytes[j--] = bytes[i];
        }

        return new String(newBytes);
    }
}
