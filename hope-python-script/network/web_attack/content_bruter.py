# coding=utf-8
import Queue
import threading
import urllib
import urllib2

threads = 5
target_url = "http://testphp.vulnweb.com"
wordlist_file = "./SVNDigger/all.txt"  # from SVNDigger
resume = None
user_agent = "Mozilla/5.0 (X11; Linux x86_64; rv:19.0) Gecko/20100101 Firefox/19.0"


def build_wordlist(wordlist_file):
    # 读取字典文件
    fd = open(wordlist_file, "rb")
    raw_words = fd.readlines()
    fd.close()

    found_resume = False
    words = Queue.Queue()

    # 对文件的每一行进行迭代
    for word in raw_words:

        word = word.rstrip()

        if resume is not None:

            if found_resume:
                words.put(word)
            else:
                if word == resume:
                    found_resume = True
                    print "Resuming wordlist from: %s" % resume

        else:
            words.put(word)

    # 完毕后返回一个队列
    return words


def dir_bruter(extensions=None):
    while not word_queue.empty():
        attempt = word_queue.get()

        attempt_list = []

        # 检查是否有文件拓展名,如果没有,那么就是我们要暴力破解的路径
        if "." not in attempt:
            attempt_list.append("/%s/" % attempt)
        else:
            attempt_list.append("/%s" % attempt)

        # 如果要暴力拓展,此时使用字典字符对添加每一个想要测试的文件拓展名进行测试
        if extensions:
            for extension in extensions:
                attempt_list.append("/%s%s" % (attempt, extension))

        # 迭代尝试列表
        for brute in attempt_list:

            url = "%s%s" % (target_url, urllib.quote(brute))

            try:
                # 添加User-Agent测试远程服务器
                headers = {"User-Agent": user_agent}
                r = urllib2.Request(url, headers=headers)

                response = urllib2.urlopen(r)

                if len(response.read()):
                    print "[%d] => %s" % (response.code, url)

            except urllib2.HTTPError, e:

                if e.code != 404:
                    print "!!! %d => %s" % (e.code, url)

                pass


word_queue = build_wordlist(wordlist_file)
extensions = [".php", ".bak", ".orig", ".inc"]

for i in range(threads):
    t = threading.Thread(target=dir_bruter, args=(extensions,))
    t.start()
