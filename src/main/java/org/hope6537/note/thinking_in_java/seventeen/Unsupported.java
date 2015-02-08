package org.hope6537.note.thinking_in_java.seventeen;

import java.util.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 什么是容器的未获支持操作？
 * @signdate 2014年7月23日上午10:54:05
 * @company Changchun University&SHXT
 */
public class Unsupported {
    static void test(String msg, List<String> list) {
        System.out.println("=====" + msg + "=====");
        Collection<String> c = list;
        Collection<String> subList = list.subList(1, 8);
        Collection<String> c2 = new ArrayList<String>();
        try {
            c.retainAll(c2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("A B C D E F G H J K L M N O P Q"
                .split(" "));
        test("Copy", new ArrayList<String>(list));
        test("Arrays.asList()", list);//因为Arrays.asList仅仅支持不会影响数组大小的操作
        test("unmodififableList()", Collections.unmodifiableList(new ArrayList<String>(list)));//产生一个不可修改的列表
    }
}
