package org.hope6537.java8;

import org.junit.Test;

public class LambdaOnField {

    static int outerStaticNum;
    int outerNum;

    @Test
    public void testScopes() {

        Converter<Integer, String> str1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> str2 = (from) -> {
            outerStaticNum = 72;//分别赋值
            return String.valueOf(from);
        };

    }
}