package org.hope6537.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.hope6537.page.PageDto;
import org.hope6537.security.AESLocker;

/**
 * 页面处理
 * <p>
 * 场景:<br/>
 * 客户端自己生成PageMap = {currentPageNo : 1 , pageSize : 20} 和跳页
 * 1.用户浏览刚刚进入浏览页,准备浏览第一页,此时没有入参pageMap,服务端将第一页请求处理完成后,加入一个pageMap字符串,内容为(2,y),意义为第二页,一页y个<br/> sendNextPage
 * 2.用户浏览第N页,此时入参有pageMap,服务端解码后按照当前pageMap所记录的页数进行查询,查询完成后,将页码+1,加密后发送 内容为(n+1,y)<br/> sendNextPage
 * 蛤,如果你问超页了怎么办?
 * 1.currentPageNo数字是在客户端进行填充的,只会显示计算的数量,当超过数量不会提交请求
 * 2.如果强行用http提交了请求怎么办?凉拌,返回的是data:[]
 * </p>
 */
public class PageMapUtil {

    public final static Integer MAX_PAGE_SIZE = 100;
    public final static Integer MIN_PAGE_SIZE = 1;
    public final static Integer MIN_PAGE_NUMBER = 1;

    /**
     * 直接在页码加一,默认的返回策略
     *
     * @param pageMap 页码字符串
     * @return 返回已经喜加一的页码字符串
     */
    public static String sendNextPage(String pageMap) {
        PageDto pageDto;
        if (pageMap == null) {
            pageDto = new PageDto();
        } else {
            pageDto = JSON.parseObject(AESLocker.decrypt(pageMap), PageDto.class);
        }
        return sendNextPage(pageDto);
    }

    public static <T extends PageDto> String sendNextPage(T pageDto) {
        Integer nextPage;
        Integer currentPage = pageDto.getCurrentPage();
        if (currentPage == null || currentPage < MIN_PAGE_NUMBER) {
            nextPage = MIN_PAGE_NUMBER;
        } else {
            nextPage = currentPage + 1;
        }
        pageDto.setCurrentPage(nextPage);
        return AESLocker.encrypt(JSON.toJSONString((PageDto) pageDto));
    }

    /**
     * 直接生成基础查询对象
     *
     * @param pageMap 页码字符串
     * @param clz     查询类
     * @param <T>     泛型
     * @return
     */
    public static <T extends PageDto> T getQuery(String pageMap, Class<T> clz) {
        try {
            T query;
            if (pageMap == null || pageMap.equals("")) {
                query = clz.newInstance();
                query.setCurrentPage(MIN_PAGE_NUMBER);
            } else {
                query = JSON.parseObject(AESLocker.decrypt(pageMap), clz);
            }
            return query;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("查询失败");
        } catch (JSONException e) {
            throw new RuntimeException("页码非法");
        }
    }


}
