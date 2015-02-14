package org.hope6537.test;

import org.hope6537.hadoop.recommend.RecommendDriver;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class HelloWorld {

    public static void main(String[] args) {

        String path = "1\t103->2.5,101->5.0,102->3.0";
        String[] data = RecommendDriver.DELIMITER.split(path);
        for (int i = 1; i < data.length; i++) {
            String[] strs = data[i].split("->");
            System.out.println(strs[0]);
            System.out.println(strs[1]);
        }
    }


}
