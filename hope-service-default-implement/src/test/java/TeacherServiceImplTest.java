import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.TeacherDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.TeacherService;
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
public class TeacherServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TeacherService teacherService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(teacherService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = teacherService.addTeacher("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddTeacher() {
        ResultSupport<Integer> integerResultSupport = teacherService.addTeacher("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyTeacher() {
        ResultSupport<Integer> resultSupport = teacherService.addTeacher("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        TeacherDto dto = new TeacherDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), "modify2" + System.currentTimeMillis(), "modify3" + System.currentTimeMillis(), "modify4" + System.currentTimeMillis(), "modify5" + System.currentTimeMillis(), "modify6" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = teacherService.modifyTeacher(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = teacherService.batchModifyTeacher(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(teacherService.getTeacherListByIdList(idList)));
    }

    @Test
    public void testRemoveTeacher() {
        ResultSupport<Integer> resultSupport = teacherService.addTeacher("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), "wait_delete2" + System.currentTimeMillis(), "wait_delete3" + System.currentTimeMillis(), "wait_delete4" + System.currentTimeMillis(), "wait_delete5" + System.currentTimeMillis(), "wait_delete6" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = teacherService.removeTeacher(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = teacherService.batchRemoveTeacher(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(teacherService.getTeacherListByIdList(idList)));
    }


    @Test
    public void testGetTeacherById() {
        ResultSupport<TeacherDto> result = teacherService.getTeacherById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetTeacherListByIdList() {
        ResultSupport<List<TeacherDto>> result = teacherService.getTeacherListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetTeacherListByQuery() {
        TeacherDto dto = new TeacherDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<TeacherDto>> teacherListByQuery = teacherService.getTeacherListByQuery(dto);
        logger.info(JSON.toJSONString(teacherListByQuery));
        dto.setCurrentPage(2);
        teacherListByQuery = teacherService.getTeacherListByQuery(dto);
        logger.info(JSON.toJSONString(teacherListByQuery));
        dto.setCurrentPage(3);
        teacherListByQuery = teacherService.getTeacherListByQuery(dto);
        logger.info(JSON.toJSONString(teacherListByQuery));
    }


}

    