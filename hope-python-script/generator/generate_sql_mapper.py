# encoding:UTF-8
import os


def generate(objectName, columns):
    """
    生成SQL映射文件
    """
    insertColumns = ""
    insertDynamic = ""
    updateColumns = ""
    batchUpdateColumns = ""
    whereColumns = ""
    for c in columns:
        insertColumns += "`" + c[0] + "`,"
        insertDynamic += "#{" + c[0] + "},"
        updateColumns += '<if test="' + c[0] + '!=null and ' + c[0] + '!=\'\'"> `' + c[0] + '` = #{' + c[
            0] + '}, </if>\n\t\t\t'
        batchUpdateColumns += '<if test="data.' + c[0] + '!=null and data.' + c[0] + '!=\'\'"> `' + c[
            0] + '` = #{data.' + c[0] + '}, </if>\n\t\t\t'
        whereColumns += '<if test="' + c[0] + '!=null and ' + c[0] + '!=\'\'"> AND `' + c[0] + '` = #{' + c[
            0] + '} </if>\n\t\t\t'
    text = """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD iBatis Mapper 3.0 //EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.hope6537.dao.{ObjectName}Dao">
    <!-- 默认模板生成 添加单行记录 -->
    <insert id="insert{ObjectName}"> INSERT INTO `{ObjectName}`
        (""" + insertColumns + """
        <!--<if test="可以为空字段!=null and 可以为空字段!=''"> `可以为空字段`, </if>-->
        `status`, `isDeleted`, `created`, `updated`)
        VALUES
        (""" + insertDynamic + """
        <!--<if test="可以为空字段!=null and 可以为空字段!=''"> #{可以为空字段}, </if>-->
        #{status}, 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());
        <selectKey resultType="integer" keyProperty="id">SELECT LAST_INSERT_ID()</selectKey>
    </insert>
    <!-- 默认模板生成 更新单行记录 -->
    <update id="update{ObjectName}"> UPDATE `{ObjectName}`
        <set>
            """ + updateColumns + """
            <if test="status!=null and status!=''"> `status` = #{status}, </if>
            updated = UNIX_TIMESTAMP()
        </set>
        <where> id = #{id} </where>
        LIMIT 1
    </update>
    <!-- 默认模板生成 更新多行记录 -->
    <update id="batchUpdate{ObjectName}"> UPDATE `{ObjectName}`
        <set>
            """ + batchUpdateColumns + """
            <if test="data.status!=null and data.status!=''"> `status` = #{data.status}, </if>
        </set>
        <where>
            <foreach collection="idList" item="id" separator=" or ">id = #{id}</foreach>
        </where>
        LIMIT ${idList.size}
    </update>
    <!-- 默认模板生成 删除单行记录-->
    <update id="delete{ObjectName}"> UPDATE `{ObjectName}` SET isDeleted = 1, updated = UNIX_TIMESTAMP() WHERE id = ${id} LIMIT 1 </update>
    <!-- 默认模板生成 删除多行记录-->
    <update id="batchDelete{ObjectName}"> UPDATE `{ObjectName}` SET isDeleted = 1,updated = UNIX_TIMESTAMP()
        <where>
            <foreach collection="idList" item="id" separator=" or ">id = #{id}</foreach>
        </where>
        LIMIT ${idList.size}
    </update>
    <!-- 默认模板生成 根据ID选取单行记录 -->
    <select id="select{ObjectName}ById" resultType="org.hope6537.dataobject.{ObjectName}Do"> SELECT * FROM `{ObjectName}` WHERE id = ${id} LIMIT 1 </select>
    <!-- 默认模板生成 根据ID集合选取多行记录-->
    <select id="select{ObjectName}ListByIds" resultType="org.hope6537.dataobject.{ObjectName}Do"> SELECT * FROM `{ObjectName}`
        <where>
            id in (
            <foreach collection="idList" item="id" separator=" , ">#{id}</foreach>
            )
        </where>
        LIMIT ${idList.size}
    </select>
    <!-- 默认模板生成 动态SQL语句 通常字段判断是否为空 并增加日期范围 -->
    <sql id="where">
        <where> 1 = 1
            """ + whereColumns + """
            <!--<if test="模糊查询字段!=null and 模糊查询字段!=''"> AND`模糊查询字段` LIKE concat('%',#{模糊查询字段},'%') </if>-->
            <if test="createdAfter!=null and createdAfter!=''"> AND `created` &lt; #{createdAfter} </if>
            <if test="createdBefore!=null and createdBefore!=''">AND `created` &gt; #{createdBefore} </if>
            <if test="updatedAfter!=null and updatedAfter!=''"> AND `updated` &lt; #{updatedAfter} </if>
            <if test="updatedBefore!=null and updatedBefore!=''">AND `updated` &gt; #{updatedBefore} </if>
            <if test="status!=null and status!=''"> AND `status` = #{status} </if>
            <if test="isDeleted!=null and isDeleted!=''"> AND `isDeleted` = #{isDeleted} </if>
        </where>
    </sql>
    <!-- 默认模板生成 根据Query对象查询记录 -->
    <select id="select{ObjectName}ListByQuery" resultType="org.hope6537.dataobject.{ObjectName}Do" parameterType="org.hope6537.dto.{ObjectName}Dto"> SELECT * FROM `{ObjectName}`
        <include refid="where"/>
        ORDER BY `created` DESC
        <if test="limit!=null and limit!=''"> LIMIT
            <if test="offset!=null and offset!=''">#{offset},</if> #{limit} </if>
    </select>
    <!-- 默认模板生成 根据Query对象查询符合条件的个数 -->
    <select id="select{ObjectName}CountByQuery" resultType="Integer" parameterType="org.hope6537.dataobject.{ObjectName}Do"> SELECT count(*) FROM `{ObjectName}`
        <include refid="where"/>
    </select>
</mapper>
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./datamapper/" + objectName.lower() + "-mapper.xml"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName
