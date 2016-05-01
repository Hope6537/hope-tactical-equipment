package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.CategoryDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface CategoryDao {

    int insertCategory(CategoryDo categoryDo);

    int updateCategory(CategoryDo categoryDo);

    int batchUpdateCategory(@Param("data") CategoryDo categoryDo, @Param("idList") List<Integer> idList);

    int deleteCategory(@Param("id") Integer id);

    int batchDeleteCategory(@Param("idList") List<Integer> idList);

    CategoryDo selectCategoryById(@Param("id") Integer id);

    List<CategoryDo> selectCategoryListByIds(@Param("idList") List<Integer> idList);

    List<CategoryDo> selectCategoryListByQuery(CategoryDo query);

    int selectCategoryCountByQuery(CategoryDo query);

}

    