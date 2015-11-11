package org.hope6537.dubbo.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hope6537 on 15/11/11.
 */
public class ServiceProvider {

    public static void initTimeService(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath*:provider/dubbo_time_service_provider.xml"});
        context.start();
    }


    public static void main(String[] args) throws Exception {
        initTimeService(args);
    }

}
