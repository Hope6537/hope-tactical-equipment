package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.PublishDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface PublishDao {

    int insertPublish(PublishDo publishDo);

    int updatePublish(PublishDo publishDo);

    int batchUpdatePublish(@Param("data") PublishDo publishDo, @Param("idList") List<Integer> idList);

    int deletePublish(@Param("id") Integer id);

    int batchDeletePublish(@Param("idList") List<Integer> idList);

    PublishDo selectPublishById(@Param("id") Integer id);

    List<PublishDo> selectPublishListByIds(@Param("idList") List<Integer> idList);

    List<PublishDo> selectPublishListByQuery(PublishDo query);

    int selectPublishCountByQuery(PublishDo query);

}

    