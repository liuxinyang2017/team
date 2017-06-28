# 算法
1. 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数

2. 请实现一个函数，把字符串中的每个空格替换成"%20"，例如“We are happy.“，则输出”We%20are%20happy.“（不许用任何第三方jar）

3. 了解什么是链表结构。题目：输入个链表的头结点，从尾到头反过来打印出每个结点的值。
                public class ListNode {  
                    int val; // 结点的值  
                    ListNode nxt; // 下一个结点  
                } 

                 ListNode root = new ListNode();  
                 root.val = 1;  
                 root.nxt = new ListNode();  
                 root.nxt.val = 2;  
                 root.nxt.nxt = new ListNode();  
                 root.nxt.nxt.val = 3;  
                 root.nxt.nxt.nxt = new ListNode() ;  
                 root.nxt.nxt.nxt.val = 4;  
                 root.nxt.nxt.nxt.nxt = new ListNode();  
                 root.nxt.nxt.nxt.nxt.val = 5;  