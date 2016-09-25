package org.hope6537.service.impl;

import com.google.common.collect.Lists;
import org.hope6537.convert.impl.DozerMappingConverter;
import org.hope6537.dao.TeacherDao;
import org.hope6537.dataobject.BasicDo;
import org.hope6537.dataobject.TeacherDo;
import org.hope6537.dto.TeacherDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.enums.IsDeleted;
import org.hope6537.generator.data.ChineseNameGenerator;
import org.hope6537.generator.data.EmailGenerator;
import org.hope6537.generator.data.SexGenator;
import org.hope6537.generator.data.TelGenerator;
import org.hope6537.page.PageDto;
import org.hope6537.service.TeacherService;
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
@Service(value = "teacherService")
public class TeacherServiceImpl implements TeacherService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "teacherDao")
    private TeacherDao teacherDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ResultSupport<List<Integer>> generatorTeachers(int count) {
        List<Integer> list = Lists.newArrayList();
        if (count > 30 || count < 0) {
            count = 30;
        }
        while (count-- != 0) {
            ResultSupport<Integer> result = this.addTeacher(ChineseNameGenerator.generateChineseName(), SexGenator.generator(), TelGenerator.generateTel(), "", "0", EmailGenerator.generator(), "acde3e5760f9e9156fcb2ef62f190c658eba6ab1e555e1094c49d8a2b1cf09b2");
            if (result.isSuccess()) {
                list.add(result.getModule());
            } else {
                continue;
            }
        }
        boolean expr = list.size() > 0;
        return ResultSupport.getInstance(expr, expr ? "教师完成生成" : "教师生成失败", list);
    }

    @Override
    public ResultSupport<Integer> addTeacher(TeacherDto teacherDto) {
        Integer result;
        Integer id;
        try {
            checkNotNull(teacherDto, "[添加失败][当前插入数据实体为空]");
            if (teacherDto.getStatus() == null) {
                teacherDto.setStatus(0);
            }
            TeacherDo teacherDo = mappingConverter.doMap(teacherDto, TeacherDo.class);
            result = teacherDao.insertTeacher(teacherDo);
            checkNotNull(teacherDo.getId(), "[添加失败][数据库没有返回实体ID]");
            id = teacherDo.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[添加成功]" : "[添加失败][与预期结果不符][result==1]", id);
    }

    @Override
    public ResultSupport<Integer> addTeacher(String name, String sex, String tel, String des, String level, String email, String password) {
        try {
            checkNotNull(name, "[添加失败][当前插入数据字段(name)为空]");
            checkNotNull(sex, "[添加失败][当前插入数据字段(sex)为空]");
            checkNotNull(tel, "[添加失败][当前插入数据字段(tel)为空]");
            checkNotNull(des, "[添加失败][当前插入数据字段(des)为空]");
            checkNotNull(level, "[添加失败][当前插入数据字段(level)为空]");
            checkNotNull(email, "[添加失败][当前插入数据字段(email)为空]");
            checkNotNull(password, "[添加失败][当前插入数据字段(password)为空]");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return this.addTeacher(new TeacherDto(name, sex, tel, des, level, email, password));
    }

    @Override
    public ResultSupport<Integer> modifyTeacher(TeacherDto teacherDto) {
        Integer result;
        try {
            checkNotNull(teacherDto, "[单体更新失败][当前入参实体为空]");
            checkNotNull(teacherDto.getId(), "[单体更新失败][当前入参实体ID为空,无法更新]");
            result = teacherDao.updateTeacher(mappingConverter.doMap(teacherDto, TeacherDo.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体更新成功]" : "[单体更新失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchModifyTeacher(TeacherDto teacherDto, List<Integer> idList) {
        Integer result;
        try {
            checkNotNull(teacherDto, "[批量更新失败][当前入参实体为空]");
            checkNotNull(idList, "[批量更新失败][当前入参实体ID为空,无法更新]");
            checkArgument(idList.size() > 0, "[批量更新失败][当前入参实体更新的ID集合大小为0]");
            result = teacherDao.batchUpdateTeacher(mappingConverter.doMap(teacherDto, TeacherDo.class), idList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? "[批量更新成功]" : "[批量更新失败][与预期结果不符][result==" + idList.size() + "]", result);
    }

    @Override
    public ResultSupport<Integer> removeTeacher(Integer id) {
        Integer result;
        try {
            checkNotNull(id, "[单体删除失败][当前入参实体为空]");
            TeacherDo readyToDelete = teacherDao.selectTeacherById(id);
            checkNotNull(readyToDelete, "[单体删除失败][ID=" + id + "记录从未存在]");
            checkArgument(readyToDelete.getIsDeleted() != 1, "[单体删除失败][ID=" + id + "记录已经被删除]");
            result = teacherDao.deleteTeacher(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体删除成功]" : "[单体删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchRemoveTeacher(List<Integer> idList) {
        Integer result; //返回结果
        boolean flag = true;  //是否存在无效数据
        String alreadyBeenDeletedIdListString; //打印的ID
        try {
            checkNotNull(idList, "[批量删除失败][当前入参实体为空]");
            checkArgument(idList.size() > 0, "[批量删除失败][当前入参实体更新的ID集合大小为0]");

            List<TeacherDo> readyToDelete = teacherDao.selectTeacherListByIds(idList);

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
            result = teacherDao.batchDeleteTeacher(idList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? flag ? "[批量删除成功]" : "[批量删除成功][存在不符合条件的数据][idList=" + alreadyBeenDeletedIdListString + "]" : "[批量删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<TeacherDto> getTeacherById(Integer id) {
        TeacherDto result;
        try {
            checkNotNull(id, "[单体查询失败][当前入参实体为空]");
            TeacherDo comicDo = teacherDao.selectTeacherById(id);
            result = mappingConverter.doMap(comicDo, TeacherDto.class);
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
    public ResultSupport<List<TeacherDto>> getTeacherListByIdList(List<Integer> idList) {
        boolean flag;
        List<TeacherDto> result;
        List<TeacherDo> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<TeacherDo> list = teacherDao.selectTeacherListByIds(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, TeacherDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }

    @Override
    public ResultSupport<List<TeacherDto>> getTeacherListByQuery(TeacherDto query) {
        List<TeacherDto> result;
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
            TeacherDo doQuery = mappingConverter.doMap(query, TeacherDo.class);
            countByQuery = teacherDao.selectTeacherCountByQuery(doQuery);
            result = teacherDao.selectTeacherListByQuery(doQuery).stream().map(o -> mappingConverter.doMap(o, TeacherDto.class)).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = countByQuery >= 0 && result != null;
        ResultSupport<List<TeacherDto>> instance = ResultSupport.getInstance(expr, expr ? "[条件查询成功]" : "[条件查询失败][查询无数据,请变更查询条件]", result);
        instance.setTotalCount(countByQuery);
        return instance;
    }
}

    