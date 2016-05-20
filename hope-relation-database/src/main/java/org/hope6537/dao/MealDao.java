
package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.MealDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface MealDao {

    int insertMeal(MealDo mealDo);

    int updateMeal(MealDo mealDo);

    int batchUpdateMeal(@Param("data") MealDo mealDo, @Param("idList") List<Integer> idList);

    int deleteMeal(@Param("id") Integer id);

    int batchDeleteMeal(@Param("idList") List<Integer> idList);

    MealDo selectMealById(@Param("id") Integer id);

    List<MealDo> selectMealListByIds(@Param("idList") List<Integer> idList);

    List<MealDo> selectMealListByQuery(MealDo query);

    int selectMealCountByQuery(MealDo query);

}

    