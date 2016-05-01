# encoding:UTF-8
import json
import sys
import time

import comic_hentai_data_source


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


# 读取当前已经被下载的漫画,并将漫画信息读取和写入数据库
def driver(total_json_path):
    comic_list = read_comic_data_from_json(total_json_path)
    write_comic_data_to_db(comic_list)


def read_comic_data_from_json(total_json_path):
    with open(total_json_path, 'r') as f:
        total_comic = json.loads(f.read(-1))
    print(now() + "当前要向数据库写入的漫画数量为" + str(len(total_comic)))
    return total_comic


# 根据读取的JSONArray对象,将这个对象里面的所有数据写入到数据库中
# 这里面比较重要的一点是如何判断当前漫画已经被写入进数据库
# 通过E-Hentai的ComicId来进行判断是比较好的结果
# 所以需要建立一个逻辑外键映射表

'''
漫画
`title`'漫画名称',
`coverTitle` '封面存储路径',
`author`  '作者',
`introduction`  '漫画简介',
`contentTitle` '漫画内容',

外部信息
`comicId` '漫画ID',
`foreignId`'外部信息ID',
`foreignSite` '外部信息来源',
`foreignLink` '外部链接',
`extra` '其他外部信息 JSON格式',
'''


def write_comic_data_to_db(comic_list):
    foreign_id_list = ""
    for comic in comic_list:
        foreign_id_list += "'" + comic['comicId'] + "'" + ","
    foreign_id_list = foreign_id_list[0:-1]
    find_sql = "select foreignId from Capture where foreignId in (" + foreign_id_list + ")"
    conn = comic_hentai_data_source.get_conn()
    # 第一步,查询数据是否有重复的
    cursor = conn.cursor()
    cursor.execute(find_sql)
    # 获取到的数据,就是重复的,排除
    values = cursor.fetchall()
    # 刷洗数据
    comic_list = flush_comic_data(comic_list, values)
    print(now() + "经过数据去重后,要插入的漫画数量为" + str(len(comic_list)))
    # 完成刷洗后开始写入
    for comic in comic_list:
        comic_id = insert_comic_data(comic, conn)
        # 然后插入capture表
        insert_capture_data(comic, comic_id, conn)
    print(now() + "完成写入")
    conn.close()


# 去掉重复的comic信息
def flush_comic_data(comic_list, duplicated_list):
    duplicated_id_list = []
    new_comic_list = []
    for data in duplicated_list:
        duplicated_id_list.append(data[0])
    for comic in comic_list:
        if duplicated_id_list.count(comic['comicId']) != 0:
            continue
        else:
            new_comic_list.append(comic)
    return new_comic_list


# 插入漫画数据
def insert_comic_data(comic, conn):
    title = "'" + comic['comicTitle'].replace("'", "\\'") + "'"
    coverTitle = "'" + comic['comicCover'] + "'"
    author = "'" + "e-hentai" + "'"
    introduction = "'" + comic['comicTitle'].replace("'", "\\'") + "'"
    contentTitle = "'" + "{}" + "'"
    status = "'1'"
    insert_comic_sql = "INSERT INTO `Comic` (`title`,`coverTitle`,`author`,`introduction`,`contentTitle`, `status`, `isDeleted`, `created`, `updated`) " \
                       "VALUES (" + title + "," + coverTitle + "," + author + "," + introduction + "," + contentTitle + ", " + status + ", 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());"
    cursor = conn.cursor()
    cursor.execute(insert_comic_sql)
    cursor.close()
    conn.commit()
    # 然后查询刚刚插入的主键
    query_id_sql = "SELECT LAST_INSERT_ID()"
    cursor = conn.cursor()
    cursor.execute(query_id_sql)
    comic_id = cursor.fetchall()[0][0]
    print(now() + "插入的漫画主键为[" + str(comic_id) + "]")
    return comic_id


# 插入捕获数据
def insert_capture_data(comic, last_insert_comic_id, conn):
    foreign_id = "'" + comic['comicId'] + "'"
    foreign_site = '\'http://lofi.e-hentai.org\''
    foreign_link = "'" + comic['comicLink'] + "'"
    extra = "'" + json.dumps(comic).replace("'", "\\'") + "'"
    insert_capture_sql = "INSERT INTO `Capture` (`comicId`,`foreignId`,`foreignSite`,`foreignLink`,`extra`,`created`,`updated`,`status`,`isDeleted`) " \
                         "VALUES ('" + str(
            last_insert_comic_id) + "'," + foreign_id + "," + foreign_site + "," + foreign_link + "," + extra + ",UNIX_TIMESTAMP(),UNIX_TIMESTAMP(),0,0);"
    cursor = conn.cursor()
    cursor.execute(insert_capture_sql)
    conn.commit()
    query_id_sql = "SELECT LAST_INSERT_ID()"
    cursor = conn.cursor()
    cursor.execute(query_id_sql)
    capture_id = cursor.fetchall()[0][0]
    print(now() + "插入的捕获信息主键为[" + str(capture_id) + "]")


reload(sys)
sys.setdefaultencoding('utf-8')


def normal():
    driver('./local_total.json')
