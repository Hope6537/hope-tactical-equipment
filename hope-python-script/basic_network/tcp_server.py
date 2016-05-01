# coding=utf-8
import socket
import threading

bind_ip = "0.0.0.0"
bind_port = 6537

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.listen(5)

print("[*]Listening on %s:%d" % (bind_ip, bind_port))


def handle_client(client_socket):
    request = client_socket.recv(1024)
    print("[*]received % s" % request)
    client_socket.send("ACK !")
    client_socket.close()


while True:
    # 接受请求
    client, addr = server.accept()
    print("accept the connection from: % s, % d" % (addr[0], addr[1]))
    # 挂起客户端线程，处理传输的数据
    client_handler = threading.Thread(target=handle_client, args=(client,))
    client_handler.start()
