# encoding:utf-8
# 处理大量图像
import os


def get_imlist(path):
    """返回当前目录中所有的jpg图像名列表"""
    return [os.path.join(path, f) for f in os.listdir(path) if f.endswith(".jpg")]
