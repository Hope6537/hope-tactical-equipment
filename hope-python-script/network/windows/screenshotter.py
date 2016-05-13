# coding=utf-8
"""
截取屏幕快照
"""
import time

import win32api
import win32con
import win32gui
import win32ui


def driver():
    # 获得桌面窗口的句柄
    hdesktop = win32gui.GetDesktopWindow()

    # 获取所有显示器的像素尺寸
    width = win32api.GetSystemMetrics(win32con.SM_CXVIRTUALSCREEN)
    height = win32api.GetSystemMetrics(win32con.SM_CYVIRTUALSCREEN)
    left = win32api.GetSystemMetrics(win32con.SM_XVIRTUALSCREEN)
    top = win32api.GetSystemMetrics(win32con.SM_YVIRTUALSCREEN)

    # 创建设备描述表
    desktop_dc = win32gui.GetWindowDC(hdesktop)
    img_dc = win32ui.CreateDCFromHandle(desktop_dc)

    # 创建基于内存的设备描述表
    mem_dc = img_dc.CreateCompatibleDC()

    # 创建位图对象
    screenshot = win32ui.CreateBitmap()
    screenshot.CreateCompatibleBitmap(img_dc, width, height)
    mem_dc.SelectObject(screenshot)

    # 复制屏幕到内存设备描述表中
    mem_dc.BitBlt((0, 0), (width, height), img_dc, (left, top), win32con.SRCCOPY)

    # 保存位图
    screenshot.SaveBitmapFile(mem_dc, 'c:\\screenshot-' + time.strftime("[%Y-%m-%d %H:%M:%S]",
                                                                        time.localtime(int(time.time()))) + '.bmp')

    # 释放对象
    mem_dc.DeleteDC()
    win32gui.DeleteObject(screenshot.GetHandle())
