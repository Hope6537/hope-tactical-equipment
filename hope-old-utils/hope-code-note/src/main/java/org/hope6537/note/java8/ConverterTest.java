package org.hope6537.note.java8;

import org.junit.Test;

public class ConverterTest {

    @Test
    public void testCase1() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Converter<String, Integer> converter2 = Integer::valueOf;
        Integer res = converter.convert("123");
        Integer res2 = converter2.convert("1234");
        System.out.println(res + res2);
    }

    /**
     * lambda访问局部变量
     */
    @Test
    public void testCase2() {
        int num = 1;
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from + num);
        System.out.println(converter.convert("1234"));
    }


}
