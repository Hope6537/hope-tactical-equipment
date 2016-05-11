# coding=utf-8
"""
解码ICMP
"""
import os
import socket
import struct
from ctypes import *


class IP(Structure):
    _fields_ = [
        ("ihl", c_ubyte, 4),
        ("version", c_ubyte, 4),
        ("tos", c_ubyte),
        ("len", c_ushort),
        ("id", c_ushort),
        ("offset", c_ushort),
        ("ttl", c_ubyte),
        ("protocol_num", c_ubyte),
        ("sum", c_ushort),
        # 32bit
        # ("src", c_ulong),
        # ("dst", c_ulong),
        # 64bit
        ("src", c_uint32),
        ("dst", c_uint32)
    ]

    def __new__(self, socket_buffer=None):
        return self.from_buffer_copy(socket_buffer)

    def __init__(self, socket_buffer=None):

        # 协议字段和协议名称对应
        self.protocol_map = {1: "ICMP", 6: "TCP", 17: "UDP"}

        # 对IP地址进行格式化 32bit
        # self.src_address = socket.inet_ntoa(struct.pack("<L", self.src))
        # self.dst_address = socket.inet_ntoa(struct.pack("<L", self.dst))

        # 对IP地址进行格式化 64bit
        self.src_address = socket.inet_ntoa(struct.pack("@I", self.src))
        self.dst_address = socket.inet_ntoa(struct.pack("@I", self.dst))

        # 格式化协议类型
        try:
            self.protocol = self.protocol_map[self.protocol_num]
        except:
            self.protocol = str(self.protocol_num)


class ICMP(Structure):
    _fields_ = [
        ("type", c_ubyte),
        ("code", c_ubyte),
        ("checksum", c_ushort),
        ("unused", c_ushort),
        ("next_hop_mtu", c_ushort)
    ]

    def __new__(self, socket_buffer):
        return self.from_buffer_copy(socket_buffer)

    def __init__(self, socket_buffer):
        pass


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

    # 将IPv4头信息打包
    sniffer.setsockopt(socket.IPPROTO_IP, socket.IP_HDRINCL, 1)

    # 设置混杂模式
    if os.name == "nt":
        sniffer.ioctl(socket.SIO_RCVALL, socket.RCVALL_ON)

    try:
        while True:

            # 读取数据包
            raw_buffer = sniffer.recvfrom(65565)[0]

            # 将缓冲区的前20个字节按照IP头进行解析(32位)
            # ip_header = IP(raw_buffer[0:20])
            # 将缓冲区的前32个字节按照IP头进行解析(64位)
            ip_header = IP(raw_buffer[0:32])

            print "Protocol: %s %s -> %s" % (ip_header.protocol, ip_header.src_address, ip_header.dst_address)

            # 如果是ICMP,进行进一步处理
            if ip_header.protocol == "ICMP":
                # 计算ICMP包的起始位置
                offset = ip_header.ihl * 4
                buf = raw_buffer[offset:offset + sizeof(ICMP)]

                # 解析数据
                icmp_header = ICMP(buf)

                print "ICMP -> Type: %d Code: %d" % (icmp_header.type, icmp_header.code)

    # handle CTRL-C
    except KeyboardInterrupt:
        # 在Windows平台上关闭混杂模式
        if os.name == "nt":
            sniffer.ioctl(socket.SIO_RCVALL, socket.RCVALL_OFF)

'''
output:
Protocol: ICMP 220.181.57.217 -> 192.168.1.102
ICMP -> Type: 0 Code: 0
'''
driver()
