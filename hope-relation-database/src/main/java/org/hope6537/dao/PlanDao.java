
    package org.hope6537.dao;

    import org.hope6537.annotation.MybatisRepository;
    import org.hope6537.dataobject.PlanDo;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface PlanDao {

        int insertPlan(PlanDo planDo);

        int updatePlan(PlanDo planDo);

        int batchUpdatePlan(@Param("data") PlanDo planDo, @Param("idList") List<Integer> idList);

        int deletePlan(@Param("id") Integer id);

        int batchDeletePlan(@Param("idList") List<Integer> idList);

        PlanDo selectPlanById(@Param("id") Integer id);

        List<PlanDo> selectPlanListByIds(@Param("idList") List<Integer> idList);

        List<PlanDo> selectPlanListByQuery(PlanDo query);

        int selectPlanCountByQuery(PlanDo query);

    }

    