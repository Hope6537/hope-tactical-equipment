# encoding:utf-8
import json
import time

import requests
from pyquery import PyQuery as pq


def init(url):
    response = requests.get(url, timeout=5)
    html = pq(response.text)("tbody")
    table = html.children()
    i = 1
    list = []
    while i < len(table):
        ip = str(table[i].text)
        port = get_port(table[i + 1].find("img").attrib['src'].split('v')[-1])
        if port is None:
            i += 9
            continue
        list.append("http://" + str(ip) + ":" + str(port))
        i += 9
    return list


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


def low_secret_proxy():
    return init('http://proxy.mimvp.com/free.php?proxy=out_tp')


def high_secret_proxy():
    return init('http://proxy.mimvp.com/free.php?proxy=out_hp')


# curl 'http://www.xicidaili.com/wn/1'
# -H 'If-None-Match: W/"3cef789f0d175497dd09bdcffe621d6b"'
# -H 'Accept-Encoding: gzip, deflate, sdch'
# -H 'Accept-Language: zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4'
# -H 'Upgrade-Insecure-Requests: 1'
# -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36'
# -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8'
# -H 'Cache-Control: max-age=0'
# -H 'Cookie: _free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJTE2OTgyOWYxNjBjNmFjNTgwMGRkODg5YzdjY2I2NGYxBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMWxUckgyQTJkcDloaDRVekEwQk9nNnhHL0MyWjNKWExjQlV0VjAwMHZTRXc9BjsARg%3D%3D--f4f30e1da8f208e73219badf7cd95a8202dbf1b3; CNZZDATA1256960793=299748290-1458706542-http%253A%252F%252Fwww.baidu.com%252F%7C1458727389'
# -H 'Connection: keep-alive' --compressed
def get_more_proxy(count=100):
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
    url = 'http://www.xicidaili.com/wn/'
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
            ip = proxy.eq(2)[0].text
            port = proxy.eq(3)[0].text
            proxy_item = "http://" + ip + ":" + str(port)
            ipList.append(proxy_item)
            size += 1
        page += 1
    return ipList


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


def create_proxy():
    has_old_proxy = True
    old_proxy_list = []
    try:
        with open('proxy.json', 'r') as f:
            old_proxy_list = json.loads(f.read(-1))
    except IOError:
        print("没有以前的代理数据,使用本地模板")
        has_old_proxy = False

    if not has_old_proxy:
        try:
            with open('template_proxy.json', 'r') as f:
                old_proxy_list = json.loads(f.read(-1))
                has_old_proxy = True
        except IOError:
            print("模板数据无效")

    has_proxy = False
    more_list = []
    low_list = []
    high_list = []
    count = 0
    while not has_proxy and count < 5:
        count += 1
        if count == 5:
            if has_old_proxy:
                print("获取代理失败,使用老数据")
                break
            else:
                print("没有老数据,60秒后重新读取")
                time.sleep(60)
                count = 0
        try:
            low_list = low_secret_proxy()
            high_list = high_secret_proxy()
            has_proxy = True
        except BaseException:
            print("获取代理失败,重新获取")
            continue
    try:
        more_list = get_more_proxy(300)
    except BaseException:
        print("获取大量代理失败")
    if has_proxy:
        proxy_list = low_list + high_list + old_proxy_list
    else:
        proxy_list = old_proxy_list
    if len(more_list) == 0:
        json_list = json.dumps(proxy_list)
    else:
        json_list = json.dumps(proxy_list + more_list)
    json_file = open('proxy.json', 'w')
    json_file.write(json_list)
    json_file.close()


create_proxy()
