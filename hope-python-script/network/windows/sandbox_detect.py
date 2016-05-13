# coding=utf-8
"""
沙盒环境检测
"""
import ctypes
import random
import sys
import time

user32 = ctypes.windll.user32
kernel32 = ctypes.windll.kernel32

keystrokes = 0
mouse_clicks = 0
double_clicks = 0


class LASTINPUTINFO(ctypes.Structure):
    _fields_ = [("cbSize", ctypes.c_uint),
                ("dwTime", ctypes.c_ulong)
                ]


def get_last_input():
    struct_lastinputinfo = LASTINPUTINFO()
    # 初始化并将其设置为结构体的大小
    struct_lastinputinfo.cbSize = ctypes.sizeof(LASTINPUTINFO)

    # 获得用户最后输入的信息,填充了该函数
    user32.GetLastInputInfo(ctypes.byref(struct_lastinputinfo))

    # 获得机器运行的时间
    run_time = kernel32.GetTickCount()

    elapsed = run_time - struct_lastinputinfo.dwTime

    print "[*] It's been %d milliseconds since the last input event." % elapsed

    return elapsed


def simple_driver():
    """
    该方法用于确定脚本启动期间 鼠标轨迹,键盘按键操作的最后一次被触发的时间
    :return:
    """
    while True:
        get_last_input()
        time.sleep(1)


def get_key_press():
    global mouse_clicks
    global keystrokes

    for i in range(0, 0xff):
        # 通过GetAsyncKeyState对每个键位进行检查,以确定是否被按下
        if user32.GetAsyncKeyState(i) == -32767:

            # 左键点击的事件地址是0x1
            if i == 1:
                # 是的话就加一
                mouse_clicks += 1
                return time.time()
            else:
                # 否则检查按键时间
                keystrokes += 1

    return None


def detect_sandbox():
    global mouse_clicks
    global keystrokes

    # 追踪用户的点击
    max_keystrokes = random.randint(10, 25)
    max_mouse_clicks = random.randint(5, 25)

    double_clicks = 0
    max_double_clicks = 10
    double_click_threshold = 0.250
    first_double_click = None

    average_mousetime = 0
    max_input_threshold = 30000

    previous_timestamp = None
    detection_complete = False

    # 得到最后一次输入经过的时间
    last_input = get_last_input()

    # 超过设定的阈值则强制退出
    if last_input >= max_input_threshold:
        sys.exit(0)

    while not detection_complete:
        # 检查鼠标点击或者键盘时间,如果返回了一个值,那就是点击的时间
        keypress_time = get_key_press()
        if keypress_time is not None and previous_timestamp is not None:

            # 然后计算两次点击相隔的时间
            elapsed = keypress_time - previous_timestamp

            # 如果间隔较短,就是用户双击
            if elapsed <= double_click_threshold:
                double_clicks += 1

                if first_double_click is None:

                    # 获得第一次双击的时间
                    first_double_click = time.time()

                else:
                    # 如果短时间出现了超多的鼠标点击事件,很可能不是用户人肉进行的
                    if double_clicks == max_double_clicks:
                        if keypress_time - first_double_click <= (max_double_clicks * double_click_threshold):
                            sys.exit(0)

            # 用户的输入次数达到设定的条件
            if keystrokes >= max_keystrokes and double_clicks >= max_double_clicks and mouse_clicks >= max_mouse_clicks:
                # 如果通过了沙盒校验,退出
                return

            previous_timestamp = keypress_time

        elif keypress_time is not None:
            previous_timestamp = keypress_time


detect_sandbox()
# simple_driver()
print "We are ok!"
