package org.hope6537.dubbo.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hope6537 on 15/11/11.
 */
public class ServiceProvider {


    private static final ClassPathXmlApplicationContext context;

    static{
        context = new ClassPathXmlApplicationContext(
                new String[]{"classpath*:provider/dubbo_time_service_provider.xml"});

        context.start();
    }

    public static TimeService initTimeService() throws Exception {
        return (TimeService) context.getBean("timeService");
    }


    public static void main(String[] args) throws Exception {
        TimeService timeService = initTimeService();
        System.out.println(timeService.getCurrentTime());
    }

}
