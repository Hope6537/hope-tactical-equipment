# encoding:UTF-8
import os


def generate(objectName, columns):
    foreginIdListInterface = ''
    for c in columns:
        if c[1] == 'int' and "Id" in c[0]:
            foreginName = c[0]
            foreginIdListInterface += """
    @Override
    public ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByIdList(List<Integer> idList) {
        boolean flag;
        List<{ObjectName}Dto> result;
        List<{ObjectName}Do> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<{ObjectName}Do> list = {objectName}Dao.select{ObjectName}ListBy""" + (foreginName[0].upper() + foreginName[1:]) + """s(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, {ObjectName}Dto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }
            """

    params = ""
    validation = ''
    nextStep = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
        validation += 'checkNotNull(' + c[0] + ', "[添加失败][当前插入数据字段(' + c[0] + ')为空]");\n'
        nextStep += c[0] + ','

    params = params[0:-1]
    nextStep = nextStep[0:-1]

    text = """
package org.hope6537.service.impl;

import org.hope6537.convert.impl.DozerMappingConverter;
import org.hope6537.dao.{ObjectName}Dao;
import org.hope6537.dataobject.BasicDo;
import org.hope6537.dataobject.{ObjectName}Do;
import org.hope6537.dto.{ObjectName}Dto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.enums.IsDeleted;
import org.hope6537.page.PageDto;
import org.hope6537.service.{ObjectName}Service;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 实体服务实现类
 * Created by hope6537 by Code Generator
 */
@Service(value = "{objectName}Service")
public class {ObjectName}ServiceImpl implements {ObjectName}Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "{objectName}Dao")
    private {ObjectName}Dao {objectName}Dao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ResultSupport<Integer> add{ObjectName}({ObjectName}Dto {objectName}Dto) {
        Integer result;
        Integer id;
        try {
            checkNotNull({objectName}Dto, "[添加失败][当前插入数据实体为空]");
            if ({objectName}Dto.getStatus() == null) {
                {objectName}Dto.setStatus(0);
            }
            {ObjectName}Do {objectName}Do = mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class);
            result = {objectName}Dao.insert{ObjectName}({objectName}Do);
            checkNotNull({objectName}Do.getId(), "[添加失败][数据库没有返回实体ID]");
            id = {objectName}Do.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[添加成功]" : "[添加失败][与预期结果不符][result==1]", id);
    }

    @Override
    public ResultSupport<Integer> add{ObjectName}(""" + params + """) {
        try {
            """ + validation + """
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return this.add{ObjectName}(new {ObjectName}Dto(""" + nextStep + """));
    }

    @Override
    public ResultSupport<Integer> modify{ObjectName}({ObjectName}Dto {objectName}Dto) {
        Integer result;
        try {
            checkNotNull({objectName}Dto, "[单体更新失败][当前入参实体为空]");
            checkNotNull({objectName}Dto.getId(), "[单体更新失败][当前入参实体ID为空,无法更新]");
            result = {objectName}Dao.update{ObjectName}(mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体更新成功]" : "[单体更新失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchModify{ObjectName}({ObjectName}Dto {objectName}Dto, List<Integer> idList) {
        Integer result;
        try {
            checkNotNull({objectName}Dto, "[批量更新失败][当前入参实体为空]");
            checkNotNull(idList, "[批量更新失败][当前入参实体ID为空,无法更新]");
            checkArgument(idList.size() > 0, "[批量更新失败][当前入参实体更新的ID集合大小为0]");
            result = {objectName}Dao.batchUpdate{ObjectName}(mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class), idList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? "[批量更新成功]" : "[批量更新失败][与预期结果不符][result==" + idList.size() + "]", result);
    }

    @Override
    public ResultSupport<Integer> remove{ObjectName}(Integer id) {
        Integer result;
        try {
            checkNotNull(id, "[单体删除失败][当前入参实体为空]");
            {ObjectName}Do readyToDelete = {objectName}Dao.select{ObjectName}ById(id);
            checkNotNull(readyToDelete, "[单体删除失败][ID=" + id + "记录从未存在]");
            checkArgument(readyToDelete.getIsDeleted() != 1, "[单体删除失败][ID=" + id + "记录已经被删除]");
            result = {objectName}Dao.delete{ObjectName}(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体删除成功]" : "[单体删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchRemove{ObjectName}(List<Integer> idList) {
        Integer result; //返回结果
        boolean flag = true;  //是否存在无效数据
        String alreadyBeenDeletedIdListString; //打印的ID
        try {
            checkNotNull(idList, "[批量删除失败][当前入参实体为空]");
            checkArgument(idList.size() > 0, "[批量删除失败][当前入参实体更新的ID集合大小为0]");

            List<{ObjectName}Do> readyToDelete = {objectName}Dao.select{ObjectName}ListByIds(idList);

            checkNotNull(readyToDelete, "[批量删除失败][所有记录从未存在]");
            checkArgument(readyToDelete.size() > 0, "[[批量删除失败][所有记录从未存在]");

            //已经处于删除状态的ID
            List<Integer> alreadyBeenDeleted = readyToDelete.stream().filter(r -> r.getIsDeleted() == 1).map(BasicDo::getId).collect(Collectors.toList());
            if (alreadyBeenDeleted == null) {
                alreadyBeenDeleted = Lists.newArrayList();
            }
            checkArgument(alreadyBeenDeleted.size() != readyToDelete.size(), "[批量删除失败][所有记录均已被删除]");
            if (alreadyBeenDeleted.size() > 0) {
                flag = false;
            }
            alreadyBeenDeletedIdListString = alreadyBeenDeleted.toString();
            //求出还未被删除的
            final List<Integer> finalAlreadyBeenDeleted = alreadyBeenDeleted;
            idList = idList.stream().filter(id -> !finalAlreadyBeenDeleted.contains(id)).collect(Collectors.toList());
            result = {objectName}Dao.batchDelete{ObjectName}(idList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? flag ? "[批量删除成功]" : "[批量删除成功][存在不符合条件的数据][idList=" + alreadyBeenDeletedIdListString + "]" : "[批量删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<{ObjectName}Dto> get{ObjectName}ById(Integer id) {
        {ObjectName}Dto result;
        try {
            checkNotNull(id, "[单体查询失败][当前入参实体为空]");
            {ObjectName}Do comicDo = {objectName}Dao.select{ObjectName}ById(id);
            result = mappingConverter.doMap(comicDo, {ObjectName}Dto.class);
            //判断单条数据是否合法
            checkNotNull(result, "[单体查询失败][查询无结果]");
            checkNotNull(result.getId(), "[单体查询失败][查询结果无主键]");
            checkNotNull(result.getStatus(), "[单体查询失败][查询结果无状态]");
            checkNotNull(result.getIsDeleted(), "[单体查询失败][查询结果无是否删除标记]");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(true, "[单体查询成功]", result);
    }

    @Override
    public ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByIdList(List<Integer> idList) {
        boolean flag;
        List<{ObjectName}Dto> result;
        List<{ObjectName}Do> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<{ObjectName}Do> list = {objectName}Dao.select{ObjectName}ListByIds(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, {ObjectName}Dto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }

    @Override
    public ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByQuery({ObjectName}Dto query) {
        List<{ObjectName}Dto> result;
        Integer countByQuery;
        try {
            checkNotNull(query, "[条件查询失败][查询对象为空]");
            if (query.getCurrentPage() == null) {
                query.setCurrentPage(1);
            }
            if (query.getPageSize() == null || query.getPageSize() > 500) {
                query.setPageSize(PageDto.DEFAULT_PAGESIZE);
            }
            if (query.getIsDeleted() == null) {
                query.setIsDeleted(IsDeleted.NO);
            }
            {ObjectName}Do doQuery = mappingConverter.doMap(query, {ObjectName}Do.class);
            countByQuery = {objectName}Dao.select{ObjectName}CountByQuery(doQuery);
            result = {objectName}Dao.select{ObjectName}ListByQuery(doQuery).stream().map(o -> mappingConverter.doMap(o, {ObjectName}Dto.class)).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = countByQuery >= 0 && result != null;
        ResultSupport<List<{ObjectName}Dto>> instance = ResultSupport.getInstance(expr, expr ? "[条件查询成功]" : "[条件查询失败][查询无数据,请变更查询条件]", result);
        instance.setTotalCount(countByQuery);
        return instance;
    }
}

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./impl/" + objectName + "ServiceImpl.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName
