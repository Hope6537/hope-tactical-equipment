# encoding:UTF-8
import socket
import threading
import time

__author__ = 'Hope6537'

# Server
# 定义基于TCP和IPV4的协议
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 监听端口:
s.bind(('127.0.0.1', 9999))
# 等待连接的最大数量
s.listen(5)


# 连接建立后，服务器首先发一条欢迎消息，然后等待客户端数据，并加上Hello再发送给客户端。
# 如果客户端发送了exit字符串，就直接关闭连接。
def tcplink(sock, addr):
    print('Accept new connection from %s:%s...' % addr)
    sock.send(bytearray.append('Welcome!'))
    while True:
        data = sock.recv(1024)
        time.sleep(1)
        if data == 'exit' or not data:
            break
        sock.send('Hello, %s!' % data)
    sock.close()
    print('Connection from %s:%s closed.' % addr)


while True:
    # 接受一个新连接:
    sock, addr = s.accept()
    # 创建新线程来处理TCP连接:
    t = threading.Thread(target=tcplink, args=(sock, addr))
    t.start()