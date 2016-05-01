# encoding:UTF-8
import os


def generate(objectName, testColumn):
    param = ''
    modify_param = ''
    delete_param = ''
    i = 0
    for c in testColumn:
        if c[1] == 'varchar' or c[1] == 'text':
            param += ('"test' + str(i) + '"+System.currentTimeMillis(),')
            modify_param += ('"modify' + str(i) + '"+System.currentTimeMillis(),')
            delete_param += ('"wait_delete' + str(i) + '"+System.currentTimeMillis(),')
        if c[1] == 'int':
            param += ("9" + str(i) + ',')
            modify_param += ("8" + str(i) + ',')
            delete_param += ("7" + str(i) + ',')
        i += 1
    param = param[0:-1]
    modify_param = modify_param[0:-1]
    delete_param = delete_param[0:-1]
    text = """
    import com.alibaba.fastjson.JSON;
import com.comichentai.dto.{ObjectName}Dto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.{ObjectName}Service;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class {ObjectName}ServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private {ObjectName}Service {objectName}Service;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info({objectName}Service.toString());
    }

    @Test
    public void testAdd{ObjectName}() {
        ResultSupport<Integer> integerResultSupport = {objectName}Service.add{ObjectName}(""" + param + """);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModify{ObjectName}() {
        ResultSupport<Integer> resultSupport = {objectName}Service.add{ObjectName}(""" + param + """);
        Integer id = resultSupport.getModule();
        {ObjectName}Dto dto = new {ObjectName}Dto(""" + modify_param + """);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = {objectName}Service.modify{ObjectName}(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = {objectName}Service.batchModify{ObjectName}(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemove{ObjectName}() {
        ResultSupport<Integer> resultSupport = {objectName}Service.add{ObjectName}(""" + delete_param + """);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = {objectName}Service.remove{ObjectName}(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = {objectName}Service.batchRemove{ObjectName}(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGet{ObjectName}ById() {
        String comic = JSON.toJSONString({objectName}Service.get{ObjectName}ById(1));
        logger.info(comic);
    }

    @Test
    public void testGet{ObjectName}ListByIdList() {
        String comicList = JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGet{ObjectName}ListByQuery() {
        {ObjectName}Dto dto = new {ObjectName}Dto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<{ObjectName}Dto>> {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
        dto.setCurrentPage(2);
        {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
        dto.setCurrentPage(3);
        {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./test/" + objectName + "ServiceImplTest.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName