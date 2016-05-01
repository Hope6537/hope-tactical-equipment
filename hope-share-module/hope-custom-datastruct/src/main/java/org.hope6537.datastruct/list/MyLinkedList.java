package org.hope6537.datastruct.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @version 0.9
 * @Describe 自编LinkedList
 * @Author Hope6537(赵鹏)
 * @Signdate 2013-10-27下午07:26:32
 * @company Changchun University&SHXT
 */
@SuppressWarnings("hiding")
public class MyLinkedList<Object> implements Iterable<Object> {

    private int theSize;

    private int modCount = 0;

    /**
     * @Describe 头结点 用于识别并从头检索
     */
    private Node<Object> beginMarker;

    /**
     * @Describe 尾结点 用于识别并从尾检索
     */
    private Node<Object> endMarker;

    public MyLinkedList() {
        clear();
    }

    /**
     * @Descirbe 清空链表
     * @Author Hope6537(赵鹏)
     * @Params
     * @SignDate 2013-10-27下午07:27:18
     * @Version 0.9
     */
    public void clear() {
        beginMarker = new Node<Object>(null, null, null);
        endMarker = new Node<Object>(null, beginMarker, null);
        beginMarker.next = endMarker;

        theSize = 0;
        modCount++;
    }

    /**
     * @return
     * @Descirbe 返回数量
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2013-10-27下午07:27:30
     * @Version 0.9
     */
    public int size() {
        return theSize;
    }

    /**
     * @return
     * @Descirbe 根据数量判断是否为空
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2013-10-27下午07:27:39
     * @Version 0.9
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @param x
     * @return
     * @Descirbe 直接从屁股后面添加
     * @Author Hope6537(赵鹏)
     * @Params @param x
     * @Params @return
     * @SignDate 2013-10-27下午07:27:58
     * @Version 0.9
     */
    public boolean add(Object x) {
        add(size(), x);
        return true;
    }

    /**
     * @param index
     * @param x
     * @Descirbe 从索引位置将原节点退一格，然后添加新数据
     * @Author Hope6537(赵鹏)
     * @Params @param index
     * @Params @param x
     * @SignDate 2013-10-27下午07:28:12
     * @Version 0.9
     */
    public void add(int index, Object x) {
        addBefore(getNode(index), x);
    }

    /**
     * @param p
     * @param x
     * @Descirbe 添加新数据 从该节点的前方
     * @Author Hope6537(赵鹏)
     * @Params @param p
     * @Params @param x
     * @SignDate 2013-10-27下午07:28:41
     * @Version 0.9
     */
    private void addBefore(Node<Object> p, Object x) {
        //在目标之前插入新的数据项目
        Node<Object> newNode = new Node<Object>(x, p.prev, p);
        //首先创建一个新的节点 p是要插入的地方的旧节点，位于新节点之后，所以数据是x，前节点p的原来的前节点，后节点是p
        newNode.prev.next = newNode;
        //然后新节点的前节点也就是原来p的前节点的后指针指向新节点。
        p.prev = newNode;
        //然后p节点的前指针指向后节点。
        theSize++;//大小+1
        modCount++;//变更次数+1
    }

    /**
     * @param index
     * @return
     * @Descirbe 通过索引得到节点的方法
     * @Author Hope6537(赵鹏)
     * @Params @param index
     * @Params @return
     * @SignDate 2013-10-27下午07:29:00
     * @Version 0.9
     */
    private Node<Object> getNode(int index) {
        //这是内部的私有的得到节点的方法
        Node<Object> p;
        if (index < 0 || index > size()) {
            //如果数组越界
            throw new IndexOutOfBoundsException();
        }
        if (index < size() / 2) {
            //如果索引在前半部分,就从头开始查找
            p = beginMarker.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            //如果索引从后半部分，就从尾部开始查找
            p = endMarker;
            for (int i = size(); i > index; i--) {
                p = p.prev;
            }
        }
        return p;
    }

    /**
     * @param p
     * @return
     * @Descirbe 将节点p从链表中移除, 并返回该项的数据
     * @Author Hope6537(赵鹏)
     * @Params @param p
     * @Params @return
     * @SignDate 2013-10-27下午07:29:57
     * @Version 0.9
     */
    private Object remove(Node<Object> p) {
        //p的后节点的前指针指向p的前一项
        p.next.prev = p.prev;
        //p的前节点的后指针指向p的后一项
        p.prev.next = p.next;
        theSize--;
        modCount--;

        return p.data;
    }

    /**
     * @param index
     * @return
     * @Descirbe 通过索引返回数据
     * @Author Hope6537(赵鹏)
     * @Params @param index
     * @Params @return
     * @SignDate 2013-10-27下午07:32:30
     * @Version 0.9
     */
    public Object get(int index) {
        return getNode(index).data;
    }

    /**
     * @param index
     * @param newValue
     * @return
     * @Descirbe 通过索引值来更改当前节点的数据
     * @Author Hope6537(赵鹏)
     * @Params @param index
     * @Params @param newValue
     * @Params @return
     * @SignDate 2013-10-27下午07:33:39
     * @Version 0.9
     */
    public Object set(int index, Object newValue) {
        Node<Object> p = getNode(index);
        Object oldValue = p.data;
        p.data = newValue;
        return oldValue;
    }

    public Object remove(int index) {
        return remove(getNode(index));
    }

    @Override
    public Iterator<Object> iterator() {
        return new LinkedListIterator();
    }

    /**
     * @param <Object>
     * @version 0.9
     * @Describe 内部类Node 节点的结构
     * @Author Hope6537(赵鹏)
     * @Signdate 2013-10-27下午07:26:42
     * @company Changchun University&SHXT
     */
    private static class Node<Object> {

        public Object data;
        public Node<Object> prev;
        public Node<Object> next;

        /**
         * @param @param d 数据
         * @param @param p 前指针
         * @param @param n 后指针
         * @Describe 构造方法 分别分为三部分
         * @Author Hope6537(赵鹏)
         */
        public Node(Object d, Node<Object> p, Node<Object> n) {
            data = d;
            prev = p;
            next = n;
        }
    }

    private class LinkedListIterator implements Iterator<Object> {


        private Node<Object> current = beginMarker.next;

        private int expectedModCount = modCount;

        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            //如果索引指针指向了最后的尾结点。那么是返回没有下一项了
            return current != endMarker;

        }

        @Override
        public Object next() {
            if (modCount != expectedModCount) {
                //如果链表的更改次数和当前迭代器内记录的不符合，那么说明迭代器无效
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                //如果没有下一项了
                throw new NoSuchElementException();
            }
            //获取当前的索引指针的指向的结点的数据
            Object nextItem = current.data;
            //同时索引后移
            current = current.next;
            //同时代表这个索引已经被移动到头结点之外了，可以被移除
            okToRemove = true;
            //返回数据
            return nextItem;

        }

        @Override
        public void remove() {

            if (modCount != expectedModCount) {
                //如果链表的更改次数和当前迭代器内记录的不符合，那么说明迭代器无效
                throw new ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            //注意这里为何不是current本身的原因，因为此时索引指针已经因为next而后移了，而我们看到的数据是指针前移之前的数据，所以要prev
            MyLinkedList.this.remove(current.prev);
            okToRemove = false;
            expectedModCount++;

        }

    }

}
