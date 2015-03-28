# encoding:UTF-8
__author__ = 'Hope6537'
import threading

# 创建全局ThreadLocal对象:
local_school = threading.local()


def process_student():
    print 'Hello, %s (in %s)' % (local_school.student, threading.current_thread().name)


# 定义线程泛型
def process_thread(name):
    # 绑定ThreadLocal的student:
    local_school.student = name
    # 对于每个线程来说独有新的信息
    process_student()


t1 = threading.Thread(target=process_thread, args=('Alice',), name='Thread-A')
t2 = threading.Thread(target=process_thread, args=('Bob',), name='Thread-B')
t1.start()
t2.start()
t1.join()
t2.join()
# ThreadLocal最常用的地方就是为每个线程绑定一个数据库连接，HTTP请求，用户身份信息等，
# 这样一个线程的所有调用到的处理函数都可以非常方便地访问这些资源。
