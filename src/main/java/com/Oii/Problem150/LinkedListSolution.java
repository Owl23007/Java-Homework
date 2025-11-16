package com.Oii.Problem150;

import java.util.HashMap;
import java.util.Map;

public class LinkedListSolution {
    // 25.11.13 - 1 环形链表 leetcode - 141
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    // 25.11.13 - 2 两数相加 leetcode - 2
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0); // 虚拟头节点
        ListNode current = res;
        int needPlusOne = 0;  // 进位标志

        // 当 l1 或 l2 不为空，或还有进位时继续循环
        while (l1 != null || l2 != null || needPlusOne != 0) {
            int sum = needPlusOne;

            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next; // 移动到下一位
            }

            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next; // 移动到下一位
            }

            needPlusOne = sum / 10; // 计算进位
            current.next = new ListNode(sum % 10);
            current = current.next; // 移动到结果链表下一位
        }
        return res.next; // 返回头节点
    }

    // 25.11.13 - 3 合并两个有序链表 leetcode - 21
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode res = new ListNode(0);

        ListNode p = res;

        while (p1 != null && p2!= null){
            if (p1.val < p2.val){
                p.next = p1;
                p1 = p1.next;
            }else{
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        p.next = p1 == null ? p2 : p1;
        return res.next;
    }

    // 递归的解法
    public ListNode mergeTwoLists_2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists_2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists_2(l1, l2.next);
            return l2;
        }
    }

    // 25.11.14 - 1 随机链表的复制 leetcode - 138
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        Node res = new Node(0);
        Node current = res;
        Node p = head;

        Map<Node, Node> map = new HashMap<>();
        while (p != null) {
            Node newNode = new Node(p.val);
            current.next = newNode;
            map.put(p, newNode);
            current = current.next;
            p = p.next;
        }
        p = head;
        current = res.next;
        while (p != null) {
            if (p.random != null) {
                current.random = map.get(p.random);
            }

            p = p.next;
            current = current.next;
        }

        return res.next;
    }

    // 25.11.14 - 2 反转链表II leetcode - 92
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 创建一个虚拟头节点，简化边界情况的处理
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        // 移动到反转部分的前一个节点
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        // start 是反转部分的第一个节点
        ListNode start = pre.next;
        // then 是 start 的下一个节点，将是反转过程中的关键节点
        ListNode then = start.next;

        // 执行 (right - left) 次操作来反转中间的节点
        for (int i = 0; i < right - left; i++) {
            start.next = then.next; // 将 then 节点从原来的位置断开
            then.next = pre.next; // 将 then 节点连接到 pre 的后面，成为新的第一个节点
            pre.next = then; // pre.next 指向这个新来的节点
            then = start.next; // then 指向下一个待反转的节点
        }
        return dummy.next;
    }

    // 25.11.15 - 1 K 个一组翻转链表 leetcode - 25
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode tail = dummy;
        ListNode pre = dummy;
        int count = 0;
        while (tail != null) {
            if ((count - 1) % k == 0) {
                ListNode start = pre.next;
                for (int i = 0; i < k - 1; i++) {
                    ListNode then = start.next;
                    start.next = then.next;
                    then.next = pre.next;
                    pre.next = then;
                }
                pre = start;
                tail = start;
            }
            tail = tail.next;
            count++;
        }

        return dummy.next;
    }

    // 25.11.15 - 2 删除链表的倒数第 N 个结点 leetcode - 19
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy;
        ListNode fast = head;
        while (n >= 0 && fast != null) {
            fast = fast.next;
            n--;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;

        return dummy.next;
    }

    // 25.11.16 -1 删除链表中重复的结点 leetcode - 83
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode pre = dummy; // 记录上一个节点

        while(head != null){
           if (head.next != null && head.val == head.next.val){
               while (head.next != null && head.val == head.next.val){
                   head = head.next;
               }
               pre.next = head.next;
           }else {
               pre = head;
           }
            head = head.next;
        }
        return dummy.next;
    }

    // 25.11.16 - 2 旋转链表 leetcode - 61
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null|| k == 0) {
            return head;
        }

        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        k = k % n;

        ListNode dummy = new ListNode(0, head);
        ListNode left = head;
        ListNode right = head;

        while (k > 0) {
            right = right.next;
            if (right == null) {
                right = head;
            }
            k--;
        }

        while (right.next != null) {
            left = left.next;
            right = right.next;
        }
        right.next = dummy.next;
        dummy.next = left.next;
        left.next = null;
        return dummy.next;
    }
}
