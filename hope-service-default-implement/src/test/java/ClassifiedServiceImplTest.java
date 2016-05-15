
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.ClassifiedDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.ClassifiedService;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class ClassifiedServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClassifiedService classifiedService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(classifiedService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = classifiedService.addClassified("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddClassified() {
        ResultSupport<Integer> integerResultSupport = classifiedService.addClassified("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyClassified() {
        ResultSupport<Integer> resultSupport = classifiedService.addClassified("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ClassifiedDto dto = new ClassifiedDto("modify0"+System.currentTimeMillis(),"modify1"+System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = classifiedService.modifyClassified(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = classifiedService.batchModifyClassified(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(classifiedService.getClassifiedListByIdList(idList)));
    }

    @Test
    public void testRemoveClassified() {
        ResultSupport<Integer> resultSupport = classifiedService.addClassified("wait_delete0"+System.currentTimeMillis(),"wait_delete1"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = classifiedService.removeClassified(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = classifiedService.batchRemoveClassified(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(classifiedService.getClassifiedListByIdList(idList)));
    }


    @Test
    public void testGetClassifiedById() {
        ResultSupport<ClassifiedDto> result = classifiedService.getClassifiedById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetClassifiedListByIdList() {
        ResultSupport<List<ClassifiedDto>> result = classifiedService.getClassifiedListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetClassifiedListByQuery() {
        ClassifiedDto dto = new ClassifiedDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<ClassifiedDto>> classifiedListByQuery = classifiedService.getClassifiedListByQuery(dto);
        logger.info(JSON.toJSONString(classifiedListByQuery));
        dto.setCurrentPage(2);
        classifiedListByQuery = classifiedService.getClassifiedListByQuery(dto);
        logger.info(JSON.toJSONString(classifiedListByQuery));
        dto.setCurrentPage(3);
        classifiedListByQuery = classifiedService.getClassifiedListByQuery(dto);
        logger.info(JSON.toJSONString(classifiedListByQuery));
    }


}

    