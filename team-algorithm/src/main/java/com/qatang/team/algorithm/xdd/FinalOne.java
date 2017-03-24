package com.qatang.team.algorithm.xdd;

import java.util.Scanner;

/**
 * 在一个二维整数数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * Created by xdd on 2017/3/1.
 */
public class FinalOne {
    public static boolean findArr(int[][] arr, int numbers) {
        Boolean found = false;
        if (arr == null) {
            return false;
        }
        int rows = arr.length;
        int columns = arr[0].length;
        int i = 1;
        if (rows > 0 && columns > 0) {
            int row = 0;
            int column = columns - 1;
            while (row < rows && column >= 0) {
                if (arr[row][column] == numbers) {
                    found = true;
                    break;
                } else if (arr[row][column] > numbers) {
                    i++;
                    --column;
                } else {
                    i++;
                    ++row;
                }
            }
        }

        System.out.println("查询次数 {}" + i);
        return found;
    }


    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        //输入行列
        System.out.println("输入行列数： ");
        int a = scanner.nextInt();//行
        int b = scanner.nextInt();//列

        //输入元素
        System.out.println("输入元素 ");
        int[][] arr = new int[a][b];
        for (int i = 0; i < a; i++) {
            System.out.println("第" + (i + 1) + "行");
            for (int j = 0; j < b; j++) {
                //System.out.println("第" + (j + 1) + "列");
                arr[i][j] = scanner.nextInt();
            }
        }

        System.out.println("待查询二维数组是：");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("请输入要查询的数字：");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        boolean result = findArr(arr, n);
        System.out.println("查询结果：" + result);
    }
}

