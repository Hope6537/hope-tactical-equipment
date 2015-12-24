# encoding:UTF-8
import cookielib
import json
import os
import urllib
import urllib2

from pyquery import PyQuery as pq

__author__ = 'hope6537'

wget = "wget "
https = "https://"
dir = "/Users/hope6537/eHentai"
urlFile = open('/Users/hope6537/eHentai/url.txt', 'a')
site = "http://lofi.e-hentai.org/"


def init_web():
    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    opener.addheaders = [('cookie', "xres=3")]
    urllib2.install_opener(opener)
    parse_data(pq(url='http://lofi.e-hentai.org/?f_search=Chinese&f_apply=Search')(".ig"))


'''
得到主页列表
'''


def parse_data(data):
    print("data is" + json.dumps(data))
    # 对于列表的每一个元素进行循环
    for i in data:
        # 得到当前本子的连接
        doc = pq(i).find("a").attr("href")[-10:-1] + '/'
        print("doc is " + doc)
        try:
            # 拿到文件名(即E绅士随机生成的字符串)
            os.mkdir(dir + doc)
            # 创建新目录
            print("mkdir on " + (dir + doc))
            copy_to_fs(i, doc)
        except Exception:
            print("already exist")
            continue
    final()


'''
将图片下载到文件系统
'''


def copy_to_fs(index, doc):
    # 拿到当前本子的第一页的连接
    content_link = pq(url=pq(index).find("a").attr("href")).find("a:first").attr("href")
    print("content_link is " + content_link)
    # 前一个页面的连接是无，后一个页面的连接就是现在的链接
    pre_link = ""
    next_link = "this"
    # 如果之前的连接和现在的连接相同，说明爬虫完毕，否则继续下载
    while pre_link != next_link:
        # 下一页的连接就是当前的图片href
        next_link = pq(content_link).find("#sd").find("a").attr("href")
        to_next_link(content_link, doc)
        # 要进入到下一个页面了，将前一个页面的连接指向内容连接，将内容连接指向下一个连接，并继续抓取
        pre_link = content_link
        content_link = next_link


'''
content_link 是图片的连接
doc 是当前的本子的随机码
'''


def to_next_link(content_link, doc):
    global img_link
    try:
        # 拿到图片连接的源地址
        img_link = pq(content_link).find("#sm").attr("src")
        file_name = (img_link[-8:]).replace("/", "-")
        if os.path.exists(dir + doc + file_name):
            print("already exists")
            return
        print("writing " + (wget + img_link))
        print("filename will be " + file_name)
        # 写入url文件
        urlFile.write(wget + img_link + ";\n")
        print("download " + (dir + doc + file_name))
        # 并下载
        urllib.urlretrieve(img_link, dir + doc + file_name)
        print("download complete")
        # 写入之后刷新
        urlFile.flush()
    except Exception:
        print("error page" + img_link)


def final():
    urlFile.close()


init_web()