# coding=utf-8
import socket

target_host = "127.0.0.1"
target_port = 6537

# 建立一个socket对象 我们将使用IPV4地址或者主机名，STREAM表示这是一个TCP客户端
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 连接客户端
client.connect((target_host, target_port))
# 发送一些数据
client.send("Hello TCP!")
# client.send("GET / HTTP/1.1\r\nHost: baidu.com\r\n\r\n")
# 接受一些数据
response = client.recv(4096)
# 这里忽略了套接字异常，套接字阻塞等方式
print response
