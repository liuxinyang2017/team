package com.qatang.team.algorithm.xdd;

/**
 * 节点类
 * Created by Popo on 2017/4/20.
 */
public class Node {
    protected Node next; //指针域
    protected int data;//数据域

    public Node(int data) {
        this.data = data;
    }

    public void display() {
        System.out.print(data + " ");
    }
}