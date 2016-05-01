# encoding:UTF-8
from operator import contains

__author__ = 'Hope6537'
import os

print(os.name)
# 获得环境变量
print(os.environ.get("HADOOP_HOME"))
# 查看当前目录的绝对路径
print(os.path.abspath("."))
# 首先要把目录的完整路径表示
# 把两个路径合成一个时，不要直接拼字符串，而要通过os.path.join()函数，
# 这样可以正确处理不同操作系统的路径分隔符。在Linux/Unix/Mac下->"/" Windows下->"\"
path = os.path.join(os.path.abspath("."), "testDir")
# 创建目录
# os.mkdir(path)
# 删除目录
# os.rmdir(path)

# 同样的道理，要拆分路径时，也不要直接去拆字符串，而要通过os.path.split()函数，这样可以把一个路径拆分为两部分，后一部分总是最后级别的目录或文件名
t = os.path.split(path)
print(t[-1])
# 可以直接得到文件拓展名
suffix = os.path.splitext("C:/user/jdk.zip");
filename = os.path.basename("C:/user/jdk.zip");
print(suffix[-1])
print(filename)
print(contains("encodings.xml", "encoding"))

# 列出当前目录下的所有目录
print([x for x in os.listdir('.') if os.path.isdir(x)])


# 这些合并、拆分路径的函数并不要求目录和文件要真实存在，它们只对字符串进行操作。

# Python没有提供关于复制的函数，所以需要如下
# 对文件重命名:
# os.rename('test.txt', 'test.py')
# 删掉文件
# os.remove('test.py')
# 或者使用第三方库

# 编写一个search(s)的函数，能在当前目录以及当前目录的所有子目录下查找文件名包含指定字符串的文件，并打印出完整路径：
def search(target, path="."):
    for filename in os.listdir(path):
        fileNext = os.path.join(path, filename)
        if os.path.isdir(fileNext):
            search(target, fileNext)
        elif os.path.isfile(filename) and target in filename:
            print(fileNext)


search(".xml")
