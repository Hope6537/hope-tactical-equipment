# encoding:utf-8
__author__ = 'Hope6537'
# Python高级特性
# 切片

# ->首先我们定义一个List
L = ['Michael', 'Sarah', 'Tracy', 'Bob', 'Jack']

# ->取前3个元素
print(L[0:3])
print(L[:3])

# ->取倒數元素
print(L[-2:-1])

# ->同样也适用于tuple和字符串
print((0, 1, 2, 3, 4, 5)[:3])
print('ABCDEFG'[:3])

# 迭代
d = {'a': 1, 'b': 2, 'c': 3};
for key in d:
    print(key);
    print(d.get(key))
# ->那么，如何判断一个对象是可迭代对象呢？方法是通过collections模块的Iterable类型判断
from collections import Iterable

print isinstance('abc', Iterable)  # str是否可迭代
print isinstance([1, 2, 3], Iterable)  # list是否可迭代
print isinstance(123, Iterable)  # int是否可迭代

for i, value in enumerate(['A', 'B', 'C']):
    print i, value
    # ->i代表一个类似于int i = 0 ;的下标


# 列表生成式
# ->最常见的range函数
list = range(0, 10)
print(list)
# ->但是面对复杂情况的时候，我们可以采用这样的操作
# ->需要对生成的数据进行计算 计算表达式后面接上for循环
list = [x * x for x in range(1, 11)];
print(list)
print(list)
# ->也可以按照条件进行生成
#->for循环后面还可以加上if判断，这样我们就可以筛选出偶数
list = [x for x in range(1, 11) if x % 2 == 0]
print(list)
#->还可以使用两层循环，可以生成全排列 即声明两个for
list = [m + n for m in 'ABC' for n in 'XYZ']
print(list)
#->运用列表生成式，可以写出非常简洁的代码。例如，列出当前目录下的所有文件和目录名，可以通过一行代码实现
import os

list = [d for d in os.listdir('C:/')]  # os.listdir可以列出文件和目录
print("->C:/")
for value in list:
    print("->>" + value);
#->for循环可以使用多个变量
d = {'x': 'A', 'y': 'B', 'z': 'C'}
#->写法1
for key, value in d.iteritems():
    print(key + "->" + value)
#->写法2
print([k + '<->' + v for k, v in d.iteritems()]);
#->还可以传递函数
print([s.upper() for s in d])

#生成器
#为了不在生成的时候浪费过多的内存，可以使用生成器来边迭代边生成数据，避免数据过多导致溢出
g = (x * x for x in range(10))
print(g.next())
print(g)  #output : <generator object <genexpr> at 0x02056FD0>
#->一口气全部生成，实际是不是这样的。而是迭代到了就生成而已
for generatorValue in g:
    print(generatorValue)
#->那么这样斐波那契数列简直迅速生成
def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        yield b  #yield -> 定义generator的另一种方法
        a, b = b, a + b
        n = n + 1

#这就是定义generator的另一种方法。如果一个函数定义中包含yield关键字，那么这个函数就不再是一个普通函数，而是一个generator
print([x for x in fib(12)]);
#而此时函数的运行方式也发生了变化
#变成generator的函数，在每次调用next()的时候执行，遇到yield语句返回，再次执行时从上次返回的yield语句处继续执行。

