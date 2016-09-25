package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.NoticeDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface NoticeDao {

    int insertNotice(NoticeDo noticeDo);

    int updateNotice(NoticeDo noticeDo);

    int batchUpdateNotice(@Param("data") NoticeDo noticeDo, @Param("idList") List<Integer> idList);

    int deleteNotice(@Param("id") Integer id);

    int batchDeleteNotice(@Param("idList") List<Integer> idList);

    NoticeDo selectNoticeById(@Param("id") Integer id);

    List<NoticeDo> selectNoticeListByIds(@Param("idList") List<Integer> idList);

    List<NoticeDo> selectNoticeListByQuery(NoticeDo query);

    int selectNoticeCountByQuery(NoticeDo query);

}

    