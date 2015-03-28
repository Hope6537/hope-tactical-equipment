# encoding:UTF-8
from multiprocessing import Process, Pool, Queue
import os
import random
import time

__author__ = 'Hope6537'
# multiprocessing模块提供了一个Process类来代表一个进程对象，下面的例子演示了启动一个子进程并等待其结束：
def run_proc(name):
    print 'Run child process %s (%s)...' % (name, os.getpid())


if __name__ == '__main__':
    print 'Parent process %s.' % os.getpid()
    # 创建进程对象
    p = Process(target=run_proc, args=('test',))
    print 'Process will start.'
    p.start()
    # 相当于同步，等待子进程结束后再接着运行
    p.join()
    print 'Process end.'

# 如果需要启动大量的子进程，那么进程池走你
def long_time_task(name):
    print 'Run task %s (%s)...' % (name, os.getpid())
    start = time.time()
    time.sleep(random.random() * 3)
    end = time.time()
    print 'Task %s runs %0.2f seconds.' % (name, (end - start))

# 请注意输出的结果，task 0，1，2，3是立刻执行的，而task 4要等待前面某个task完成后才执行，
# 这是因为Pool的默认大小在我的电脑上是4，因此，最多同时执行4个进程。这是Pool有意设计的限制，并不是操作系统的限制

if __name__ == '__main__':
    print 'Parent process %s.' % os.getpid()
    # 在这里设定最多可以执行子进程的数量
    p = Pool(2)
    for i in range(5):
        p.apply_async(long_time_task, args=(i,))
    print 'Waiting for all subprocesses done...'
    # 关闭池
    p.close()
    # 等待完成
    p.join()
    print 'All subprocesses done.'

# Process之间肯定是需要通信的，操作系统提供了很多机制来实现进程间的通信。
# Python的multiprocessing模块包装了底层的机制，提供了Queue队列、Pipes管道等多种方式来交换数据。

# 写数据进程执行的代码:
def write(q):
    for value in ['A', 'B', 'C']:
        print 'Put %s to queue...' % value
        q.put(value)
        time.sleep(random.random())


# 读数据进程执行的代码:
def read(q):
    while True:
        value = q.get(True)
        print 'Get %s from queue.' % value

# multiprocessing需要“模拟”出fork的效果，父进程所有Python对象都必须通过pickle序列化再传到子进程去
# 所有，如果multiprocessing在Windows下调用失败了，要先考虑是不是pickle失败了。
if __name__ == '__main__':
    # 父进程创建Queue，并传给各个子进程：
    q = Queue()
    pw = Process(target=write, args=(q,))
    pr = Process(target=read, args=(q,))
    # 启动子进程pw，写入:
    pw.start()
    # 启动子进程pr，读取:
    pr.start()
    # 等待pw结束:
    pw.join()
    # pr进程里是死循环，无法等待其结束，只能强行终止:
    pr.terminate()


