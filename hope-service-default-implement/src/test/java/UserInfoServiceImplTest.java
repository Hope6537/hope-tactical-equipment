import com.alibaba.fastjson.JSON;
import org.hope6537.dto.UserInfoDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.UserInfoService;
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
public class UserInfoServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserInfoService userInfoService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(userInfoService.toString());
    }

    @Test
    public void testAddUserInfo() {
        ResultSupport<Integer> integerResultSupport = userInfoService.addUserInfo("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyUserInfo() {
        ResultSupport<Integer> resultSupport = userInfoService.addUserInfo("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        UserInfoDto dto = new UserInfoDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), "modify2" + System.currentTimeMillis(), "modify3" + System.currentTimeMillis(), "modify4" + System.currentTimeMillis(), "modify5" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = userInfoService.modifyUserInfo(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = userInfoService.batchModifyUserInfo(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(userInfoService.getUserInfoListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveUserInfo() {
        ResultSupport<Integer> resultSupport = userInfoService.addUserInfo("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), "wait_delete2" + System.currentTimeMillis(), "wait_delete3" + System.currentTimeMillis(), "wait_delete4" + System.currentTimeMillis(), "wait_delete5" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = userInfoService.removeUserInfo(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = userInfoService.batchRemoveUserInfo(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(userInfoService.getUserInfoListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetUserInfoById() {
        String comic = JSON.toJSONString(userInfoService.getUserInfoById(1));
        logger.info(comic);
    }

    @Test
    public void testGetUserInfoListByIdList() {
        String comicList = JSON.toJSONString(userInfoService.getUserInfoListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetUserInfoListByQuery() {
        UserInfoDto dto = new UserInfoDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<UserInfoDto>> userInfoListByQuery = userInfoService.getUserInfoListByQuery(dto);
        logger.info(JSON.toJSONString(userInfoListByQuery));
        dto.setCurrentPage(2);
        userInfoListByQuery = userInfoService.getUserInfoListByQuery(dto);
        logger.info(JSON.toJSONString(userInfoListByQuery));
        dto.setCurrentPage(3);
        userInfoListByQuery = userInfoService.getUserInfoListByQuery(dto);
        logger.info(JSON.toJSONString(userInfoListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    