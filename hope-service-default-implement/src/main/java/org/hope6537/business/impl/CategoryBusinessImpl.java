package org.hope6537.business.impl;

import org.hope6537.bo.CategoryBusiness;
import org.hope6537.dto.*;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.CategoryService;
import org.hope6537.service.ClassifiedService;
import org.hope6537.service.ComicService;
import org.hope6537.service.SpecialService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dintama on 2016/3/14.
 */
@Service("categoryBusiness")
public class CategoryBusinessImpl implements CategoryBusiness {


    private static final int TYPE_COMIC = 0;
    private static final int TYPE_SPECIAL = 1;

    @Resource(name = "classifiedService")
    private ClassifiedService classifiedService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    private ResultSupport<List<Integer>> getTargetIdListByClassified(Integer classifiedId, Integer targetType){
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setClassifiedId(classifiedId);
        categoryDto.setTargetType(targetType);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(null);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            idList.add(tmp.getTargetId());
        }
        return ResultSupport.getInstance(true, "[targetId查询成功]", idList);
    }


    @Override
    public ResultSupport<CategoryDto> getComicByClassified(CategoryDto categoryDto) {
        ResultSupport<List<ComicDto>> comicListByIdList = null;
        Integer classifiedId = categoryDto.getClassifiedId();
        /*获取Key*/
        ResultSupport<ClassifiedDto> classifiedById = classifiedService.getClassifiedById(classifiedId);
        if(!classifiedById.isSuccess()){
            return classifiedById.castToReturnFailed(CategoryDto.class);
        }
        ResultSupport<List<Integer>> targetByClassified = getTargetIdListByClassified(classifiedId, TYPE_COMIC);
        if(!targetByClassified.isSuccess()){
            return targetByClassified.castToReturnFailed(CategoryDto.class);
        }
        List<Integer> idList = targetByClassified.getModule();
        if(!idList.isEmpty()) {
            comicListByIdList = comicService.getComicListByIdList(idList);
            if (!comicListByIdList.isSuccess()) {
                return comicListByIdList.castToReturnFailed(CategoryDto.class);
            }
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        if(comicListByIdList == null){
            result.put("classified", classifiedById.getModule());
            result.put("comicList", Lists.newArrayList());
        }else{
            result.put("classified", classifiedById.getModule());
            result.put("comicList", comicListByIdList.getModule());
        }
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setRelation(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }


    @Override
    public ResultSupport<CategoryDto> getSpecialByClassified(CategoryDto categoryDto) {
        ResultSupport<List<SpecialDto>> specialListByIdList = null;
        Integer classifiedId = categoryDto.getClassifiedId();
        /*获取Key*/
        ResultSupport<ClassifiedDto> classifiedById = classifiedService.getClassifiedById(classifiedId);
        if(!classifiedById.isSuccess()){
            return classifiedById.castToReturnFailed(CategoryDto.class);
        }
        ResultSupport<List<Integer>> targetByClassified = getTargetIdListByClassified(classifiedId, TYPE_SPECIAL);
        if(!targetByClassified.isSuccess()){
            return targetByClassified.castToReturnFailed(CategoryDto.class);
        }
        List<Integer> idList = targetByClassified.getModule();
        if(!idList.isEmpty()) {
            specialListByIdList = specialService.getSpecialListByIdList(idList);
            if (!specialListByIdList.isSuccess()) {
                return specialListByIdList.castToReturnFailed(CategoryDto.class);
            }
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        if(specialListByIdList == null){
            result.put("classified", classifiedById.getModule());
            result.put("specialList", Lists.newArrayList());
        }else{
            result.put("classified", classifiedById.getModule());
            result.put("specialList", specialListByIdList.getModule());
        }
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setRelation(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }

    private ResultSupport<List<Integer>> getTargetClassifiedIdList(Integer targetId, Integer targetType){
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTargetId(targetId);
        categoryDto.setTargetType(0);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(null);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            idList.add(tmp.getClassifiedId());
        }
        return ResultSupport.getInstance(true, "[classifiedId查询成功]", idList);
    }

    @Override
    public ResultSupport<CategoryDto> getComicClassified(CategoryDto categoryDto) {
        ResultSupport<List<ClassifiedDto>> classifiedListByIdList = null;
        Integer comicId = categoryDto.getTargetId();
        /*获取Key*/
        ResultSupport<ComicDto> comicById = comicService.getComicById(comicId);
        if(!comicById.isSuccess()){
            return comicById.castToReturnFailed(CategoryDto.class);
        }
        ResultSupport<List<Integer>> targetClassifiedIdList = getTargetClassifiedIdList(comicId, TYPE_COMIC);
        if(!targetClassifiedIdList.isSuccess()){
            return targetClassifiedIdList.castToReturnFailed(CategoryDto.class);
        }
        List<Integer> idList = targetClassifiedIdList.getModule();
        if(!idList.isEmpty()) {
            classifiedListByIdList = classifiedService.getClassifiedListByIdList(idList);
            if (!classifiedListByIdList.isSuccess()) {
                return classifiedListByIdList.castToReturnFailed(CategoryDto.class);
            }
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        if(classifiedListByIdList == null){
            result.put("comic", comicById.getModule());
            result.put("classifiedList",Lists.newArrayList());
        }
        else{

            result.put("comic", comicById.getModule());
            result.put("classifiedList",classifiedListByIdList.getModule());
        }
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setRelation(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }

    @Override
    public ResultSupport<CategoryDto> getSpecialClassified(CategoryDto categoryDto) {
        ResultSupport<List<ClassifiedDto>> classifiedListByIdList = null;
        Integer specialId = categoryDto.getTargetId();
        /*获取Key*/
        ResultSupport<SpecialDto> specialById = specialService.getSpecialById(specialId);
        if(!specialById.isSuccess()){
            return specialById.castToReturnFailed(CategoryDto.class);
        }
        ResultSupport<List<Integer>> targetClassifiedIdList = getTargetClassifiedIdList(specialId, TYPE_COMIC);
        if(!targetClassifiedIdList.isSuccess()){
            return targetClassifiedIdList.castToReturnFailed(CategoryDto.class);
        }
        List<Integer> idList = targetClassifiedIdList.getModule();
        if(!idList.isEmpty()) {
            classifiedListByIdList = classifiedService.getClassifiedListByIdList(idList);
            if (!classifiedListByIdList.isSuccess()) {
                return classifiedListByIdList.castToReturnFailed(CategoryDto.class);
            }
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        if(classifiedListByIdList == null){
            result.put("special", specialById.getModule());
            result.put("classifiedList", Lists.newArrayList());
        }else{
            result.put("special", specialById.getModule());
            result.put("classifiedList", classifiedListByIdList.getModule());
        }
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setRelation(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }
}
