import com.alibaba.fastjson.JSON;
import org.hope6537.dto.ComicDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.ComicService;
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
public class ComicServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ComicService comicService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(comicService.toString());
    }

    @Test
    public void testAddComic() {
        ResultSupport<Integer> integerResultSupport = comicService.addComic("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyComic() {
        ResultSupport<Integer> resultSupport = comicService.addComic("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis(), "test4" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ComicDto dto = new ComicDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), "modify2" + System.currentTimeMillis(), "modify3" + System.currentTimeMillis(), "modify4" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = comicService.modifyComic(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = comicService.batchModifyComic(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(comicService.getComicListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveComic() {
        ResultSupport<Integer> resultSupport = comicService.addComic("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), "wait_delete2" + System.currentTimeMillis(), "wait_delete3" + System.currentTimeMillis(), "wait_delete4" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = comicService.removeComic(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = comicService.batchRemoveComic(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(comicService.getComicListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetComicById() {
        String comic = JSON.toJSONString(comicService.getComicById(1));
        logger.info(comic);
    }

    @Test
    public void testGetComicListByIdList() {
        String comicList = JSON.toJSONString(comicService.getComicListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetComicListByQuery() {
        ComicDto dto = new ComicDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<ComicDto>> comicListByQuery = comicService.getComicListByQuery(dto);
        logger.info(JSON.toJSONString(comicListByQuery));
        dto.setCurrentPage(2);
        comicListByQuery = comicService.getComicListByQuery(dto);
        logger.info(JSON.toJSONString(comicListByQuery));
        dto.setCurrentPage(3);
        comicListByQuery = comicService.getComicListByQuery(dto);
        logger.info(JSON.toJSONString(comicListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    