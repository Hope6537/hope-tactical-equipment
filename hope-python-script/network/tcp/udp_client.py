# coding=utf-8
import socket

target_host = "127.0.0.1"
target_port = 6537

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# UDP是无连接状态的传输协议

client.sendto("ABCDEFGHIJK", (target_host, target_port))

data, addr = client.recvfrom(4096)

print data
