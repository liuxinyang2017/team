package com.qatang.team.algorithm.xdd;

/**
 * 单链表类
 * Created by Popo on 2017/4/20.
 */
public class LinkList {
    public Node head; // 定义一个头结点
    private int position = 0;// 节点的位置

    public LinkList() {
        this.head = null;
    }

    // 插入一个头节点
    public void insertHeadNode(int data) {
        Node node = new Node(data);
        node.next = head;
        head = node;
    }

    // 正方向显示出所有的节点信息
    public void displayAllNodes() {
        Node current = head;
        while (current != null) {
            current.display();
            current = current.next;
        }
        System.out.println();
    }

    // 输入头结点反向显示出所有的节点信息
    public void displayAllNodesReverse(Node head) {
        if (head != null) {
            if (head.next != null) {
                displayAllNodesReverse(head.next);
            }

            head.display();
        }
    }

    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.insertHeadNode(1);
        linkList.insertHeadNode(2);
        linkList.insertHeadNode(3);
        System.out.println("正方向显示出所有的节点信息");
        linkList.displayAllNodes();
        System.out.println("反方向显示出所有的节点信息");
        linkList.displayAllNodesReverse(linkList.head);
    }
}
