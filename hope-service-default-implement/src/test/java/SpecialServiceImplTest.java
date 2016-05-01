import com.alibaba.fastjson.JSON;
import org.hope6537.dto.SpecialDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.SpecialService;
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
public class SpecialServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SpecialService specialService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(specialService.toString());
    }

    @Test
    public void testAddSpecial() {
        ResultSupport<Integer> integerResultSupport = specialService.addSpecial("test0" + System.currentTimeMillis(), 91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifySpecial() {
        ResultSupport<Integer> resultSupport = specialService.addSpecial("test0" + System.currentTimeMillis(), 91);
        Integer id = resultSupport.getModule();
        SpecialDto dto = new SpecialDto("modify0" + System.currentTimeMillis(), 81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = specialService.modifySpecial(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = specialService.batchModifySpecial(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(specialService.getSpecialListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveSpecial() {
        ResultSupport<Integer> resultSupport = specialService.addSpecial("wait_delete0" + System.currentTimeMillis(), 71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = specialService.removeSpecial(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = specialService.batchRemoveSpecial(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(specialService.getSpecialListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetSpecialById() {
        String comic = JSON.toJSONString(specialService.getSpecialById(1));
        logger.info(comic);
    }

    @Test
    public void testGetSpecialListByIdList() {
        String comicList = JSON.toJSONString(specialService.getSpecialListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetSpecialListByQuery() {
        SpecialDto dto = new SpecialDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<SpecialDto>> specialListByQuery = specialService.getSpecialListByQuery(dto);
        logger.info(JSON.toJSONString(specialListByQuery));
        dto.setCurrentPage(2);
        specialListByQuery = specialService.getSpecialListByQuery(dto);
        logger.info(JSON.toJSONString(specialListByQuery));
        dto.setCurrentPage(3);
        specialListByQuery = specialService.getSpecialListByQuery(dto);
        logger.info(JSON.toJSONString(specialListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    