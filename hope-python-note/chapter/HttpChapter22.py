# encoding:UTF-8
__author__ = 'Hope6537'


# hello.py
# 无论多么复杂的Web应用程序，入口都是一个WSGI处理函数。
# HTTP请求的所有输入信息都可以通过environ获得，HTTP响应的输出都可以通过start_response()加上函数返回值作为Body。
def application2(environ, start_response):
    start_response('200 OK', [('Content-Type', 'text/html')])
    return '<h1>Hello, %s!</h1>' % (environ['JAVA_HOME'][1:] or 'web')


# server.py
# 从wsgiref模块导入:
from wsgiref.simple_server import make_server

# 导入我们自己编写的application函数:
# 创建一个服务器，IP地址为空，端口是8000，处理函数是application:
httpd = make_server('', 8000, application2)
print "Serving HTTP on port 8000..."
# 开始监听HTTP请求:
httpd.serve_forever()
