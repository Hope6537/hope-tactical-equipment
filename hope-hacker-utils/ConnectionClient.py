#encoding:utf-8
__author__ = 'hope6537'

import socket

target_host = '127.0.0.1'
target_post = 80

def tcp_connect():

    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect(target_host, target_post)
    client.send("GET \ HTTP/1.1 \r\n Host:hope6537.com")
    response = client.recv(4096)

    return response


def udp_connect():

    client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    client.connect(target_host, target_post)

    client.sendto("Message", (target_host, target_post))
    data, address = client.recv(4096)
    return data
