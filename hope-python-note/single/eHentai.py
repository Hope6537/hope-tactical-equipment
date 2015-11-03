# encoding:UTF-8
import cookielib
import os
import urllib
import urllib2

from pyquery import PyQuery as pq


__author__ = 'Hope6537'

wget = "wget "
https = "https://"
dir = "/Users/hope6537/eHentai/"
urlFile = open('/Users/hope6537/url.txt', 'a')
site = "http://lofi.e-hentai.org/"
cj = cookielib.CookieJar()
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
# Cookie:xres=3
opener.addheaders = [('cookie', "xres=3")]
urllib2.install_opener(opener)

d = pq(url='http://lofi.e-hentai.org/?f_search=chinese&f_apply=Search')
data = d('.ig')
for i in data:
    doc = pq(i).find("a").attr("href")[-10:-1] + '/'
    os.mkdir(dir + doc)
    print("mkdir on " + (dir + doc))
    contentLink = pq(url=pq(i).find("a").attr("href")).find("a:first").attr("href")
    print("contentLink is " + contentLink)
    preLink = ""
    nextLink = "this"
    while preLink != nextLink:
        nextLink = pq(contentLink).find("#sd").find("a").attr("href")
        imgLink = pq(contentLink).find("#sm").attr("src")
        print("writing " + (wget + imgLink))
        urlFile.write(wget + imgLink + ";\n")
        print("download " + (dir + doc + imgLink[-8:]))
        urllib.urlretrieve(imgLink, dir + doc + imgLink[-8:])
        print("download complete")
        urlFile.flush()
        preLink = contentLink
        contentLink = nextLink
urlFile.close()