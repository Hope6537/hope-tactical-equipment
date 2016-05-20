import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.ParentDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.ParentService;
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
public class ParentServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ParentService parentService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(parentService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = parentService.addParent("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddParent() {
        ResultSupport<Integer> integerResultSupport = parentService.addParent("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyParent() {
        ResultSupport<Integer> resultSupport = parentService.addParent("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis(), "test5" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ParentDto dto = new ParentDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), "modify2" + System.currentTimeMillis(), "modify3" + System.currentTimeMillis(), "modify4" + System.currentTimeMillis(), "modify5" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = parentService.modifyParent(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = parentService.batchModifyParent(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(parentService.getParentListByIdList(idList)));
    }

    @Test
    public void testRemoveParent() {
        ResultSupport<Integer> resultSupport = parentService.addParent("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), "wait_delete2" + System.currentTimeMillis(), "wait_delete3" + System.currentTimeMillis(), "wait_delete4" + System.currentTimeMillis(), "wait_delete5" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = parentService.removeParent(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = parentService.batchRemoveParent(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(parentService.getParentListByIdList(idList)));
    }


    @Test
    public void testGetParentById() {
        ResultSupport<ParentDto> result = parentService.getParentById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetParentListByIdList() {
        ResultSupport<List<ParentDto>> result = parentService.getParentListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetParentListByQuery() {
        ParentDto dto = new ParentDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<ParentDto>> parentListByQuery = parentService.getParentListByQuery(dto);
        logger.info(JSON.toJSONString(parentListByQuery));
        dto.setCurrentPage(2);
        parentListByQuery = parentService.getParentListByQuery(dto);
        logger.info(JSON.toJSONString(parentListByQuery));
        dto.setCurrentPage(3);
        parentListByQuery = parentService.getParentListByQuery(dto);
        logger.info(JSON.toJSONString(parentListByQuery));
    }


}

    