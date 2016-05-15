
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.JumpDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.JumpService;
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
public class JumpServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JumpService jumpService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(jumpService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = jumpService.addJump(90,91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddJump() {
        ResultSupport<Integer> integerResultSupport = jumpService.addJump(90,91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyJump() {
        ResultSupport<Integer> resultSupport = jumpService.addJump(90,91);
        Integer id = resultSupport.getModule();
        JumpDto dto = new JumpDto(80,81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = jumpService.modifyJump(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = jumpService.batchModifyJump(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(jumpService.getJumpListByIdList(idList)));
    }

    @Test
    public void testRemoveJump() {
        ResultSupport<Integer> resultSupport = jumpService.addJump(70,71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = jumpService.removeJump(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = jumpService.batchRemoveJump(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(jumpService.getJumpListByIdList(idList)));
    }


    @Test
    public void testGetJumpById() {
        ResultSupport<JumpDto> result = jumpService.getJumpById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetJumpListByIdList() {
        ResultSupport<List<JumpDto>> result = jumpService.getJumpListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetJumpListByQuery() {
        JumpDto dto = new JumpDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<JumpDto>> jumpListByQuery = jumpService.getJumpListByQuery(dto);
        logger.info(JSON.toJSONString(jumpListByQuery));
        dto.setCurrentPage(2);
        jumpListByQuery = jumpService.getJumpListByQuery(dto);
        logger.info(JSON.toJSONString(jumpListByQuery));
        dto.setCurrentPage(3);
        jumpListByQuery = jumpService.getJumpListByQuery(dto);
        logger.info(JSON.toJSONString(jumpListByQuery));
    }


}

    