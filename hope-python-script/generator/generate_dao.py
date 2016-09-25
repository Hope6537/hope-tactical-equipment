# encoding:UTF-8
import os


def generate(objectName, columns):
    """
    生成Dao接口
    :param objectName:
    :return:
    """

    foreginIdListInterface = ''
    for c in columns:
        if c[1] == 'int' and "Id" in c[0]:
            foreginName = c[0]
            foreginIdListInterface += """
            List<{ObjectName}Do> select{ObjectName}ListBy""" + (foreginName[0].upper() + foreginName[1:]) + """s(@Param("idList") List<Integer> idList);
            """

    text = """
    package org.hope6537.dao;

    import org.hope6537.annotation.MybatisRepository;
    import org.hope6537.dataobject.{ObjectName}Do;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface {ObjectName}Dao {

        int insert{ObjectName}({ObjectName}Do {objectName}Do);

        int update{ObjectName}({ObjectName}Do {objectName}Do);

        int batchUpdate{ObjectName}(@Param("data") {ObjectName}Do {objectName}Do, @Param("idList") List<Integer> idList);

        int delete{ObjectName}(@Param("id") Integer id);

        int batchDelete{ObjectName}(@Param("idList") List<Integer> idList);

        {ObjectName}Do select{ObjectName}ById(@Param("id") Integer id);

        List<{ObjectName}Do> select{ObjectName}ListByIds(@Param("idList") List<Integer> idList);
    """ + foreginIdListInterface + """
        List<{ObjectName}Do> select{ObjectName}ListByQuery({ObjectName}Do query);

        int select{ObjectName}CountByQuery({ObjectName}Do query);

    }

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./dao/" + objectName + "Dao.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/Dao/' + fileName
