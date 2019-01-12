package org.hope6537.note.leetcode;

public class ReverseLinkedList {

    static class ListNode {

        private Integer value;

        private ListNode next;

        public ListNode(Integer value) {
            this.value = value;
        }
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        //设置一个顶点
        ListNode dummy = new ListNode(-1);

        //顶点找到头结点
        dummy.next = head;
        //prev就是占位的next 1
        ListNode prev = dummy.next;
        //当前节点就是2
        ListNode pCur = prev.next;
        //当前节点不为空
        while (pCur != null) {
            //1的next =3
            prev.next = pCur.next;
            //2的next =1
            pCur.next = dummy.next;
            //2前移到顶点
            dummy.next = pCur;
            //当前节点变为 1的next,现在是3
            pCur = prev.next;
        }
        return dummy.next;
    }

    public static ListNode rList(ListNode head) {

        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1);

        dummy.next = head;

        ListNode prev = dummy.next;
        ListNode curr = prev.next;

        while (curr != null) {
            prev.next = curr.next;
            curr.next = dummy.next;
            dummy.next = curr;
            curr = prev.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);

        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        ListNode res = reverseList(a);

        System.out.println(res);

    }

}
