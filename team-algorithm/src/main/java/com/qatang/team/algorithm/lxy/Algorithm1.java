package com.qatang.team.algorithm.lxy;

/**
 * Created by liuxinyang on 2017/3/21.
 * 1. 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，
 *    输入这样的一个二维数组和一个整数，判断数组中是否含有该整数
 */
public class Algorithm1 {

    public static void main(String[] args) {
        int[ ][ ] intArray={{1,5},{2,7},{3,8,9}};
		int x = 5;
		boolean flag = find(intArray, x);
		System.out.println(flag);
    }

    public static boolean find (int[][] arr ,int x) {
        int rowCount = arr.length;
        int colCount = arr[0].length;
        int i,j;
        for (i = rowCount-1,j = 0; i >= 0 && j < colCount;) {
            if (x == arr[i][j]) {
                return true;
            }
            if (x<arr[i][j]) {
                i--;
                continue;
            }
            if (x>arr[i][j]) {
                j++;
                continue;
            }
        }
        return false;
    }

}
