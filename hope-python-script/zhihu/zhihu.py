# encoding:utf-8
import requests

import requests

cookies = {
    'd_c0': 'AACAamB1LQqPToMcUMk8zMHR10FwezFwQsg=|1467629360',
    '_zap': '540a813f-95b8-477d-9141-728140f8b544',
    '_za': 'b9974a5f-14d0-4fe3-971a-0ebed07a7c0f',
    '_ga': 'GA1.2.1022349923.1490619099',
    'q_c1': '26735a973129474dac8f09af940badb3|1502676273000|1491468158000',
    'r_cap_id': 'YTY3MzRiZjNkNTIxNGI1OGJiOTcwNmI0NDBhMGE0OGQ=|1510285264|09b6bc23aea437c66511ae0ff3ac10f685ff3ce6',
    'cap_id': 'ZDE5NjNhYTMxNzY5NGMzOTg2NjAyZmYwMDcyNGY5ZjQ=|1510285264|3b964da66815fff59b200dcef7a22da45202322e',
    'z_c0': 'Mi4xM3E5S0FBQUFBQUFBQUlCcVlIVXRDaGNBQUFCaEFsVk4xbTN5V2dBNTZ5dEkyVlc5OTd3QjU1R3Rrb1NmYTVBWkxB|1510285270|9c1c1a171c16873e03ddcab5a3fe2d6dce757358',
    'aliyungf_tc': 'AQAAAErtZUYRUgEAwk3gerg+xadr2Zq8',
    's-q': '%E5%88%98%E9%91%AB',
    's-i': '1',
    'sid': 'k0eskato',
    '__utma': '51854390.1022349923.1490619099.1510734700.1510734700.1',
    '__utmb': '51854390.0.10.1510734700',
    '__utmc': '51854390',
    '__utmz': '51854390.1510734700.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)',
    '__utmv': '51854390.110-1|2=registration_date=20140405=1^3=entry_date=20140405=1',
    '_xsrf': '8b66d9ea-9296-4e5b-ba26-98984118d3af',
}

headers = {
    'authorization': 'Bearer Mi4xM3E5S0FBQUFBQUFBQUlCcVlIVXRDaGNBQUFCaEFsVk4xbTN5V2dBNTZ5dEkyVlc5OTd3QjU1R3Rrb1NmYTVBWkxB|1510285270|9c1c1a171c16873e03ddcab5a3fe2d6dce757358',
    'DNT': '1',
    'Accept-Encoding': 'gzip, deflate, br',
    'Accept-Language': 'zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,zh-TW;q=0.2',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36',
    'accept': 'application/json, text/plain, */*',
    'Referer': 'https://www.zhihu.com/',
    'X-UDID': 'AACAamB1LQqPToMcUMk8zMHR10FwezFwQsg=',
    'Connection': 'keep-alive',
    'X-API-VERSION': '3.0.53',
}

params = (
    ('action_feed', 'True'),
    ('limit', '10'),
    ('session_token', '2de830e516bd9d2b2fb54bb97712308f'),
    ('action', 'down'),
    ('after_id', '19'),
    ('desktop', 'true'),
)

data = requests.get('https://www.zhihu.com/api/v3/feed/topstory', headers=headers, params=params, cookies=cookies)
print(data.text)
