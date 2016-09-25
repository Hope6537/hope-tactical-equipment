
package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.MessageDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface MessageDao {

    int insertMessage(MessageDo messageDo);

    int updateMessage(MessageDo messageDo);

    int batchUpdateMessage(@Param("data") MessageDo messageDo, @Param("idList") List<Integer> idList);

    int deleteMessage(@Param("id") Integer id);

    int batchDeleteMessage(@Param("idList") List<Integer> idList);

    MessageDo selectMessageById(@Param("id") Integer id);

    List<MessageDo> selectMessageListByIds(@Param("idList") List<Integer> idList);

    List<MessageDo> selectMessageListByNoticeIds(@Param("idList") List<Integer> idList);

    List<MessageDo> selectMessageListByClassesIds(@Param("idList") List<Integer> idList);

    List<MessageDo> selectMessageListByQuery(MessageDo query);

    int selectMessageCountByQuery(MessageDo query);

}

    