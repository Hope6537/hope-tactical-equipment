package org.hope6537.soa.drivers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by hope6537 on 15/12/10.
 * Any Question sent to hope6537@qq.com
 */
public class ProDriver {

    public static void main(String[] args) throws IOException, InterruptedException {
        pro();
    }

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-webapp-pro.xml");
        context.start();
        System.in.read();
    }
}