package com.qatang.team.algorithm.jinsheng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 了解什么是链表结构。题目：输入个链表的头结点，从尾到头反过来打印出每个结点的值。
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
     root.nxt.nxt.nxt = new ListNode();
     root.nxt.nxt.nxt.val = 4;
     root.nxt.nxt.nxt.nxt = new ListNode();
     root.nxt.nxt.nxt.nxt.val = 5;
 * @author jinsheng
 */
public class ListNode {
    int val; // 结点的值
    ListNode nxt; // 下一个结点

    public ListNode(int val) {
        this.val = val;
        this.nxt = null;
    }

    public void addNode(int val) {
        ListNode newNode = new ListNode(val);
        ListNode nxt = this.nxt;
        this.nxt = newNode;
        newNode.nxt = nxt;
    }

    public void addNode(ListNode newNode) {
        ListNode nxt = this.nxt;
        this.nxt = newNode;
        newNode.nxt = nxt;
    }

    public void print() {
        ListNode nxt = this;
        while (nxt != null) {
            System.out.print(nxt.val);
            nxt = nxt.nxt;
        }
    }

    public void printDesc() {
        List<Integer> list = new ArrayList<>();
        ListNode nxt = this;
        while (nxt != null) {
            list.add(nxt.val);
            nxt = nxt.nxt;
        }
        Collections.reverse(list);
        list.forEach(System.out::print);
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node3 = new ListNode(3);
        node1.nxt = node3;
        node1.addNode(2);
        node1.print();
        System.out.println();
        node1.printDesc();
    }
}

