# encoding:utf-8
import json
import os
# 收集已经完成下载的漫画ID
import time
import comic_hentai_data_source
import oss2

conn = comic_hentai_data_source.get_conn()


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


if not conn:
    print(now() + "数据库连接初始化失败,停止操作")
    exit(0)
else:
    print(now() + "数据库连接初始化成功")


# 收集下载好的数据
def collect_already_download_comic_id():
    print(now() + "开始收集数据")
    comic_id_list = []
    for files in os.listdir("./ComicData"):
        if files.count(".json"):
            comic_id = files.split(".json")[0]
            # 查看下有没有这个文件夹
            if os.path.exists(os.getcwd() + "/ComicData/" + comic_id):
                print(now() + "漫画[" + comic_id + "]被识别")
                comic_id_list.append(comic_id)
            else:
                print(now() + "漫画[" + comic_id + "]的JSON信息无效,删除")
                # 如果没有,说明JSON无效
                os.rename(os.getcwd() + "/ComicData/" + files,
                          os.getcwd() + "/ComicData/" + files.split(".json")[0] + ".disable_json")
    return comic_id_list


# 查询是否已经上传过了
def validate_comic_already_upload(comic_id_list):
    id_list_str = ""
    for comic_id in comic_id_list:
        id_list_str += ("'" + comic_id + "'" + ",")
    id_list_str = id_list_str[0:-1]
    cursor = conn.cursor()
    # 查询是否有已经下载完成的
    find_sql = "SELECT ca.foreignId FROM Capture ca , Comic co where ca.foreignId in (" + id_list_str + ") and ca.comicId = co.id and co.status = 0"
    print(now() + "正在查询重复记录")
    cursor.execute(find_sql)
    values = cursor.fetchall()
    # 梳理数据
    duplicated_id_list = []
    new_comic_id_list = []
    for data in values:
        print(now() + "漫画[" + data[0] + "]已经上传,跳过")
        duplicated_id_list.append(data[0])
    for comic_id in comic_id_list:
        if duplicated_id_list.count(comic_id) != 0:
            continue
        else:
            new_comic_id_list.append(comic_id)
    print(now() + "本次处理发现共有" + str(len(duplicated_id_list)) + "条重复记录")
    print(now() + "本次处理将更新" + str(len(new_comic_id_list)) + "条漫画记录")
    # 进行FK和CK的MAP映射
    id_list_str = ""
    for comic_id in new_comic_id_list:
        id_list_str += ("'" + comic_id + "'" + ",")
    if id_list_str == "":
        print(now() + "没有要更新的数据")
        return
    id_list_str = id_list_str[0:-1]
    new_comic_id_map = {}
    map_sql = "SELECT ca.foreignId,ca.comicId FROM Capture ca , Comic co where ca.foreignId in (" + id_list_str + ") and ca.comicId = co.id and co.status = 1"
    cursor.execute(map_sql)
    values = cursor.fetchall()
    for value in values:
        new_comic_id_map[value[0]] = value[1]
    print(now() + "读取映射关系成功")
    return new_comic_id_map


# 读取每个漫画的json信息从而获取漫画内容的本地路径
def upload_and_update_json_data(comic_id_list):
    auth, service, bucket = init_oss()
    if not auth or not service or not bucket:
        print(now() + "阿里云初始化失败,停止操作")
    local_path = os.getcwd()
    if comic_id_list == None:
        print(now() + "没有要更新的漫画ID")
        return
    for foreign_id in comic_id_list:
        print(now() + "正在读取漫画[" + foreign_id + "]的信息")
        comic_primary_key = comic_id_list[foreign_id]
        upload_count = 0
        upload_urls = []
        with open("./ComicData/" + foreign_id + ".json") as f:
            img_list = json.loads(f.read(-1))
            img_count = len(img_list)
        print(now() + "读取漫画[" + foreign_id + "]共" + str(img_count) + "张记录")
        # 这里的顺序有些不对
        # for file_name in os.listdir("./ComicData/" + foreign_id + "/"):
        # 应该是按照JSON来
        with open(os.getcwd() + '/ComicData/' + foreign_id + ".json") as f:
            data = json.loads(f.read(-1))
        for file_name in data:
            file_name = str(file_name).split('/')[-1]
            upload_count += 1
            file_path = local_path + "/ComicData/" + foreign_id + "/" + file_name
            # 真正上传的步骤
            print(now() + "上传第" + str(upload_count) + "张图片中,\n" + now() + "本地地址为:" + file_path +
                  "\n" + now() + "远程地址为:http://comic-hentai.oss-cn-qingdao.aliyuncs.com/" + foreign_id + "/" + file_name)
            upload_comic_file(bucket, file_path, foreign_id + "/" + file_name)
            print(now() + "第" + str(upload_count) + "张图片上传成功")
            # 需要异常捕获,谁知道OSS会出现什么问题
            upload_urls.append("http://comic-hentai.oss-cn-qingdao.aliyuncs.com/" + foreign_id + "/" + file_name)
        print(now() + "漫画[" + foreign_id + "]完成上传,JSON标识" + str(img_count) + "份图片,实际上传" + str(upload_count) + "份")
        update_comic_data(upload_urls, comic_primary_key, foreign_id)
    print(now() + "全部上传完成")


# 初始化阿里云OSS
def init_oss():
    try:
        access_key_id = "------------------"
        access_key_secret = "------------------"
        auth = oss2.Auth(access_key_id, access_key_secret)
        service = oss2.Service(auth, 'http://oss-cn-qingdao.aliyuncs.com', connect_timeout=30)
        bucket = oss2.Bucket(auth, 'http://oss-cn-qingdao.aliyuncs.com', 'comic-hentai')
    except Exception:
        print(now() + "阿里云OSS初始化失败")
        return
    print(now() + "阿里云OSS初始化成功")
    return auth, service, bucket


# 通过本地文件信息,上传到OSS上
def upload_comic_file(bucket, local_file_path, remote_url):
    bucket.put_object_from_file(remote_url, local_file_path)


# 更新数据库里的信息
def update_comic_data(upload_urls, comic_primary_key, foreign_id):
    data = json.dumps({"0": upload_urls})
    update_sql = "UPDATE Comic SET contentTitle = '" + data + "' , updated = UNIX_TIMESTAMP(),status = 0 where id = " + str(
            comic_primary_key)
    cursor = conn.cursor()
    cursor.execute(update_sql)
    conn.commit()
    print(now() + "更新漫画[" + foreign_id + "]信息完成")


def driver():
    upload_and_update_json_data(validate_comic_already_upload(collect_already_download_comic_id()))
