import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Dubbo本地服务驱动
 * Created by hope6537 on 16/3/13.
 */
public class DubboDriver {

    public static void main(String[] args) throws IOException {
        com.alibaba.dubbo.container.Main.main(args);
    }

}
