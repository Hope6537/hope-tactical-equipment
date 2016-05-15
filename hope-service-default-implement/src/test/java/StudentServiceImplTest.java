
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.StudentDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.StudentService;
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
public class StudentServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StudentService studentService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(studentService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = studentService.addStudent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),93,94);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddStudent() {
        ResultSupport<Integer> integerResultSupport = studentService.addStudent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),93,94);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyStudent() {
        ResultSupport<Integer> resultSupport = studentService.addStudent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),93,94);
        Integer id = resultSupport.getModule();
        StudentDto dto = new StudentDto("modify0"+System.currentTimeMillis(),"modify1"+System.currentTimeMillis(),"modify2"+System.currentTimeMillis(),83,84);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = studentService.modifyStudent(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = studentService.batchModifyStudent(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(studentService.getStudentListByIdList(idList)));
    }

    @Test
    public void testRemoveStudent() {
        ResultSupport<Integer> resultSupport = studentService.addStudent("wait_delete0"+System.currentTimeMillis(),"wait_delete1"+System.currentTimeMillis(),"wait_delete2"+System.currentTimeMillis(),73,74);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = studentService.removeStudent(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = studentService.batchRemoveStudent(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(studentService.getStudentListByIdList(idList)));
    }


    @Test
    public void testGetStudentById() {
        ResultSupport<StudentDto> result = studentService.getStudentById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetStudentListByIdList() {
        ResultSupport<List<StudentDto>> result = studentService.getStudentListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetStudentListByQuery() {
        StudentDto dto = new StudentDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<StudentDto>> studentListByQuery = studentService.getStudentListByQuery(dto);
        logger.info(JSON.toJSONString(studentListByQuery));
        dto.setCurrentPage(2);
        studentListByQuery = studentService.getStudentListByQuery(dto);
        logger.info(JSON.toJSONString(studentListByQuery));
        dto.setCurrentPage(3);
        studentListByQuery = studentService.getStudentListByQuery(dto);
        logger.info(JSON.toJSONString(studentListByQuery));
    }


}

    