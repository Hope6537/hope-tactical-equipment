import com.alibaba.fastjson.JSON;
import org.hope6537.dto.FavoriteDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.FavoriteService;
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
public class FavoriteServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FavoriteService favoriteService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(favoriteService.toString());
    }

    @Test
    public void testAddFavorite() {
        ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(90, 91, 92);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyFavorite() {
        ResultSupport<Integer> resultSupport = favoriteService.addFavorite(90, 91, 92);
        Integer id = resultSupport.getModule();
        FavoriteDto dto = new FavoriteDto(80, 81, 82);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = favoriteService.modifyFavorite(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = favoriteService.batchModifyFavorite(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(favoriteService.getFavoriteListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemoveFavorite() {
        ResultSupport<Integer> resultSupport = favoriteService.addFavorite(70, 71, 72);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = favoriteService.removeFavorite(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = favoriteService.batchRemoveFavorite(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString(favoriteService.getFavoriteListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGetFavoriteById() {
        String comic = JSON.toJSONString(favoriteService.getFavoriteById(1));
        logger.info(comic);
    }

    @Test
    public void testGetFavoriteListByIdList() {
        String comicList = JSON.toJSONString(favoriteService.getFavoriteListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGetFavoriteListByQuery() {
        FavoriteDto dto = new FavoriteDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
        dto.setCurrentPage(2);
        favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
        dto.setCurrentPage(3);
        favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    