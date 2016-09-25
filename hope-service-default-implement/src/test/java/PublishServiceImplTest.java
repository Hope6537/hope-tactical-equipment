import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.PublishDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.PublishService;
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
public class PublishServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PublishService publishService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(publishService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = publishService.addPublish(90, 91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddPublish() {
        ResultSupport<Integer> integerResultSupport = publishService.addPublish(90, 91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyPublish() {
        ResultSupport<Integer> resultSupport = publishService.addPublish(90, 91);
        Integer id = resultSupport.getModule();
        PublishDto dto = new PublishDto(80, 81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = publishService.modifyPublish(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = publishService.batchModifyPublish(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(publishService.getPublishListByIdList(idList)));
    }

    @Test
    public void testRemovePublish() {
        ResultSupport<Integer> resultSupport = publishService.addPublish(70, 71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = publishService.removePublish(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = publishService.batchRemovePublish(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(publishService.getPublishListByIdList(idList)));
    }


    @Test
    public void testGetPublishById() {
        ResultSupport<PublishDto> result = publishService.getPublishById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetPublishListByIdList() {
        ResultSupport<List<PublishDto>> result = publishService.getPublishListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetPublishListByQuery() {
        PublishDto dto = new PublishDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<PublishDto>> publishListByQuery = publishService.getPublishListByQuery(dto);
        logger.info(JSON.toJSONString(publishListByQuery));
        dto.setCurrentPage(2);
        publishListByQuery = publishService.getPublishListByQuery(dto);
        logger.info(JSON.toJSONString(publishListByQuery));
        dto.setCurrentPage(3);
        publishListByQuery = publishService.getPublishListByQuery(dto);
        logger.info(JSON.toJSONString(publishListByQuery));
    }


}

    