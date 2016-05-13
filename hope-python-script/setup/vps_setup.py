# coding=utf-8
"""
驱动Ubuntu 14.04 64bit VPS初始化
"""
import commands
import os
import time

import requests
from requests.exceptions import ReadTimeout


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


(status, output) = commands.getstatusoutput('cat /proc/cpuinfo')
print (status, output)
(status, output) = commands.getstatusoutput("cat /etc/hosts")
print (status, output)


# git 在此模式下可用
# (status, output) = commands.getstatusoutput("git clone https://github.com/Hope6537/custom_python_trojan_example.git")
# print (status, output)


def download_resources(url, target_name=None):
    """
    下载文件函数
    :param url: 下载链接
    :param target_name: 保存的文件名
    :return: 是否下载成功 True为成功
    """
    global file_name
    try:
        print(now() + "下载资源中,链接地址为[%s]" % url)
        if not os.path.exists("./resources"):
            print(now() + "下载资源中,创建目录" + os.getcwd() + "/resources")
            os.mkdir("./resources")
        if target_name is None:
            file_name = url.split("/")[-1]
        else:
            file_name = target_name
        print(now() + "下载资源中,文件名称为[" + file_name + "]")
        r = requests.get(url, timeout=10)
        with open("resources/" + file_name, "wb") as code:
            code.write(r.content)
        print(now() + "下载资源中,资源:[" + file_name + "],下载完成")
        return True
    except ReadTimeout as e:
        print(now() + "下载资源中,资源:[" + file_name + "],下载失败,连接超时")
        return False
    except Exception as e:
        print(now() + "下载资源中,资源[" + file_name + "]:" + "下载出现异常 : [{0:s}]".format(e))
        return False
