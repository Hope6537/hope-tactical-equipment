import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.ClassesDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.ClassesService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class ClassesServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClassesService classesService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(classesService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = classesService.addClasses("test0" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddClasses() {
        ResultSupport<Integer> integerResultSupport = classesService.addClasses("test0" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyClasses() {
        ResultSupport<Integer> resultSupport = classesService.addClasses("test0" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ClassesDto dto = new ClassesDto("modify0" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = classesService.modifyClasses(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = classesService.batchModifyClasses(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(classesService.getClassesListByIdList(idList)));
    }

    @Test
    public void testRemoveClasses() {
        ResultSupport<Integer> resultSupport = classesService.addClasses("wait_delete0" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = classesService.removeClasses(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = classesService.batchRemoveClasses(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(classesService.getClassesListByIdList(idList)));
    }


    @Test
    public void testGetClassesById() {
        ResultSupport<ClassesDto> result = classesService.getClassesById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetClassesListByIdList() {
        ResultSupport<List<ClassesDto>> result = classesService.getClassesListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetClassesListByQuery() {
        ClassesDto dto = new ClassesDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<ClassesDto>> classesListByQuery = classesService.getClassesListByQuery(dto);
        logger.info(JSON.toJSONString(classesListByQuery));
        dto.setCurrentPage(2);
        classesListByQuery = classesService.getClassesListByQuery(dto);
        logger.info(JSON.toJSONString(classesListByQuery));
        dto.setCurrentPage(3);
        classesListByQuery = classesService.getClassesListByQuery(dto);
        logger.info(JSON.toJSONString(classesListByQuery));
    }


}

    