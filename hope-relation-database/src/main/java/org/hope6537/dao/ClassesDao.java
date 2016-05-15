
    package org.hope6537.dao;

    import org.hope6537.annotation.MybatisRepository;
    import org.hope6537.dataobject.ClassesDo;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface ClassesDao {

        int insertClasses(ClassesDo classesDo);

        int updateClasses(ClassesDo classesDo);

        int batchUpdateClasses(@Param("data") ClassesDo classesDo, @Param("idList") List<Integer> idList);

        int deleteClasses(@Param("id") Integer id);

        int batchDeleteClasses(@Param("idList") List<Integer> idList);

        ClassesDo selectClassesById(@Param("id") Integer id);

        List<ClassesDo> selectClassesListByIds(@Param("idList") List<Integer> idList);

        List<ClassesDo> selectClassesListByQuery(ClassesDo query);

        int selectClassesCountByQuery(ClassesDo query);

    }

    