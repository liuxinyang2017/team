package com.qatang.team.algorithm.wzl;

import java.util.HashMap;
import java.util.Map;

/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数
 * Created by wangzhiliang on 2017/3/10.
 */
public class MatrixSearch {
    public static void main(String[] args) {
        int[][] twoArray = {{1,4,6},{2,5,8},{3,6,9}};
        int target = 6;
        Map<String, Integer> result = twoArraySearch(twoArray, target);
        if (result.containsKey("row")) {
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                System.out.println(entry.getKey() + ":" +entry.getValue());
            }
        } else {
            System.out.println("without find " + target);
        }
    }

    private static Map<String, Integer> twoArraySearch(int[][] twoArray, int target) {
        int row = twoArray.length;
        int col = twoArray[0].length;

        int i = row - 1;
        int j = 0;
        int step = 0;
        Map<String, Integer> result = new HashMap<>();
        while (i > 0 && j < col) {
            ++step;
            int current = twoArray[i][j];
            if (current == target) {
                result.put("row",i + 1);
                result.put("col",j + 1);
                break;
            }
            if (current < target) {
                j++;
            }

            if (current > target) {
                i--;
            }
        }
        result.put("step", step);
        return result;
    }
}
