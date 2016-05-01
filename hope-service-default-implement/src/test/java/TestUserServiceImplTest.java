import com.alibaba.fastjson.JSON;
import org.hope6537.dto.TestUserDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.TestUserService;
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
public class TestUserServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestUserService testUserService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(testUserService.toString());
    }

    @Test
    public void testAddTestUser() {
        ResultSupport<Integer> integerResultSupport = testUserService.addTestUser("test" + System.currentTimeMillis(), "imgTitle" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyTestUser() {
        ResultSupport<Integer> resultSupport = testUserService.addTestUser("test" + System.currentTimeMillis(), "imgTitle" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        TestUserDto dto = new TestUserDto("title_modify_single", "imgTitle_modify_single");
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = testUserService.modifyTestUser(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = testUserService.batchModifyTestUser(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(testUserService.getTestUserListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveTestUser() {
        ResultSupport<Integer> resultSupport = testUserService.addTestUser("wait_delete" + System.currentTimeMillis(), "wait_delete" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = testUserService.removeTestUser(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = testUserService.batchRemoveTestUser(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(testUserService.getTestUserListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetTestUserById() {
        String comic = JSON.toJSONString(testUserService.getTestUserById(1));
        logger.info(comic);
    }

    @Test
    public void testGetTestUserListByIdList() {
        String comicList = JSON.toJSONString(testUserService.getTestUserListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetTestUserListByQuery() {
        TestUserDto dto = new TestUserDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<TestUserDto>> testUserListByQuery = testUserService.getTestUserListByQuery(dto);
        logger.info(JSON.toJSONString(testUserListByQuery));
        dto.setCurrentPage(2);
        testUserListByQuery = testUserService.getTestUserListByQuery(dto);
        logger.info(JSON.toJSONString(testUserListByQuery));
        dto.setCurrentPage(3);
        testUserListByQuery = testUserService.getTestUserListByQuery(dto);
        logger.info(JSON.toJSONString(testUserListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    