/**
 * Created by Administrator on 2017/3/22.
 */
public class Test {

    /**
     * 在二维数组中查询数字
     * @param array 二维数组
     * @param num 被查找的数字
     */
    public static void searchNum(int[][] array, int num) {
        int step = 0;
        int row = 0;
        int column = array[0].length - 1;
        int maxRow = array.length;
        int minColumn = 0;
        while (true) {
            int temp = array[row][column];
            step++;
            if (temp < num) {
                row++;
            } else if (temp > num) {
                column--;
            } else {
                System.out.println(String.format("用了%s步，在第%s行第%s列查找到这个数字",step,  ++row, ++column ));
                break;
            }

            if (row >= maxRow || column <= minColumn) {
                System.out.println("数组中不存在这个数字");
                break;
            }
        }

    }


    /**
     * replaceStr 之间的数组元素移动到二维数组存储 减少遍历次数
     * @param str
     * @param replaceStr
     * @return
     */
    public static String replace(String str, String replaceStr) {
        StringBuilder sb = new StringBuilder();
        char[] arrayStr = str.toCharArray();
        char[][] resultArray = new char[arrayStr.length][];
        // 当前replaceStr数据位置标志
        int indexFlag = 0;
        // 二维数组实际长度
        int resultArrayRealLength = 0;
        // replaceStr 之间的数据移动到二维数组存储
        for (int x = 0; x < arrayStr.length; x++) {
            if (arrayStr[x] == ' ') {
                char[] tempArray = new char[x - indexFlag];
                for (int y = indexFlag; y < x; y++) {
                    tempArray[y - indexFlag] = arrayStr[y];
                }
                resultArray[resultArrayRealLength++] = tempArray;
                indexFlag = x + 1;
            }
        }

        // 最后一个
        if (indexFlag != arrayStr.length) {
            char[] tempArray = new char[arrayStr.length - indexFlag];
            for (int y = indexFlag; y < arrayStr.length; y++) {
                tempArray[y - indexFlag] = arrayStr[y];
            }
            resultArray[resultArrayRealLength++] = tempArray;
        }

        // 循环二维数组 拼凑出替换后的串
        for (int x = 0; x < resultArrayRealLength; x++) {
            for (int y = 0; y < resultArray[x].length; y++) {
                sb.append(resultArray[x][y]);
            }
            if (x != resultArrayRealLength - 1 || indexFlag == arrayStr.length) {
                sb.append(replaceStr);

            }
        }

        return sb.toString();

    }


    public static void main(String[] args) {
        int a[][] = {{1, 5, 18}, {2 , 7, 19}, {13, 17, 29}};
        searchNum(a, 88);


        System.out.println(replace("We are happy.", "%20"));
        System.out.println(replace(" We are happy.", "%20"));
        System.out.println(replace("We are happy. ", "%20"));


    }
}
