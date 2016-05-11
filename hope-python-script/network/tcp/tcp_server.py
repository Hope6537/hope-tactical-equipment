# coding=utf-8
import socket
import threading

bind_ip = "0.0.0.0"
bind_port = 6537

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server.bind((bind_ip, bind_port))

server.listen(5)

print "[*] Listening on %s:%d" % (bind_ip, bind_port)


# 客户端请求处理线程
def handle_client(client_socket):
    # 获取请求信息
    request = client_socket.recv(1024)

    print "[*] Received: %s" % request

    # 响应具体信息
    client_socket.send("ACK!")
    print client_socket.getpeername()
    client_socket.close()


while True:
    # 无限循环以获取客户端信息
    client, addr = server.accept()

    print "[*] Accepted connection from: %s:%d" % (addr[0], addr[1])

    # 创建处理请求线程
    client_handler = threading.Thread(target=handle_client, args=(client,))
    client_handler.start()
