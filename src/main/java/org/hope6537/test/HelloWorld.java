package org.hope6537.test;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class HelloWorld {

    public static void main(String[] args) {

        String path = "hdfs://hadoop2master:9000/data/bin.dat";
        System.out.println(path.split(":")[2].substring(4));
    }


}
