# encoding:UTF-8
import json
import os
import platform
import sys
import time
from Queue import Queue
from threading import Thread
from time import sleep

import requests
# 首先获取漫画的基本信息
from pyquery import PyQuery as pq
from requests.exceptions import ConnectionError, ReadTimeout, TooManyRedirects, RequestException

import init_proxy
import read_comic_to_db

proxy_list = []
url = "http://lofi.e-hentai.org"
headers = {
    'cache-control': "no-cache",
    'postman-token': "5bd3dab9-6df8-e0b8-a2e7-a714c104c74b",
    'cookie': 'xres=3'
}
index = 0


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


def init(proxy, page=1, max_page=20, use_proxy=True, dir_name=None):
    global index, proxy_list
    if dir_name is None:
        # 首先创建一个基于当前时间的文件夹
        dir_name = time.strftime("%Y-%m-%d %H%M%S", time.localtime(int(time.time())))
        # 在Windows环境下 文件夹不允许带冒号
        if platform.system() == "Windows":
            dir_name = time.strftime("%Y-%m-%d %H%M%S", time.localtime(int(time.time())))
        dir_name = "data-" + dir_name
        os.mkdir(dir_name)
    proxies = {
        "http": proxy,
    }
    while page < max_page:
        querystring = {
            "page": page,
            "f_apply": "Search",
            "f_search": "Chinese"
        }
        response = None
        while response is None:
            try:
                if use_proxy:
                    response = requests.request("GET", url, headers=headers, params=querystring, proxies=proxies,
                                                timeout=10)
                else:
                    response = requests.request("GET", url, headers=headers, params=querystring, timeout=10)
                if response.text.count('squid') > 0 or response.text.count("http://warning.or.kr") > 0:
                    print(now() + "当前代理出现异常,切换代理")
                    raise ConnectionError("has been blocked")
                if response.status_code != 200:
                    print(now() + "非正常返回结果,代码[" + str(response.status_code) + "],切换代理")
                    raise ConnectionError("has been blocked")
                if response.text.count(
                        'Your IP address has been temporarily banned for excessive pageloads which indicates that you are using automated mirroring/harvesting software.') > 0:
                    print(now() + "当前代理已被Ban,切换代理")
                    raise ConnectionError("has been blocked")
                print(now() + "入侵成功!当前为第" + str(page) + "页")
            except ConnectionError:
                # 如果代理连不上,换一个
                index += 1
                if index == len(proxy_list):
                    index = 0
                    init_proxy.create_proxy()
                    print(now() + "重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print(
                    now() + "代理[" + proxies.get('http') + "]不可用,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
                proxies = {
                    "http": proxy_list[index]
                }
                continue
            except ReadTimeout:
                # 如果代理连不上,换一个
                index += 1
                if index == len(proxy_list):
                    index = 0
                    init_proxy.create_proxy()
                    print(now() + "重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print(now() + "代理[" + proxies.get('http') + "]超时,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
                proxies = {
                    "http": proxy_list[index]
                }
                continue
        # 获得漫画栏,对漫画栏的每个元素进行迭代 div id = ig
        comic_list = pq(response.text).find('.ig')
        comic_json_list = []
        list_len = 0
        for elements in comic_list:
            comic = read_basic_comic_info(elements)
            # 获取完成基本信息之后,就要遍历漫画的每张图片了
            # imgList = read_basic_comic_info()
            # comic["imgList"] = json.dumps(imgList)
            comic_json_list.append(comic)
            list_len += 1
            print(now() + "读取漫画信息中[" + json.dumps(comic) + "]")

        comic_json_list = json.dumps(comic_json_list)
        print(now() + "读取了 " + str(list_len) + " 个漫画 , 准备写入")
        if list_len == 0:
            print(now() + "读取漫画失败,重新读取")
            continue
        # 最后将当前页的数据写回json
        with open(dir_name + "/" + str(page) + ".json", 'w') as f:
            f.write(comic_json_list)
        page += 1
        print(now() + "数据写入成功,等待睡眠2秒后进行下一页查询")
        time.sleep(2)
    return dir_name


# 生成一个Comic对象
def read_basic_comic_info(element):
    # 链接
    comic_link = pq(element).find("a").eq(0).attr("href")
    # 漫画ID
    comic_id = comic_link.split('/')[5]
    # 漫画标题
    comic_title = pq(element).find("a").eq(1).text()
    # 漫画时间
    comic_date = pq(element).find('td .ik').eq(0).next().text().split("by")[0]
    # 漫画分类
    comic_category = pq(element).find('td .ik').eq(1).next().text()
    # 漫画标签
    comic_tag = pq(element).find('td .ik').eq(2).next().text()
    # 漫画评分
    comic_rank = str(pq(element).find('td .ik').eq(3).next().text().count("*"))
    # 漫画封面
    comic_cover = pq(element).find("a").eq(0).children().attr("src")

    dict = {"comicLink": comic_link, "comicId": comic_id, "comicTitle": comic_title, "comicDate": comic_date,
            "comicCategory": comic_category, "comicTag": comic_tag, "comicRank": comic_rank, "comicCover": comic_cover,
            "imgList": []}

    return dict


def read_comic_img_info(comic_link, headers, proxy, use_proxy=True):
    if not os.path.exists('ComicData') or not os.path.isdir('ComicData'):
        os.mkdir('ComicData')
    global index, proxy_list
    if len(proxy_list) == 0:
        print(now() + '代理列表为空，重新获取')
        init_proxy.create_proxy()
        print(now() + "获取代理成功")
        with open('proxy.json', 'r') as f:
            proxy_list = json.loads(f.read(-1))
    comic_id = comic_link.split('/')[5]
    if os.path.exists('ComicData/' + comic_id + ".json"):
        print(now() + "漫画[" + comic_id + "]的数据已存在")
        return
    print(now() + "正在读取漫画[" + comic_id + "]的数据")
    this_page_link = comic_link
    prev_page_link = ''
    proxy = {
        "http": proxy
    }
    page = 0
    img_list = []
    total_page = 999
    # 当上一页和当前页相同时,说明完结了,退出
    while this_page_link != prev_page_link:
        if this_page_link is None:
            break
        try:
            if use_proxy:
                response = requests.request("GET", this_page_link, headers=headers, proxies=proxy, timeout=10)
            else:
                response = requests.request("GET", this_page_link, headers=headers, timeout=10)
            # 代理出了问题
            if response.text.count('squid') or response.text.count("http://warning.or.kr") > 0:
                print(now() + "读取漫画[" + comic_id + "]:" + "当前代理出现异常,切换代理")
                raise ConnectionError("has been blocked")
            if response.status_code != 200:
                print(now() + "读取漫画[" + comic_id + "]:" + "非正常返回结果,代码[" + str(response.status_code) + "],切换代理")
                raise ConnectionError("has been blocked")
            # print(now()+response.text)
            if page == 0:
                # 目录页 提取第一页的链接
                tmp = pq(response.text).find('.gi').eq(0).find('a').attr('href')
                if tmp is None:
                    print(now() + "读取漫画[" + comic_id + "]:" + "读取数据错误,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                if tmp.count("509.gif") > 0:
                    print(now() + "读取漫画[" + comic_id + "]:" + "当前代理已被Ban,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                prev_page_link = this_page_link
                this_page_link = tmp
                page += 1
                print(now() + "读取漫画[" + comic_id + "]:" + "目录页读取完成,开始进入第一页")
            else:
                # 图片页 提取下一页的链接
                img_link = pq(response.text)("#sm").attr('src')
                # 图片页链接非法
                if img_link is None:
                    print(now() + "读取漫画[" + comic_id + "]:" + "读取数据错误,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                if img_link.count("509.gif") > 0:
                    print(now() + "读取漫画[" + comic_id + "]:" + "当前代理已被Ban,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                print(now() + "读取漫画[" + comic_id + "]:" + "第" + str(page) + "页数据为[" + img_link + "]")
                # 下载中
                download_count = 0
                result = download_img(img_link, comic_id)
                while download_count < 5 and not result:
                    download_count += 1
                    print(
                        now() + "读取漫画[" + comic_id + "]:" + "图片下载失败[" + img_link + "],重试第" + str(download_count) + "次中")
                    result = download_img(img_link, comic_id)
                if download_count >= 5 and not result:
                    print(now() + "读取漫画[" + comic_id + "]:" + "下载图片失败,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                # 下载完成
                img_list.append(img_link)
                td = pq(response.text)('#ia').children().eq(0).children().children()
                total_page = int(td.eq(1).text().split('/')[1])
                tmp = td.eq(2).children().attr('href')
                prev_page_link = this_page_link
                this_page_link = tmp
                print(now() + "读取漫画[" + comic_id + "]:" + "第" + str(page) + "页读取完成,总共有" + str(total_page) + "页")
                if total_page == page:
                    print(now() + "读取漫画[" + comic_id + "]:" + "当前漫画已读取完毕")
                    break
                if this_page_link is None:
                    print(now() + "读取漫画[" + comic_id + "]:" + "读取下一页出现错误")
                    raise ConnectionError("has been blocked")
                print(now() + "读取漫画[" + comic_id + "]:" + "下一页为[" + this_page_link + "]")
                page += 1
        except ConnectionError:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            if index >= len(proxy_list):
                index = 0
            print(now() + "读取漫画[" + comic_id + "]:" + "代理[" + proxy.get('http') + "]不可用,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        except TooManyRedirects:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            if index >= len(proxy_list):
                index = len(proxy_list) - 1
            print(now() + "读取漫画[" + comic_id + "]:" + "代理[" + proxy.get('http') + "]链接不可用,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        except ReadTimeout:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            if index >= len(proxy_list):
                index = len(proxy_list) - 1
            print(now() + "读取漫画[" + comic_id + "]:" + "代理[" + proxy.get('http') + "]超时,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        except RequestException as e:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            if index >= len(proxy_list):
                index = len(proxy_list) - 1
            print(now() + "读取漫画[" + comic_id + "]:" + "出现未知异常 [{0:s}]".format(e))
            print(now() + "读取漫画[" + comic_id + "]:" + "代理[" + proxy.get('http') + "]出现异常,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        print(now() + "读取漫画[" + comic_id + "]:" + "数据查询成功,等待睡眠2秒后进行下一页查询")
        time.sleep(2)
        if this_page_link == prev_page_link and total_page != page:
            print(now() + "读取漫画[" + comic_id + "]:" + "漫画[" + comic_id + "]写入出现问题,页数不符")
    print(now() + "读取漫画[" + comic_id + "]:" + "当前漫画所有页读取完成,进行写入")
    json_list = json.dumps(img_list)
    with open("ComicData/" + comic_id + ".json", 'w') as f:
        f.write(json_list)
    print(now() + "漫画[" + comic_id + "]写入完成")


# 下载图片
def download_img(img_link, comic_id):
    try:
        print(now() + "读取漫画[" + comic_id + "]:" + "下载图片中[" + img_link + "]")
        if not os.path.exists("ComicData/" + comic_id):
            print(now() + "读取漫画[" + comic_id + "]:" + "创建目录[" + comic_id + "]")
            os.mkdir("ComicData/" + comic_id)
        file_name = img_link.split("/")[-1]
        print(now() + "读取漫画[" + comic_id + "]:" + "文件名称为[" + file_name + "]")
        r = requests.get(img_link, timeout=10)
        with open("ComicData/" + comic_id + "/" + file_name, "wb") as code:
            code.write(r.content)
        print(now() + "读取漫画[" + comic_id + "]:" + "写入完成")
        return True
    except ReadTimeout as e:
        print(now() + "读取漫画[" + comic_id + "]:" + "下载超时 : [{0:s}]".format(e))
        return False
    except Exception as e:
        print(now() + "读取漫画[" + comic_id + "]:" + "下载出现异常 : [{0:s}]".format(e))
        return False


# 从头开始
def start(page=1, max_page=20):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))

    dir_name = init(proxy_list[index], page, max_page)
    print(now() + "梳理获取的数据")
    new_list = []
    for i in range(1, max_page - 1):
        with open(dir_name + '/' + str(i) + ".json", 'r') as f:
            new_list += json.loads(f.read(-1))

    with open(dir_name + '/total.json', 'w') as f:
        f.write(json.dumps(new_list))
    print(now() + "梳理完成")

    # 将梳理结果写入数据库
    try:
        read_comic_to_db.driver(dir_name + '/total.json')
    except Exception:
        print(now() + "写入数据库失败,跳过写入操作")
    # 梳理完成后开始读取图片
    after(dir_name + '/total.json')


# 从头开始
def multi_start(page=1, max_page=20):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))

    dir_name = init(proxy_list[index], page, max_page)
    print(now() + "梳理获取的数据")
    new_list = []
    for i in range(1, max_page - 1):
        with open(dir_name + '/' + str(i) + ".json", 'r') as f:
            new_list += json.loads(f.read(-1))

    with open(dir_name + '/total.json', 'w') as f:
        f.write(json.dumps(new_list))
    print(now() + "梳理完成")

    # 将梳理结果写入数据库
    try:
        read_comic_to_db.driver(dir_name + '/total.json')
    except Exception:
        print(now() + "写入数据库失败,跳过写入操作")
    # 梳理完成后开始读取图片
    multi_thread_after(dir_name + '/total.json')


# 读取每个漫画的图片
def after(total_json_file):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    data = ''
    # 将total数据全部读取
    with open(total_json_file, 'r') as f:
        for line in f.readlines():
            data += line.strip()
        data = json.loads(data)
        for comic in data:
            comic_json_after(comic)


# 传入一个json对象，进行图片的读取
def comic_json_after(comic_json, get_proxy_again=False):
    global proxy_list, headers
    if get_proxy_again:
        init_proxy.create_proxy()
        print(now() + "获取代理成功")
        with open('proxy.json', 'r') as f:
            proxy_list = json.loads(f.read(-1))
    read_comic_img_info(comic_json['comicLink'], headers, proxy_list[index])


# 读取每个漫画的图片 多线程版本
def multi_thread_after(total_json_file):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    data = ''
    # 将total数据全部读取
    with open(total_json_file, 'r') as f:
        for line in f.readlines():
            data += line.strip()
        json_list_data = json.loads(data)

    # q是任务队列
    q = Queue()
    # data是comic对象队列
    comic_list = Queue()
    # NUM是并发线程总数
    NUM = 15
    # JOBS是有多少任务,也就是comic_list的大小
    JOBS = 0

    # 在工作之前，将json_list_data的数据放入comic_list中
    for comic in json_list_data:
        JOBS += 1
        comic_list.put(comic)

    print(now() + "要执行" + str(JOBS) + "个任务")

    # 这个是工作进程，负责不断从队列取数据并处理
    def working():
        while True:
            comic_json = comic_list.get()
            print(now() + "执行新任务:下载[" + comic_json['comicId'] + "]漫画")
            comic_json_after(comic_json)
            sleep(1)
            q.task_done()

    # fork NUM个线程等待队列
    for i in range(NUM):
        print(now() + "创建线程[" + str(i) + "]中")
        t = Thread(target=working)
        t.setDaemon(True)
        t.start()
    # 把JOBS排入队列
    for i in range(JOBS):
        # print(now() + "执行第[" + str(i) + "]个任务")
        q.put(i)
    # 等待所有JOBS完成
    q.join()


reload(sys)
sys.setdefaultencoding('utf8')
