# encoding:UTF-8
import cookielib
import time
import urllib2

__author__ = 'Hope6537'

wget = "wget "
https = "https://"
urlFile = open('D:/url.txt', 'a')
from pyquery import PyQuery as pq

site = "https://idol.sankakucomplex.com/"
cj = cookielib.CookieJar()
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
opener.addheaders = [('User-agent', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)'), ('cookie',
                                                                                            "__cfduid=dae2599eb54d430ed60a654006a8d4f811431181298; login=hope6537; pass_hash=75e68379fbfce293a596adb5e3551b5a7c2d1b60; __atuvc=26%7C20%2C13%7C21%2C32%7C22%2C9%7C23%2C13%7C24; __atuvs=557ef05f96b6228400c; mode=view; auto_page=0; blacklisted_tags=; _sankakucomplex_session=BAh7BzoPc2Vzc2lvbl9pZCIlZDQ1NjdhNDYwMzAxOTQyMzIxMWEyZWViYzlkZGNjZWM6DHVzZXJfaWRpAi7n--1307a91c49017467d75b5d53f36a0ae7202566cc")]
urllib2.install_opener(opener)
for i in range(1, 106):
    d = pq(url='https://idol.sankakucomplex.com/?tags=webm&page=1')
    data = d('.thumb')
    for i in data:
        doc = pq(i).children().attr("href")
        single = pq(url=(site + doc))
        url = single('#image').attr("src")[2:-7]
        print(url)
        urlFile.write(wget + https + url + ";\n")
        urlFile.flush()
        time.sleep(2)
urlFile.close()
