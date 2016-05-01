package org.hope6537.bo;

import org.hope6537.dto.CategoryDto;
import org.hope6537.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/14.
 */
public interface CategoryBusiness {


    /**
     * 通过分类获取漫画。
     *
     *@param categoryDto query-分类ID
     *@return ResultSupport.module = Map<分类，漫画>
     * */
    ResultSupport<CategoryDto> getComicByClassified(CategoryDto categoryDto);


    /**
     * 通过分类获取专辑。
     *
     * @param categoryDto query-分类ID
     * @return ResultSupport.module = Map<分类，专辑>
     * */
    ResultSupport<CategoryDto> getSpecialByClassified(CategoryDto categoryDto);


    /**
     * 获取漫画详细信息
     *
     * @param categoryDto query-漫画ID
     * @return ResultSupport.module = 漫画详细信息
     * */
    ResultSupport<CategoryDto> getComicClassified(CategoryDto categoryDto);


    /**
     * 获取专辑分类信息
     *
     * @param categoryDto query-专辑ID
     * @return ResultSupport.module = 专辑分类详细信息
     * */
    ResultSupport<CategoryDto> getSpecialClassified(CategoryDto categoryDto);


}

