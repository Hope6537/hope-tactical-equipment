# encoding:UTF-8
import codecs

__author__ = 'Hope6537'
# 打开文件并读取同时关闭流
# 像open()函数返回的这种有个read()方法的对象，在Python中统称为file-like Object。
# 除了file外，还可以是内存的字节流，网络流，自定义流等等。file-like Object不要求从特定类继承，只要写个read()方法就行。
try:
    file1 = open("D:/test.txt", "r")
    # 以二进制模式打开文本文件，并进行转码
    # file1 = open("D:/test.txt", "rb")
    # u = file1.read().decode("GBK")
    # print(file1.read())
    # 同样的codecs里面自带一个默认转码
    with codecs.open('D:/test1.txt', 'r', 'GBK') as f:
        print(f.read())  # u'\u6d4b\u8bd5'
finally:
    if file1:
        file1.close()

# 另一种比较稳妥的写法 自动调用close方法
with open("D:/test.txt", "r") as f:
    # print(f.read())
    # 如果文件很小，read()一次性读取最方便；如果不能确定文件大小，反复调用read(size)比较保险；如果是配置文件，调用readlines()最方便：
    for line in f.readlines():
        len(line)
# 读取二进制文件
f = open('D:/test.txt', 'rb')

# 写文件
# f = open('D:/test.txt', 'w')
# 但是如果不close的话是没法从缓冲区放入到磁盘里的
# f.write('Hello, world!')
# f.close()

with open('D:/test.txt', 'w') as f:
    f.write('你好')
# 写入特定编码文件
with codecs.open('D:/test1.txt', 'w+', 'GBK') as f:
    f.write("你好".decode("UTF-8"))
