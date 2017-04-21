package com.qatang.team.algorithm.wzl;

/**
 * 了解什么是链表结构。题目：输入个链表的头结点，从尾到头反过来打印出每个结点的值。
 public class LinkedList {
 int val; // 结点的值
 LinkedList nxt; // 下一个结点
 }

 LinkedList root = new LinkedList();
 root.val = 1;
 root.nxt = new LinkedList();
 root.nxt.val = 2;
 root.nxt.nxt = new LinkedList();
 root.nxt.nxt.val = 3;
 root.nxt.nxt.nxt = new LinkedList();
 root.nxt.nxt.nxt.val = 4;
 root.nxt.nxt.nxt.nxt = new LinkedList();
 root.nxt.nxt.nxt.nxt.val = 5;
 * @author wangzhiliang
 */
public class LinkedList {
    private class Node {
        private Node next = null;
        private int value;

        Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Node head = null;

    public void insertNode(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
    }

    public void printValue(Node node) {
        //正序
        //System.out.println(node.getValue());
        if (node.next != null) {
            printValue(node.next);
        }
        //倒序
        System.out.println(node.getValue());
    }

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.insertNode(1);
        linkedList.insertNode(2);
        linkedList.insertNode(3);
        linkedList.insertNode(4);
        linkedList.insertNode(5);
        linkedList.insertNode(6);

        linkedList.printValue(linkedList.head);
    }

}
