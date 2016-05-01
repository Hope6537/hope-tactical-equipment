# coding=utf-8
# author=hope6537
# 目标：创建一个客户端和服务端用于传输文件,执行命令等..etc

import getopt
import socket
import subprocess
import sys
import threading

# 定义一些全局变量
listen = False
command = False
upload = False
execute = ""
target = ""
upload_destination = ""
port = 0


# 运行命令
def run_command(command):
    # 换行处理
    command = command.rstrip()
    try:
        # 从子线程获取输出
        # subprocess提供了很多线程创建借口,提供多种与客户端程序交互的方法
        output = subprocess.check_output(command, stderr=subprocess.STDOUT, shell=True)
    except:
        output = "Failed to execute command.\r\n"
    return output


# 对客户端请求的处理
def client_handler(client_socket):
    global upload
    global execute
    global command

    # 检测上传文件
    if len(upload_destination):
        # 读取字符串
        file_buffer = ""
        while True:
            data = client_socket.recv(1024)
            if not data:
                break
            else:
                file_buffer += data

        # 接受数据并写回IO
        try:
            # 二进制格式写入文件
            file_descriptor = open(upload_destination, "wb")
            file_descriptor.write(file_buffer)
            file_descriptor.close()
            # 确认是否已经写入
            client_socket.send("Successfully save the file to %s\r\n" % upload_destination)
        except:
            client_socket.send("Failed to save the file to %s\r\n" % upload_destination)

    # 检查命令执行
    if len(execute):
        output = run_command(execute)
        client_socket.send(output)

    # 如果执行shell
    if command:
        while True:
            client_socket.send("<HOPE:#> ")
            command_buffer = ""
            while "\r\n" not in command_buffer:
                command_buffer += client_socket.recv(1024)
            response = run_command(command_buffer)
            client_socket.send(response)


# 创建服务端循环和子函数,对命令行shell的创建和命令的执行进行处理
def server_loop():
    global target
    global port
    if not len(target):
        target = "0.0.0.0"
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((target, port))
    server.listen(5)
    while True:
        # 通过相应请求获取请求信息
        client_socket, addr = server.accept()
        # 分拆一个线程处理新的客户端
        client_thread = threading.Thread(target=client_handler, args=(client_socket,))
        client_thread.start()


# 客户端发送
def client_sender(buffer):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 连接到目标主机
    try:
        client.connect((target, port))
        # 首先检查是否已经得到输入
        if len(buffer):
            client.send(buffer)
        while True:
            # 等待数据返回
            recv_len = 1
            response = ""
            # 如果一切正常,那么发送数据到远程主机
            while recv_len:
                data = client.recv(4096)
                recv_len = len(data)
                response += data
                if recv_len < 4096:
                    break
            print(response)
            # 等待用户的下一步输入或者退出
            buffer = raw_input("")
            buffer += "\n"
            # 最后发送出去
            client.send(buffer)

    except:
        print("[*] Exception! Exiting.....")
        client.close()


# 创建主函数处理命令行参数和调用其他自定义函数

def usage():
    print("===Hope6537 Net Tool====")
    print("")
    print("Usage:netcaty.py - t target_host - p port")
    print("-l - -listen")
    print("-e - -execute=file_to_run")
    print("-c - -command")
    print("-u - -upload=destination")
    print("-------------")
    print("Examples:")
    print("netcaty.py - t192.168.1.1 - p 8080 -l -u = / usr / local / share / target")
    print("echo ‘ADBD’ | ./netcaty.py - t 192.168.11.12 - p 123")
    sys.exit(0)


def main():
    global listen
    global port
    global execute
    global command
    global upload_destination
    global target

    # 提示使用方式
    if not len(sys.argv[1:]):
        usage()

    # 读取命令行选项
    try:
        opts, args = getopt.getopt(sys.argv[1:], "hle:t:p:cu:",
                                   ["help", "listen", "execute", "target", "port", "command", "upload"])
    except getopt.GetoptError as err:
        print str(err)
        usage()

    # 操作全局变量
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

    # 我们是进行监听还是从标准输入发送数据
    # 通过网络发送数据
    if not listen and len(target) and port > 0:
        # 从输入获取数据,这里将会进行IO阻塞
        # 如果需要交互式发送数据,需要进行CTRL-D来避免从标准输入中读取
        buffer = sys.stdin.read()
        # 发送数据
        client_sender(buffer)

    # 监听并准备发送文件,执行命令
    if listen:
        server_loop()


# 程序入口
main()
