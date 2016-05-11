# coding=utf-8
"""
用于连接SSH
前置依赖:sudo pip install paramiko
"""
import paramiko


def ssh_command(ip, user, passwd, command):
    client = paramiko.SSHClient()
    # 可以通过使用密钥认证的方式来做,但是为了方便直接上密码
    # client.load_host_keys('/home/hope6537/.ssh/known_hosts')
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(ip, username=user, password=passwd)
    ssh_session = client.get_transport().open_session()
    if ssh_session.active:
        ssh_session.exec_command(command)
        print(ssh_session.recv(1024))
    return


# 执行命令
# output:uid=0(root) gid=0(root) groups=0(root)
ssh_command("192.168.1.188", 'root', '****', 'id')
