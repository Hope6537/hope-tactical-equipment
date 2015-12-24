package org.hope6537.soa.drivers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hope6537 on 15/12/10.
 * Any Question sent to hope6537@qq.com
 */
public class ConDriver {

    public static void main(String[] args) throws IOException, InterruptedException {
        con();
    }

    static void con() throws InterruptedException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-webapp-con.xml");
        TimeUnit.SECONDS.sleep(100);
    }
}
