
    package org.hope6537.dao;

    import org.hope6537.annotation.MybatisRepository;
    import org.hope6537.dataobject.ParentDo;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface ParentDao {

        int insertParent(ParentDo parentDo);

        int updateParent(ParentDo parentDo);

        int batchUpdateParent(@Param("data") ParentDo parentDo, @Param("idList") List<Integer> idList);

        int deleteParent(@Param("id") Integer id);

        int batchDeleteParent(@Param("idList") List<Integer> idList);

        ParentDo selectParentById(@Param("id") Integer id);

        List<ParentDo> selectParentListByIds(@Param("idList") List<Integer> idList);

        List<ParentDo> selectParentListByQuery(ParentDo query);

        int selectParentCountByQuery(ParentDo query);

    }

    