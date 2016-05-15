# encoding:UTF-8
# coding=utf8
# 代码自动生成 do-dao-mapper-dto-service-serviceImpl-test一站式服务 我好强啊
# TODO:增加链接数据库读取阶段
# 查询表中所有字段
# select column_name,column_comment,is_nullable from information_schema.columns where table_name = 'TestUser' order by table_schema,table_name
# select distinct table_name from information_schema.columns where table_schema = 'ComicHentai' order by table_schema,table_name

import generate_dao
import generate_data_object
import generate_data_transform_object
import generate_dir
import generate_service
import generate_service_default_implement
import generate_sql_mapper
import generate_test
import generate_service_default_controller
from comic_hentai import comic_hentai_data_source



def initAll(objectName, columns):
    generate_dir.generate()
    print(generate_data_object.generate(objectName, columns))
    print(generate_data_transform_object.generate(objectName, columns))
    print(generate_dao.generate(objectName))
    print(generate_sql_mapper.generate(objectName, columns))
    print(generate_service.generate(objectName, columns))
    print(generate_service_default_implement.generate(objectName, columns))
    print(generate_test.generate(objectName, columns))
    print(generate_service_default_controller.generate(objectName, columns))


def mysql_connect():
    conn = comic_hentai_data_source.get_conn()
    cursor = conn.cursor()
    # 得到当前数据库中的所有表
    cursor.execute(
            "select distinct table_name from information_schema.columns where table_schema = 'ComicHentai' order by table_schema,table_name")
    tables = cursor.fetchall()
    print(tables)
    for table in tables:
        table = table[0]
        if table == 'TestComic' or table == "TestUser":
            continue
        cursor.execute(
                "select column_name,data_type,is_nullable,column_comment from information_schema.columns where table_name = '" + table + "' order by table_schema,table_name", )
        values = cursor.fetchall()
        columns = []
        for column in values:
            if not (column[0] == 'id' or column[0] == 'created' or column[0] == 'updated' or column[0] == 'isDeleted' or
                            column[0] == 'status'):
                columns.append(column)
        print(columns)
        initAll(table, columns)
    cursor.close()
    conn.close()


mysql_connect()
# initAll("TestUser")
