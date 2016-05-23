import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.RequireDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.RequireService;
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
public class RequireServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RequireService requireService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(requireService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = requireService.addRequire("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, 93, 94, "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis(), 97, "test8" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddRequire() {
        ResultSupport<Integer> integerResultSupport = requireService.addRequire("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, 93, 94, "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis(), 97, "test8" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyRequire() {
        ResultSupport<Integer> resultSupport = requireService.addRequire("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, 93, 94, "test5" + System.currentTimeMillis(), "test6" + System.currentTimeMillis(), 97, "test8" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        RequireDto dto = new RequireDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), 82, 83, 84, "modify5" + System.currentTimeMillis(), "modify6" + System.currentTimeMillis(), 87, "modify8" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = requireService.modifyRequire(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = requireService.batchModifyRequire(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(requireService.getRequireListByIdList(idList)));
    }

    @Test
    public void testRemoveRequire() {
        ResultSupport<Integer> resultSupport = requireService.addRequire("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), 72, 73, 74, "wait_delete5" + System.currentTimeMillis(), "wait_delete6" + System.currentTimeMillis(), 77, "wait_delete8" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = requireService.removeRequire(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = requireService.batchRemoveRequire(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(requireService.getRequireListByIdList(idList)));
    }


    @Test
    public void testGetRequireById() {
        ResultSupport<RequireDto> result = requireService.getRequireById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetRequireListByIdList() {
        ResultSupport<List<RequireDto>> result = requireService.getRequireListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetRequireListByQuery() {
        RequireDto dto = new RequireDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<RequireDto>> requireListByQuery = requireService.getRequireListByQuery(dto);
        logger.info(JSON.toJSONString(requireListByQuery));
        dto.setCurrentPage(2);
        requireListByQuery = requireService.getRequireListByQuery(dto);
        logger.info(JSON.toJSONString(requireListByQuery));
        dto.setCurrentPage(3);
        requireListByQuery = requireService.getRequireListByQuery(dto);
        logger.info(JSON.toJSONString(requireListByQuery));
    }


}

    