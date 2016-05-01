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

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(jumpService.toString());
    }

    @Test
    public void testAddJump() {
        ResultSupport<Integer> integerResultSupport = jumpService.addJump(90, 91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyJump() {
        ResultSupport<Integer> resultSupport = jumpService.addJump(90, 91);
        Integer id = resultSupport.getModule();
        JumpDto dto = new JumpDto(80, 81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = jumpService.modifyJump(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = jumpService.batchModifyJump(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(jumpService.getJumpListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveJump() {
        ResultSupport<Integer> resultSupport = jumpService.addJump(70, 71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = jumpService.removeJump(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = jumpService.batchRemoveJump(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(jumpService.getJumpListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetJumpById() {
        String comic = JSON.toJSONString(jumpService.getJumpById(1));
        logger.info(comic);
    }

    @Test
    public void testGetJumpListByIdList() {
        String comicList = JSON.toJSONString(jumpService.getJumpListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
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

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    