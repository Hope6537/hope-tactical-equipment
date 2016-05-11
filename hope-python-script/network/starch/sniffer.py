# coding=utf-8
"""
建立原始Socket嗅探器,读取一个数据包然后退出即可
"""

import os
import socket


def driver():
    # 监听的主机
    host = "192.168.1.102"
    # 创建原始Socket,然后绑定在公开接口上
    if os.name == "nt":
        socket_protocol = socket.IPPROTO_IP
    else:
        socket_protocol = socket.IPPROTO_ICMP

    sniffer = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket_protocol)

    sniffer.bind((host, 0))

    # 设置在捕获的数据包中包含IP头
    sniffer.setsockopt(socket.IPPROTO_IP, socket.IP_HDRINCL, 1)

    # 在Windows平台上,需要设置IOCTL来启用混杂模式
    if os.name == "nt":
        sniffer.ioctl(socket.SIO_RCVALL, socket.RCVALL_ON)

    # 读取单个数据包
    print sniffer.recvfrom(65565)

    # 在Windows平台上关闭混杂模式
    if os.name == "nt":
        sniffer.ioctl(socket.SIO_RCVALL, socket.RCVALL_OFF)


# 然后随便ping下别的主机 ping 192.168.1.188
"""
('E\x00@\x00L\xe0\x00\x00@\x01\xa9V\xc0\xa8\x01\xbc\xc0\xa8\x01f\x00\x00\xa5\x8b\xb4\x19\x00\x00W3\x10"\x00\x08S\xfa\x08\t\n\x0b\x0c\r\x0e\x0f\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f !"#$%&\'()*+,-./01234567', ('192.168.1.188', 0))
"""
driver()
