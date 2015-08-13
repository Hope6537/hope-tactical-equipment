# encoding:UTF-8
import cookielib
import io
import os
import urllib
import urllib2

__author__ = 'Hope6537'

wget = "wget "
https = "https://"
dir = "D:/eHentai/"
urlFile = open('D:/url.txt', 'a')
from pyquery import PyQuery as pq

site = "http://lofi.e-hentai.org/"
cj = cookielib.CookieJar()
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
# Cookie:xres=3
opener.addheaders = [('cookie', "xres=3")]
urllib2.install_opener(opener)

d = pq(url='http://lofi.e-hentai.org/?f_search=chinese&f_apply=Search')
data = d('.ig')
for i in data:
    doc = pq(i).find("a").attr("href")[-10:-1]
    os.mkdir(dir + doc)
    contentLink = pq(url=pq(i).find("a").attr("href")).find("a:first").attr("href")
    preLink = ""
    nextLink = "this"
    while preLink != nextLink:
        nextLink = pq(contentLink).find("#sd").find("a").attr("href")
        imgLink = pq(contentLink).find("#sm").attr("src")
        urlFile.write(wget + imgLink + ";\n")
        urllib.urlretrieve(imgLink, dir + doc + imgLink[-8:])
        urlFile.flush()
        print(nextLink)
        preLink = contentLink
        contentLink = nextLink
urlFile.close()