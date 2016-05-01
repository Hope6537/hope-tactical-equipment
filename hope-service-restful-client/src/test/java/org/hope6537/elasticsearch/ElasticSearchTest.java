package org.hope6537.elasticsearch;

import org.hope6537.helper.SpringTestHelper;
import org.hope6537.rest.utils.ElasticSearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Created by dintama on 16/5/1.
 */
public class ElasticSearchTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/spring-dubbo-service-cli.xml");
        context.start();
        Object elasticSearchUtilBean = context.getBean("elasticSearchUtil");
        ElasticSearchUtil elasticSearchUtil = (ElasticSearchUtil) elasticSearchUtilBean;
        List<Integer> idList = elasticSearchUtil.getIdList("comichentai", "comic", "C89", 0, 30);
        System.out.println(idList.toString());
    }


}
