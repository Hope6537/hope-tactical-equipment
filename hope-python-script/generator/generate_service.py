# encoding:UTF-8
import os


def generate(objectName, columns):
    """
    生成服务接口
    """
    params = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
    params = params[0:-1]
    text = """
package com.comichentai.service;

import com.comichentai.dto.{ObjectName}Dto;
import com.comichentai.entity.ResultSupport;

import java.util.List;

/**
 * 实体服务接口
 * Created by hope6537 on 16/1/30.
 */
public interface {ObjectName}Service {

    /**
     * 标准模板生成-向数据库添加单行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @return ResultSupport.getData = 新添加的数据的ID
     */
    ResultSupport<Integer> add{ObjectName}({ObjectName}Dto {objectName}Dto);

    /**
     * 标准模板生成-向数据库添加单行记录 参数集合
     * @param 数据字段集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> add{ObjectName}(""" + params + """);

    /**
     * 标准模板生成-向数据库更新单行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> modify{ObjectName}({ObjectName}Dto {objectName}Dto);

    /**
     * 标准模板生成-向数据库更新多行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @param idList       要更新的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchModify{ObjectName}({ObjectName}Dto {objectName}Dto, List<Integer> idList);

    /**
     * 标准模板生成-向数据库删除单行记录
     *
     * @param id 要删除的id
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> remove{ObjectName}(Integer id);

    /**
     * 标准模板生成-向数据库删除多行记录
     *
     * @param idList 要删除的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchRemove{ObjectName}(List<Integer> idList);

    /**
     * 标准模板生成-根据ID查询单个数据
     *
     * @param id 要查询的id
     * @return ResultSupport.getData = 单条数据
     */
    ResultSupport<{ObjectName}Dto> get{ObjectName}ById(Integer id);

    /**
     * 标准模板生成-根据IDList查询多个数据
     *
     * @param idList 要查询的ID集合
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByIdList(List<Integer> idList);

    /**
     * 标准模板生成-根据Query对象查询符合条件的数据
     *
     * @param query 数据查询对象
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByQuery({ObjectName}Dto query);
}
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./service/" + objectName + "Service.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName
