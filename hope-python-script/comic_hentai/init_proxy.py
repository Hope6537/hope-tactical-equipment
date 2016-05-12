# encoding:utf-8
import json
import os
import time

import requests
from pyquery import PyQuery as pq


def parse_few_proxy(url, add_pattern=9):
    response = requests.get(url, timeout=5)
    html = pq(response.text)("tbody")
    table = html.children()
    i = 1
    list = []
    while i < len(table):
        ip = str(table[i].text)
        key = table[i + 1].find("img").attrib['src'].split('v')[-1]
        port = get_port(key)
        if port is None:
            i += add_pattern
            continue
        list.append("http://" + str(ip) + ":" + str(port))
        i += add_pattern
    return list


def parse_fpl_proxy(url='http://free-proxy-list.net/', add_pattern=8):
    response = requests.get(url, timeout=5)
    html = pq(response.text)("tbody")
    table = html.children()
    i = 0
    list = []
    while i < len(table):
        ip = str(table[i][0].text)
        port = str(table[i][1].text)
        list.append("http://" + str(ip) + ":" + str(port))
        i += 1
    return list


def get_port(img):
    ports = '''
    {
      "MpDgw": "8080",
      "MpTI4": "3128",
      "MpTIz": "8123",
      "MpAO0OO0O": "80",
      "NpDMO0O": "443",
      "MpDgz": "8083",
      "MpDg5": "8089",
      "MpDAw": "9090",
      "OpDg4": "8888",
      "MpQO0OO0O":"81"
    }
    '''
    ports = json.loads(ports)
    return ports.get(img)


def parse_lots_proxy(url='http://www.xicidaili.com/wn', count=100):
    """
    需要curl 'http://www.xicidaili.com/wn/1' 后面跟一堆http请求头的,有时候会被ban掉,所以只有应该尝试切换动态
    :param url:
    :param count:
    :return:
    """
    ipList = []
    headers = {
        'If-None-Match': 'W/"3cef789f0d175497dd09bdcffe621d6b"',
        'Accept-Encoding': 'gzip, deflate, sdch',
        'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4',
        'Upgrade-Insecure-Requests': "1",
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Cache-Control': 'max-age=0',
        'Connection': 'keep-alive',
        'User-Agent': "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36",
        'cookie': '_free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTE2OTgyOWYxNjBjNmFjNTgwMGRkODg5YzdjY2I2NGYxBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMWxUckgyQTJkcDloaDRVekEwQk9nNnhHL0MyWjNKWExjQlV0VjAwMHZTRXc9BjsARg%3D%3D--f4f30e1da8f208e73219badf7cd95a8202dbf1b3; CNZZDATA1256960793=299748290-1458706542-http%253A%252F%252Fwww.baidu.com%252F%7C1458727389'
    }
    size = 0
    page = 1
    s = requests.Session()
    while size < count:
        response = s.get(url + str(page), headers=headers, timeout=5)
        if response.status_code != 200:
            print(now() + "非正常返回结果,代码[" + str(response.status_code) + "]")
            continue
        table = pq(response.text)('#ip_list').children('tr')
        for i in range(1, len(table) - 1):
            proxy = table.eq(i).children('td')
            ip = proxy.eq(1)[0].text
            port = proxy.eq(2)[0].text
            proxy_item = "http://" + ip + ":" + str(port)
            ipList.append(proxy_item)
            size += 1
        page += 1
    return ipList


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


def get_few_cn_proxy():
    return parse_few_proxy('http://proxy.mimvp.com/free.php?proxy=in_tp', 10)


def get_few_cn_secret_proxy():
    return parse_few_proxy('http://proxy.mimvp.com/free.php?proxy=in_hp', 10)


def get_few_foreign_proxy():
    return parse_few_proxy('http://proxy.mimvp.com/free.php?proxy=out_tp')


def get_few_foreign_secret_proxy():
    return parse_few_proxy('http://proxy.mimvp.com/free.php?proxy=out_hp')


def get_lot_cn_proxy():
    return parse_lots_proxy('http://www.xicidaili.com/nt/')


def get_lot_cn_secret_proxy():
    return parse_lots_proxy('http://www.xicidaili.com/nn/')


def get_lot_foreign_proxy():
    return parse_lots_proxy('http://www.xicidaili.com/wt/')


def get_lot_foreign_secret_proxy():
    return parse_lots_proxy('http://www.xicidaili.com/wn/')


def get_parse_fpl_proxy():
    return parse_fpl_proxy()


def gen_china_proxy():
    """
    获取中国代理
    """
    has_old_proxy = False
    old_proxy_list = []
    template_proxy_list = []
    proxy_list = []
    fetch_success = False
    has_add_old = False

    try:
        with open('cn_proxy.json', 'r') as f:
            old_proxy_list = json.loads(f.read(-1))
            has_old_proxy = True
    except IOError:
        print("没有以前的代理数据,使用本地模板")
        try:
            with open('template_cn_proxy.json', 'r') as f:
                old_proxy_list = json.loads(f.read(-1))
                template_proxy_list = old_proxy_list
                has_old_proxy = True
        except IOError:
            print(now() + "国内代理数据无效")

    try:
        proxy_list += get_few_cn_proxy()
    except BaseException as e:
        print(now() + "获取国内普通代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_few_cn_secret_proxy()
    except BaseException as e:
        print(now() + "获取国内高匿代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_lot_cn_proxy()
    except BaseException as e:
        print(now() + "获取国内大量普通代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_lot_cn_secret_proxy()
    except BaseException as e:
        print(now() + "获取国内大量高匿代理失败 : [{0:s}]".format(e))

    if len(proxy_list) == 0:
        proxy_list += old_proxy_list
        has_add_old = True
        print(now() + "国内代理获取失败,寻找老数据")
        if len(proxy_list) == 0:
            print(now() + "寻找老数据失败,获取模板数据")
            if len(template_proxy_list) == 0 or not has_old_proxy:
                print(now() + "读取模板数据失败,国内代理获取结束")
                return
            proxy_list = old_proxy_list
    else:
        fetch_success = True

    if not has_add_old:
        proxy_list += old_proxy_list

    if fetch_success:
        with open('cn_proxy.json', 'w') as f:
            print(now() + "代理读取成功,写入文件" + os.getcwd() + "/cn_proxy.json")
            f.write(json.dumps(proxy_list))
        # 如果没有模板数据,在第一次的时候生成
        if not has_old_proxy:
            with open('template_cn_proxy.json', 'w') as f:
                print(now() + "正在生成后备模板数据")
                f.write(json.dumps(proxy_list))
        return True
    else:
        return False


def gen_foreign_proxy():
    """
    获取国外代理
    """
    has_old_proxy = False
    old_proxy_list = []
    template_proxy_list = []
    proxy_list = []
    fetch_success = False
    has_add_old = False

    try:
        with open('foreign_proxy.json', 'r') as f:
            old_proxy_list = json.loads(f.read(-1))
            has_old_proxy = True
    except IOError:
        print("没有以前的代理数据,使用本地模板")
        try:
            with open('template_foreign_proxy.json', 'r') as f:
                old_proxy_list = json.loads(f.read(-1))
                template_proxy_list = old_proxy_list
                has_old_proxy = True
        except IOError:
            print(now() + "国外代理数据无效")

    try:
        proxy_list += get_few_foreign_proxy()
    except BaseException as e:
        print(now() + "获取国外普通代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_few_foreign_secret_proxy()
    except BaseException as e:
        print(now() + "获取国外高匿代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_lot_foreign_proxy()
    except BaseException as e:
        print(now() + "获取国外大量普通代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_lot_foreign_secret_proxy()
    except BaseException as e:
        print(now() + "获取国外大量高匿代理失败 : [{0:s}]".format(e))
    try:
        proxy_list += get_parse_fpl_proxy()
    except BaseException as e:
        print(now() + "获取国外FPL代理失败 : [{0:s}]".format(e))

    if len(proxy_list) == 0:
        proxy_list += old_proxy_list
        has_add_old = True
        print(now() + "国外代理获取失败,寻找老数据")
        if len(proxy_list) == 0:
            print(now() + "寻找老数据失败,获取模板数据")
            if len(template_proxy_list) == 0 or not has_old_proxy:
                print(now() + "读取模板数据失败,国外代理获取结束")
                return
            proxy_list = old_proxy_list
    else:
        fetch_success = True

    if not has_add_old:
        proxy_list += old_proxy_list

    if fetch_success:
        with open('foreign_proxy.json', 'w') as f:
            print(now() + "代理读取成功,写入文件" + os.getcwd() + "/foreign_proxy.json")
            f.write(json.dumps(proxy_list))
        # 如果没有模板数据,在第一次的时候生成
        if not has_old_proxy:
            with open('template_foreign_proxy.json', 'w') as f:
                print(now() + "正在生成后备模板数据")
                f.write(json.dumps(proxy_list))
        return True
    else:
        return False


def gen_fpl_proxy():
    """
    获取国外代理
    """
    has_old_proxy = False
    old_proxy_list = []
    template_proxy_list = []
    proxy_list = []
    fetch_success = False
    has_add_old = False

    try:
        with open('fpl_proxy.json', 'r') as f:
            old_proxy_list = json.loads(f.read(-1))
            has_old_proxy = True
    except IOError:
        print("没有以前的代理数据,使用本地模板")
        try:
            with open('template_fpl_proxy.json', 'r') as f:
                old_proxy_list = json.loads(f.read(-1))
                template_proxy_list = old_proxy_list
                has_old_proxy = True
        except IOError:
            print(now() + "国外代理数据无效")

    try:
        proxy_list += get_parse_fpl_proxy()
    except BaseException as e:
        print(now() + "获取国外FPL代理失败 : [{0:s}]".format(e))

    if len(proxy_list) == 0:
        proxy_list += old_proxy_list
        has_add_old = True
        print(now() + "国外代理获取失败,寻找老数据")
        if len(proxy_list) == 0:
            print(now() + "寻找老数据失败,获取模板数据")
            if len(template_proxy_list) == 0 or not has_old_proxy:
                print(now() + "读取模板数据失败,国外代理获取结束")
                return
            proxy_list = old_proxy_list
    else:
        fetch_success = True

    if not has_add_old:
        proxy_list += old_proxy_list

    if fetch_success:
        with open('fpl_proxy.json', 'w') as f:
            print(now() + "代理读取成功,写入文件" + os.getcwd() + "/fpl_proxy.json")
            f.write(json.dumps(proxy_list))
        # 如果没有模板数据,在第一次的时候生成
        if not has_old_proxy:
            with open('template_fpl_proxy.json', 'w') as f:
                print(now() + "正在生成后备模板数据")
                f.write(json.dumps(proxy_list))
        return True
    else:
        return False


def create_proxy(mode='fpl'):
    """
    懒得梳理代码了
    :param mode:
    :return:
    """
    proxy_list = []
    if mode == 'foreign':
        print(now() + "读取国外代理中")
        foreign_result = gen_foreign_proxy()
        if foreign_result:
            print(now() + "国外代理获取成功,正在合并")
        with open('foreign_proxy.json', 'r') as f:
            proxy_list += json.loads(f.read(-1))

    elif mode == 'china':
        print(now() + "读取国内代理中")
        cn_result = gen_china_proxy()
        if cn_result:
            print(now() + "国内代理获取成功,正在合并")
        with open('cn_proxy.json', 'r') as f:
            proxy_list += json.loads(f.read(-1))
    elif mode == 'fpl':
        print(now() + "读取FPL代理中")
        cn_result = gen_fpl_proxy()
        if cn_result:
            print(now() + "FPL代理获取成功,正在合并")
        with open('fpl_proxy.json', 'r') as f:
            proxy_list += json.loads(f.read(-1))
    else:
        print(now() + "读取国内代理中")
        cn_result = gen_china_proxy()
        print(now() + "读取国外代理中")
        foreign_result = gen_foreign_proxy()
        if cn_result:
            print(now() + "国内代理获取成功,正在合并")
            with open('cn_proxy.json', 'r') as f:
                proxy_list += json.loads(f.read(-1))
        if foreign_result:
            print(now() + "国外代理获取成功,正在合并")
            with open('foreign_proxy.json', 'r') as f:
                proxy_list += json.loads(f.read(-1))

    with open('proxy.json', 'w') as f:
        f.write(json.dumps(proxy_list))
        print(now() + "代理合并成功,写入文件" + os.getcwd() + "/proxy.json")
