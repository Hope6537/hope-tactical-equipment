import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.NoticeDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.NoticeService;
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
public class NoticeServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NoticeService noticeService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(noticeService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = noticeService.addNotice("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddNotice() {
        ResultSupport<Integer> integerResultSupport = noticeService.addNotice("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyNotice() {
        ResultSupport<Integer> resultSupport = noticeService.addNotice("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        NoticeDto dto = new NoticeDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = noticeService.modifyNotice(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = noticeService.batchModifyNotice(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(noticeService.getNoticeListByIdList(idList)));
    }

    @Test
    public void testRemoveNotice() {
        ResultSupport<Integer> resultSupport = noticeService.addNotice("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = noticeService.removeNotice(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = noticeService.batchRemoveNotice(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(noticeService.getNoticeListByIdList(idList)));
    }


    @Test
    public void testGetNoticeById() {
        ResultSupport<NoticeDto> result = noticeService.getNoticeById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetNoticeListByIdList() {
        ResultSupport<List<NoticeDto>> result = noticeService.getNoticeListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetNoticeListByQuery() {
        NoticeDto dto = new NoticeDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<NoticeDto>> noticeListByQuery = noticeService.getNoticeListByQuery(dto);
        logger.info(JSON.toJSONString(noticeListByQuery));
        dto.setCurrentPage(2);
        noticeListByQuery = noticeService.getNoticeListByQuery(dto);
        logger.info(JSON.toJSONString(noticeListByQuery));
        dto.setCurrentPage(3);
        noticeListByQuery = noticeService.getNoticeListByQuery(dto);
        logger.info(JSON.toJSONString(noticeListByQuery));
    }


}

    