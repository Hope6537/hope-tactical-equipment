package org.hope6537.utils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>Describe: Spring辅助工具类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月10日下午6:23:04</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class SpringHelper {

    /**
     * <p>Describe: Spring核心上下文对象</p>
     * <p>Using: 用来操作Spring的IoC动作</p>
     */
    protected ApplicationContext context;
    protected Logger logger;

    /**
     * <p>Describe: 指定Spring配置文件<p>
     *
     * @param path 配置文件路径
     */
    public SpringHelper(String path) {
        if (context == null) {
            logger = Logger.getLogger(getClass());
            context = new ClassPathXmlApplicationContext(path);
            logger.info("Active the Spring");
        }
    }

    public SpringHelper(boolean bool) {
        if (context == null) {
            logger = Logger.getLogger(getClass());
            context = new ClassPathXmlApplicationContext(
                    new String[]{"classpath:spring/spring-core.xml"});
            logger.info("Active the Spring");
        }
    }

    public SpringHelper(String[] paths) {
        if (context == null) {
            logger = Logger.getLogger(getClass());
            context = new ClassPathXmlApplicationContext(paths);
            logger.info("Active the Spring");
        }
    }

    /**
     * <p>Describe:默认构造方法，使用默认的包路径 <p>
     */
    public SpringHelper() {
        if (context == null) {
            String location = this.getClass().getPackage().toString();
            location = location.split(" ")[1];
            location = location.replace(".", "/");
            location += "/applicationContext.xml";
            logger = Logger.getLogger(getClass());
            context = new ClassPathXmlApplicationContext(location);
            logger.info("Active the Spring");
        }
    }
}
