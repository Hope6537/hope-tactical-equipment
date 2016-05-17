# coding=utf-8
"""
使用WMI监视进程
"""
import os

import win32api
import win32con
import win32security
import wmi

LOG_FILE = "process_monitor_log.csv"


def get_process_privileges(pid):
    """
    提升当前操作访问权限
    :param pid:
    :return:
    """
    try:
        # 获取目标文件的句柄
        hproc = win32api.OpenProcess(win32con.PROCESS_QUERY_INFORMATION, False, pid)

        # 打开主进程的令牌
        htok = win32security.OpenProcessToken(hproc, win32con.TOKEN_QUERY)

        # 解析已启动权限的列表
        privs = win32security.GetTokenInformation(htok, win32security.TokenPrivileges)

        # 迭代每个权限并输出其中已经启用的
        priv_list = []
        for priv_id, priv_flags in privs:
            # 检查权限是否已经启动
            if priv_flags == 3:
                priv_list.append(win32security.LookupPrivilegeName(None, priv_id))

    except:
        priv_list.append("N/A")

    return "|".join(priv_list)


def log_to_file(message):
    fd = open(LOG_FILE, "ab")
    fd.write("%s\r\n" % message)
    fd.close()

    return


def driver():
    """
    创建日志文件并初始化WMI接口,打开进程监控器
    :return:
    """
    if not os.path.isfile(LOG_FILE):
        log_to_file("Time,User,Executable,CommandLine,PID,ParentPID,Privileges")

    # 初始化WMI接口
    c = wmi.WMI()

    # 打开进程监控器
    process_watcher = c.Win32_Process.watch_for("creation")

    while True:
        try:
            new_process = process_watcher()

            proc_owner = new_process.GetOwner()
            proc_owner = "%s\\%s" % (proc_owner[0], proc_owner[2])
            create_date = new_process.CreationDate
            executable = new_process.ExecutablePath
            cmdline = new_process.CommandLine
            pid = new_process.ProcessId
            parent_pid = new_process.ParentProcessId

            privileges = get_process_privileges(pid)

            process_log_message = "%s,%s,%s,%s,%s,%s,%s" % (
                create_date, proc_owner, executable, cmdline, pid, parent_pid, privileges)

            print "%s\r\n" % process_log_message

            log_to_file(process_log_message)

        except:
            pass
