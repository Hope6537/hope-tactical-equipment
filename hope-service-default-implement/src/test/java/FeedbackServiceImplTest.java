import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.FeedbackDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.FeedbackService;
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
public class FeedbackServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FeedbackService feedbackService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(feedbackService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = feedbackService.addFeedback("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, "test3" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddFeedback() {
        ResultSupport<Integer> integerResultSupport = feedbackService.addFeedback("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, "test3" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyFeedback() {
        ResultSupport<Integer> resultSupport = feedbackService.addFeedback("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92, "test3" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        FeedbackDto dto = new FeedbackDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), 82, "modify3" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = feedbackService.modifyFeedback(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = feedbackService.batchModifyFeedback(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(feedbackService.getFeedbackListByIdList(idList)));
    }

    @Test
    public void testRemoveFeedback() {
        ResultSupport<Integer> resultSupport = feedbackService.addFeedback("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), 72, "wait_delete3" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = feedbackService.removeFeedback(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = feedbackService.batchRemoveFeedback(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(feedbackService.getFeedbackListByIdList(idList)));
    }


    @Test
    public void testGetFeedbackById() {
        ResultSupport<FeedbackDto> result = feedbackService.getFeedbackById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetFeedbackListByIdList() {
        ResultSupport<List<FeedbackDto>> result = feedbackService.getFeedbackListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetFeedbackListByQuery() {
        FeedbackDto dto = new FeedbackDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<FeedbackDto>> feedbackListByQuery = feedbackService.getFeedbackListByQuery(dto);
        logger.info(JSON.toJSONString(feedbackListByQuery));
        dto.setCurrentPage(2);
        feedbackListByQuery = feedbackService.getFeedbackListByQuery(dto);
        logger.info(JSON.toJSONString(feedbackListByQuery));
        dto.setCurrentPage(3);
        feedbackListByQuery = feedbackService.getFeedbackListByQuery(dto);
        logger.info(JSON.toJSONString(feedbackListByQuery));
    }


}

    