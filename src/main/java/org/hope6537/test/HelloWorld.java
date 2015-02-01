package org.hope6537.test;

import org.hope6537.hadoop.sort.SortBean;

import java.util.Arrays;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class HelloWorld {

    public static void main(String[] args) {

        String str = "zhangsan@163.com\t6000\t0\t2014-02-20\n" +
                "lisi@163.com\t2000\t0\t2014-02-20\n" +
                "lisi@163.com\t0\t100\t2014-02-20\n" +
                "zhangsan@163.com\t3000\t0\t2014-02-20\n" +
                "wangwu@126.com\t9000\t0\t2014-02-20\n" +
                "wangwu@126.com\t0\t200\t\t2014-02-20\n";
        String[] lines = str.split("\n");
        String[] datas = str.split("\t");
        System.out.println(Arrays.toString(datas));
        String username = datas[0];
        Long increase = Long.parseLong(datas[1]);
        Long decrease = Long.parseLong(datas[2]);
        String date = datas[3];
        SortBean sortBean = new SortBean();
        SortBean.setBeanData(sortBean, username, increase, decrease, date);
        System.out.println(username + "->" + date);
        System.out.println(sortBean);
    }


}
