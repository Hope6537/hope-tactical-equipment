package org.hope6537.dubbo;

import org.hope6537.helper.SpringTestHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

/**
 * Created by hope6537 on 16/3/13.
 */
public class DubboTest extends SpringTestHelper{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    public static void main(String[] args) throws IOException {
        pro();
    }

}
