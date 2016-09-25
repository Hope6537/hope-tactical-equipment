import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.ApplyDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.ApplyService;
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
public class ApplyServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApplyService applyService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(applyService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = applyService.addApply(90, 91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddApply() {
        ResultSupport<Integer> integerResultSupport = applyService.addApply(90, 91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyApply() {
        ResultSupport<Integer> resultSupport = applyService.addApply(90, 91);
        Integer id = resultSupport.getModule();
        ApplyDto dto = new ApplyDto(80, 81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = applyService.modifyApply(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = applyService.batchModifyApply(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(applyService.getApplyListByIdList(idList)));
    }

    @Test
    public void testRemoveApply() {
        ResultSupport<Integer> resultSupport = applyService.addApply(70, 71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = applyService.removeApply(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = applyService.batchRemoveApply(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(applyService.getApplyListByIdList(idList)));
    }


    @Test
    public void testGetApplyById() {
        ResultSupport<ApplyDto> result = applyService.getApplyById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetApplyListByIdList() {
        ResultSupport<List<ApplyDto>> result = applyService.getApplyListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetApplyListByQuery() {
        ApplyDto dto = new ApplyDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<ApplyDto>> applyListByQuery = applyService.getApplyListByQuery(dto);
        logger.info(JSON.toJSONString(applyListByQuery));
        dto.setCurrentPage(2);
        applyListByQuery = applyService.getApplyListByQuery(dto);
        logger.info(JSON.toJSONString(applyListByQuery));
        dto.setCurrentPage(3);
        applyListByQuery = applyService.getApplyListByQuery(dto);
        logger.info(JSON.toJSONString(applyListByQuery));
    }


}

    