package org.hope6537.test;

import org.hope6537.utils.ReflectGenericUtil;

import java.util.HashMap;
import java.util.Map;


public class JavaGenericTest<T> {

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        System.out.println(ReflectGenericUtil.getClassGenricType(hashMap.getClass()));

    }


}
