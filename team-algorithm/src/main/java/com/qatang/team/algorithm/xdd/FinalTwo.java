package com.qatang.team.algorithm.xdd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 请实现一个函数，把字符串中的每个空格替换成"%20"，例如"We are happy."，则输出"We%20are%20happy"（不许用任何第三方jar）
 * Created by xdd on 2017/3/16.
 */
public class FinalTwo {

    public static String getReplaceStr(String str) {
        //原数组
        char[] ch1 = str.toCharArray();
        int length = str.length();
        int num = 0;//空格数
        int a = 0;
        while (a < length) {
            if (ch1[a] == ' ') {
                num++;
            }
            a++;
        }

        //替换后数组
        char[] ch2 = new char[ch1.length + 2 * num];
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (ch1[i] != ' ') {
                ch2[j++] = ch1[i];
            } else {
                ch2[j++] = '%';
                ch2[j++] = '2';
                ch2[j++] = '0';
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int q = 0; q < ch2.length; q++) {
            sb.append(ch2[q]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("请输入要替换的字符串：");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        try {
            str = stdin.readLine();//读取一行
            System.out.println("替换前字符串：" + str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = getReplaceStr(str);
        System.out.println("替换后字符串：" + response);
    }
}
