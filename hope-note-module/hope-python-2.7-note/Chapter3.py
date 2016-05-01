# encoding:utf-8
import math
import functools

__author__ = 'Hope6537'
# Python 函數式編程
x = abs(-2222);
print(x);
# 函数的传递
f = abs;
x = f(-2222);
print(x);


def add(x, y, func):
    return func(x) + func(y)


print(add(1, -2, abs))
# Python 的Map和Reduce

def pow2(x):
    return x * x;


def mutli(x, y):
    return (x + y)


list = range(0, 11);
list = map(pow2, list);
print(list)
value = reduce(mutli, list);
print(value);


def str2int(s):
    def fn(x, y):
        return x * 10 + y

    def char2num(s):
        return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]

    return reduce(fn, map(char2num, s))


print(str2int("11111") + 1);
# 还可以使用lambda表达式
def char2num(s):
    return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]


def str2int(s):
    return reduce(lambda x, y: x * 10 + y, map(char2num, s))


print(str2int("11111") + 1);
# 用户输入的不规范的英文名字，变为首字母大写，其他小写的规范名字
def formatName(name):
    return name[0:1].upper() + name[1:].lower()


def mapName(nameList):
    return map(formatName, nameList)


print(mapName(['adam', 'LISA', 'barT']))
# 请编写一个prod()函数，可以接受一个list并利用reduce()求积。

def mutli(x, y):
    return x * y;


def prod(list):
    return reduce(mutli, list);


print(prod(range(1, 22)))
# fitler 过滤器,可以以布尔返回值的函数作为参照，从而传递给过滤器
def not_empty(s):
    return s and s.strip()


print(filter(not_empty, ['A', '', 'B', None, 'C', '  ']));


def not_prime(num):
    if (num <= 5):
        return False
    for v in range(2, int(math.sqrt(num))):
        if num % v == 0:
            return True;

    return False;


print(filter(not_prime, range(1, 100)))
# sorted函數
print sorted([36, 5, 12, 9, 21]);
# 可以使用自定义的[Java中叫做Comparable接口]
def reversed_cmp(x, y):
    if x > y:
        return -1
    if x < y:
        return 1
    return 0


print(sorted(range(0, 100), reversed_cmp))


def cmp_ignore_case(s1, s2):
    u1 = s1.upper()
    u2 = s2.upper()
    if u1 < u2:
        return -1
    if u1 > u2:
        return 1
    return 0


print(sorted(['bob', 'about', 'Zoo', 'Credit'], cmp_ignore_case))

# Python 闭包
def lazy_sum(*args):
    def sum():
        sumValue = 0
        for items in args:
            sumValue += items
        return sumValue;

    return sum;


# 当我们调用lazy_sum()时，每次调用都会返回一个新的函数，即使传入相同的参数
# 两个函数互不影响，他们都独占一块内存空间
# 但是返回闭包时牢记的一点就是：返回函数不要引用任何循环变量，或者后续会发生变化的变量。
f = lazy_sum(1, 3, 5, 7, 9);
print(f);
print(f());
# 例如下面的例子
def count():
    fs = []
    for i in range(1, 4):
        def f():
            return i * i

        fs.append(f)
    return fs


f1, f2, f3 = count()
# 因為重複使用到了循環變量i 创建f1,f2,f3的时候就进入到了循环体的内部完成了3次循环
print(f1())
print(f2())
print(f3())


def count_no_index():
    fs = []
    for i in range(1, 4):
        def f(j):
            def g():
                return j * j

            return g

        fs.append(f(i))
    return fs


f1, f2, f3 = count_no_index()
print(f1())
print(f2())
print(f3())

# 匿名函數 使用lambda表达式
# 该表达式返回（x*x并返回結果的）函數！
# 注意是函數 function
list = map(lambda x: x * x, [1, 2, 3, 4, 5, 6, 7, 8, 9]);
print(list)
f = lambda x: x * x;
print(f(222));
# 同样我们可以将匿名函数作为返回值返回
# 注意！該方法返回的是函數
def build(x, y):
    return lambda: x * x + y * y


print(build(1, 2)())
# 装饰器模式
# def now():
# print '2015-2-14 19:24:17'

# 函数对象有一个__name__属性，可以拿到函数的名字：
# 假设我们要增强now()函数的功能，比如，在函数调用前后自动打印日志，
# 但又不希望修改now()函数的定义，这种在代码运行期间动态增加功能的方式，称之为“装饰器”（Decorator）




def log(func):
    def wrapper(*args, **kw):
        print 'call %s():' % func.__name__
        return func(*args, **kw)

    return wrapper


# 观察上面的log，因为它是一个decorator，所以接受一个函数作为参数，并返回一个函数。
# 我们要借助Python的@语法，把decorator置于函数的定义处：

@log
def now():
    print '2015-2-14 19:24:14'

# 现在调用
now()
# wrapper()函数的参数定义是(*args, **kw)，因此，wrapper()函数可以接受任意参数的调用。
# 在wrapper()函数内，首先打印日志，再紧接着调用原始函数。

# decorator本身需要传入参数，那就需要编写一个返回decorator的高阶函数

def log(text):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kw):
            print '%s %s():' % (text, func.__name__)
            return func(*args, **kw)

        return wrapper

    return decorator


@log('execute_text')
def now():
    print '2015-2-14 19:24:14'


# 整個函數相當於這樣 now = log('execute')(now)
now()
# 但是这个时候函数的名称就变化了 变成了wrapper
print(now.__name__)
# 所以我们需要添加@functools.wraps(func)注解来让函数的名称能够在wrapper中传递下去


# 请编写一个decorator，能在函数调用的前后打印出'begin call'和'end call'的日志。

def namespaceLog(*text):
    def decorator(func):
        @functools.wraps(func)
        def wrapper(*args, **kwargs):
            print("begin call")
            if (text):
                print("%s" % text)
            res = func(*args, **kwargs)
            print("end call")
            return res

        return wrapper

    return decorator


@namespaceLog()
def now():
    print '2015-2-14 19:24:14'


@namespaceLog("text")
def now():
    print '2015-2-14 19:24:14'


now()

# 偏函数的作用是 通过设定参数的默认值，可以降低函数调用的难度
# 我们常用的做法
# def int2(x, base=2):
# return int(x, base)
# functools.partial这个专门用来做偏函数设置的
int2 = functools.partial(int, base=2)
print(int2('1000000'))
print(int2('10000011'))
# 当函数的参数个数太多，需要简化时，使用functools.partial可以创建一个新的函数
# 这个新函数可以固定住原函数的部分参数，从而在调用时更简单。