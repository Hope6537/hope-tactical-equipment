
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

import static org.junit.Assert.assertNotNull;
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

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(userInfoService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = userInfoService.addUserInfo("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),"test3"+System.currentTimeMillis(),"test4"+System.currentTimeMillis(),"test5"+System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddUserInfo() {
        ResultSupport<Integer> integerResultSupport = userInfoService.addUserInfo("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),"test3"+System.currentTimeMillis(),"test4"+System.currentTimeMillis(),"test5"+System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyUserInfo() {
        ResultSupport<Integer> resultSupport = userInfoService.addUserInfo("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis(),"test2"+System.currentTimeMillis(),"test3"+System.currentTimeMillis(),"test4"+System.currentTimeMillis(),"test5"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        UserInfoDto dto = new UserInfoDto("modify0"+System.currentTimeMillis(),"modify1"+System.currentTimeMillis(),"modify2"+System.currentTimeMillis(),"modify3"+System.currentTimeMillis(),"modify4"+System.currentTimeMillis(),"modify5"+System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = userInfoService.modifyUserInfo(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = userInfoService.batchModifyUserInfo(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(userInfoService.getUserInfoListByIdList(idList)));
    }

    @Test
    public void testRemoveUserInfo() {
        ResultSupport<Integer> resultSupport = userInfoService.addUserInfo("wait_delete0"+System.currentTimeMillis(),"wait_delete1"+System.currentTimeMillis(),"wait_delete2"+System.currentTimeMillis(),"wait_delete3"+System.currentTimeMillis(),"wait_delete4"+System.currentTimeMillis(),"wait_delete5"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = userInfoService.removeUserInfo(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = userInfoService.batchRemoveUserInfo(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(userInfoService.getUserInfoListByIdList(idList)));
    }


    @Test
    public void testGetUserInfoById() {
        ResultSupport<UserInfoDto> result = userInfoService.getUserInfoById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetUserInfoListByIdList() {
        ResultSupport<List<UserInfoDto>> result = userInfoService.getUserInfoListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
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


}

    