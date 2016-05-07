# encoding:UTF-8

# 获取代理.然后访问目标url
import json
import sys

import requests
from requesocks import ConnectionError

import comic_hentai.init_proxy as proxy

proxy_list = []
proxy_index = 0
vote_count = 0


def now():
    return proxy.now()


def vote(url, proxy):
    global proxy_list, proxy_index
    proxies = {
        "http": proxy,
    }
    headers = {
        'If-None-Match': 'W/"3cef789f0d175497dd09bdcffe621d6b"',
        'Accept-Encoding': 'gzip, deflate, sdch',
        'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4',
        'Upgrade-Insecure-Requests': "1",
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Cache-Control': 'max-age=0',
        'Connection': 'keep-alive',
        'User-Agent': "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36",
        'cookie': 'ASPSESSIONIDCAQBARQC=PAAEFINCLDGCJOODAPIJMDBK; stprinthd1982Login=ipnum=0&dlname=hope6537&hduid=33262; a4239_pages=3; a4239_times=2; a0485_pages=6; a0485_times=2'
    }
    querystring = {
        "fid": "hope6537",
    }
    response = None
    while response is None:
        try:
            response = requests.request("GET", url, headers=headers, params=querystring, proxies=proxies, timeout=2)
            if response.text.count('squid') > 0 or response.text.count("http://warning.or.kr") > 0:
                print(now() + "当前代理出现异常,切换代理")
                raise ConnectionError("has been blocked")
            if response.status_code != 200:
                print(now() + "非正常返回结果,代码[" + str(response.status_code) + "],切换代理")
                raise ConnectionError("has been blocked")
            if response.text.count(
                    'Your IP address has been temporarily banned for excessive pageloads which indicates that you are using automated mirroring/harvesting software.') > 0:
                print(now() + "当前代理已被Ban,切换代理")
                raise ConnectionError("has been blocked")
        except Exception:
            # 如果代理连不上,换一个
            proxy_index += 1
            if proxy_index == len(proxy_list):
                proxy_index = 0
                proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print(now() + "代理[" + proxies.get('http') + "]不可用,切换至[" + proxy_list[proxy_index] + "]")
            proxies = {
                "http": proxy_list[proxy_index]
            }
            continue
    return True


def driver(limit=100):
    global vote_count, proxy_list, proxy_index
    proxy.create_proxy('mixed')
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    print(now() + "正在进行第" + str(vote_count + 1) + "访问,共需要访问" + str(limit + 1) + "次")
    while vote_count < limit:
        result = vote('http://1024sq.xyz/ad.asp', proxy_list[proxy_index])
        if result:
            print(now() + "第" + str(vote_count + 1) + "次成功登陆")
            vote_count += 1
            proxy_index += 1
            if proxy_index == len(proxy_list):
                proxy_index = 0


reload(sys)
sys.setdefaultencoding('utf8')
driver()
