
    package org.hope6537.dao;

    import org.hope6537.annotation.MybatisRepository;
    import org.hope6537.dataobject.FeedbackDo;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface FeedbackDao {

        int insertFeedback(FeedbackDo feedbackDo);

        int updateFeedback(FeedbackDo feedbackDo);

        int batchUpdateFeedback(@Param("data") FeedbackDo feedbackDo, @Param("idList") List<Integer> idList);

        int deleteFeedback(@Param("id") Integer id);

        int batchDeleteFeedback(@Param("idList") List<Integer> idList);

        FeedbackDo selectFeedbackById(@Param("id") Integer id);

        List<FeedbackDo> selectFeedbackListByIds(@Param("idList") List<Integer> idList);

        List<FeedbackDo> selectFeedbackListByQuery(FeedbackDo query);

        int selectFeedbackCountByQuery(FeedbackDo query);

    }

    