import com.alibaba.fastjson.JSON;
import org.hope6537.dto.JumpDto;
import org.hope6537.dto.TestComicDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.TestComicService;
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
 * Created by hope6537 on 16/2/4.
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class TestComicServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestComicService testComicService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(testComicService.toString());
    }

    @Test
    public void testAddTestComic() {
        ResultSupport<Integer> integerResultSupport = testComicService.addTestComic("test" + System.currentTimeMillis(), "imgTitle" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyTestComic() {
        ResultSupport<Integer> resultSupport = testComicService.addTestComic("test" + System.currentTimeMillis(), "imgTitle" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        TestComicDto dto = new TestComicDto("title_modify_single", "imgTitle_modify_single");
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = testComicService.modifyTestComic(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = testComicService.batchModifyTestComic(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(testComicService.getTestComicListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveTestComic() {
        ResultSupport<Integer> resultSupport = testComicService.addTestComic("wait_delete" + System.currentTimeMillis(), "wait_delete" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = testComicService.removeTestComic(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = testComicService.batchRemoveTestComic(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(testComicService.getTestComicListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetComicById() {
        String comic = JSON.toJSONString(testComicService.getTestComicById(1));
        logger.info(comic);
    }

    @Test
    public void testGetComicListByIdList() {
        String comicList = JSON.toJSONString(testComicService.getTestComicListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetComicListByQuery() {
        TestComicDto dto = new TestComicDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<TestComicDto>> testComicListByQuery = testComicService.getTestComicListByQuery(dto);
        logger.info(JSON.toJSONString(testComicListByQuery));
        dto.setCurrentPage(2);
        testComicListByQuery = testComicService.getTestComicListByQuery(dto);
        logger.info(JSON.toJSONString(testComicListByQuery));
        dto.setCurrentPage(3);
        testComicListByQuery = testComicService.getTestComicListByQuery(dto);
        logger.info(JSON.toJSONString(testComicListByQuery));
    }

    @Test
    public void testCast() throws IOException {
        TestComicDto dto = new TestComicDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<TestComicDto>> testComicListByQuery = testComicService.getTestComicListByQuery(dto);
        ResultSupport<JumpDto> cast = testComicListByQuery.castToReturnFailed(JumpDto.class);
        logger.info(JSON.toJSONString(cast));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}
