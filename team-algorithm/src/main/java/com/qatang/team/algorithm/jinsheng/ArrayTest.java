package com.qatang.team.algorithm.jinsheng;

/**
 * @author jinsheng
 */
public class ArrayTest {
    private static int count = 0;

    public static void main(String[] args) {
        Integer[][] array = new Integer[][]{{1, 2, 3, 4},
                {2, 3, 4, 5},
                {3, 4, 5, 6},
                {4, 5, 6, 7}};

//        Integer[][] array = new Integer[][]{{1,2,3,4,5,6, 7, 8, 9},
//                                            {2,3,4,5,6,7, 8, 9,10},
//                                            {3,4,5,6,7,8, 9,10,11},
//                                            {4,5,6,7,8,9,10,11,12}};

        find(array, 1);
        find(array, 2);
        find(array, 3);
        find(array, 4);
        find(array, 5);
        find(array, 6);
        find(array, 7);
        find(array, 8);
        find(array, 9);
        find(array, 10);
        find(array, 11);
        find(array, 12);
        find(array, 13);
        find(array, 14);
        find(array, 15);
        find(array, 16);
        find(array, 17);
        find(array, 18);
    }

    private static void find(Integer[][] array, int target) {
        count = 0;
        boolean result = find(array, 0, 0, array.length, array[0].length, target);
        System.out.println(String.format("%s exist ? %s count : %s", String.format("%04d", target), String.format("%1$5s", result), String.format("%04d", count)));
    }

    private static boolean find(Integer[][] array, int x, int y, int xLen, int yLen, int target) {
        ++ count;
        if (xLen == 1 && yLen == 1) {
            return target == array[x][y];
        }

        if (xLen == 1) {
            int yHalfLen = yLen / 2;
            int x1 = x;
            int y1 = y;

            int x2 = x;
            int y2 = y + yLen / 2;

            if (target == array[x2][y2]) {
                return true;
            }

            if (target > array[x2][y2]) {
                if (find(array, x2, y2, xLen, yLen - yHalfLen, target)) {
                    return true;
                }
                return false;
            }

            if (find(array, x1, y1, xLen, yHalfLen, target)) {
                return true;
            }
            return false;
        }

        if (yLen == 1) {
            int xHalfLen = xLen / 2;
            int x1 = x;
            int y1 = y;

            int x2 = x + xLen / 2;
            int y2 = y;

            if (target == array[x2][y2]) {
                return true;
            }

            if (target > array[x2][y2]) {
                if (find(array, x2, y2, xLen - xHalfLen, yLen, target)) {
                    return true;
                }
                return false;
            }

            if (find(array, x1, y1, xHalfLen, yLen, target)) {
                return true;
            }
            return false;
        }

        int xHalfLen = xLen / 2;
        int yHalfLen = yLen / 2;

        int x1 = x;
        int y1 = y;

        int x2 = x;
        int y2 = y + yHalfLen;

        int x3 = x + xHalfLen;
        int y3 = y;

        int x4 = x + xHalfLen;
        int y4 = y + yHalfLen;

        if (target == array[x4][y4]) {
            return true;
        }

        if (target > array[x4][y4]) {
            if (find(array, x4, y4, xLen - xHalfLen, yLen - yHalfLen, target)) {
                return true;
            }
            if (find(array, x3, y3, xHalfLen, yLen - yHalfLen, target)) {
                return true;
            }
            if (find(array, x2, y2, xLen - xHalfLen, yHalfLen, target)) {
                return true;
            }
            return false;
        }

        if (find(array, x3, y3, xHalfLen, yLen - yHalfLen, target)) {
            return true;
        }
        if (find(array, x2, y2, xLen - xHalfLen, yHalfLen, target)) {
            return true;
        }
        if (find(array, x1, y1, xHalfLen, yHalfLen, target)) {
            return true;
        }
        return false;
    }
}
