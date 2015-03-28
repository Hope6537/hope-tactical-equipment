# encoding:utf-8
__author__ = 'Hope6537'


class Userinfo(object):
    def __init__(self, username, password):
        self._username = username
        self._password = password

    __slots__ = ('_username', '_password', "_score")
    # 用tuple定义允许绑定的属性名称
    # 使用__slots__要注意，__slots__定义的属性仅对当前类起作用，对继承的子类是不起作用的

    def toString(self):
        print("Userinfo")
        print("username is %s" % self._username)
        print("password is %s" % self._password)

    # 默认的toString
    def __str__(self):
        return 'Userinfo object (username: %s)' % self._username

    # 有時候不可用 还需要覆盖一个repr
    _repr__ = __str__

    def getUsername(self):
        if hasattr(self, '_username'):
            return self._username
        return None


# 注意到这个神奇的@property，我们在对实例属性操作的时候，就知道该属性很可能不是直接暴露的，而是通过getter和setter方法来实现的。
# 还可以定义只读属性，只定义getter方法，不定义setter方法就是一个只读属性
class Student(object):
    @property
    def score(self):
        return self._score

    @score.setter
    def score(self, value):
        if not isinstance(value, int):
            raise ValueError('score must be an integer!')
        if value < 0 or value > 100:
            raise ValueError('score must between 0 ~ 100!')
        self._score = value

    # 当我们取一个不存在的属性的时候，就会抛出一个异常
    # 要避免这个错误，除了可以加上一个score属性外，Python还有另一个机制，那就是写一个__getattr__()方法，动态返回一个属性。修改如下：
    def __getattr__(self, attr):
        if attr == 'money':
            return 0

    def __init__(self, name):
        self._name = name

    # 任何类，只需要定义一个__call__()方法，就可以直接对实例进行调用。请看示例：
    def __call__(self):
        print('My name is %s.' % self._name)


class Fib(object):
    # 定制类
    # 如果一个类想被用于for ... in循环，类似list或tuple那样，就必须实现一个__iter__()方法，该方法返回一个迭代对象，
    # 然后，Python的for循环就会不断调用该迭代对象的next()方法拿到循环的下一个值，直到遇到StopIteration错误时退出循环。
    def __init__(self):
        self.a, self.b = 0, 1  # 初始化两个计数器a，b

    def __iter__(self):
        return self  # 实例本身就是迭代对象，故返回自己

    # 覆盖写入next
    def next(self):
        self.a, self.b = self.b, self.a + self.b  # 计算下一个值
        if self.a > 100000:  # 退出循环的条件
            raise StopIteration()
        return self.a  # 返回下一个值

    # def __getitem__(self, n):
    # a, b = 1, 1
    # for x in range(n):
    # a, b = b, a + b
    # return a

    # 可以使用切片的方法
    def __getitem__(self, n):
        # 如果要的只是一個数
        if isinstance(n, int):
            a, b = 1, 1
            for x in range(n):
                a, b = b, a + b
            return a
        # 当传入的是一个切片的时候
        if isinstance(n, slice):
            start = n.start
            stop = n.stop
            a, b = 1, 1
            L = []
            for x in range(stop):
                if x >= start:
                    # 在list中添加数据
                    L.append(a)
                a, b = b, a + b
            return L
            # 也没有对负数作处理，所以，要正确实现一个__getitem__()还是有很多工作要做的。


for n in Fib():
    print n

# 但是iter只是可以迭代而已
# 要表现得像list那样按照下标取出元素，需要实现__getitem__()方法：
# 与之对应的是__setitem__()方法，把对象视作list或dict来对集合赋值。最后，还有一个__delitem__()方法，用于删除某个元素。
print(Fib()[3])
print(Fib()[0:3])
s = Student("user")
print(s.money)  # result 0


class Chain(object):
    def __init__(self, path=''):
        self._path = path

    def __getattr__(self, path):
        return Chain('%s/%s' % (self._path, path))

    def __str__(self):
        return self._path


c = Chain().status.user.timeline.list
print(c)
# c = Chain().users('michael').repos
# print(c)

s = Student('Michael')
s()
print(callable(s))

# 要创建一个class对象，type()函数依次传入3个参数：
def fn(self, name='world'):  # 先定义函数
    print('Hello, %s.' % name)

# class的名称；
# 继承的父类集合，注意Python支持多重继承，如果只有一个父类，别忘了tuple的单元素写法；
# class的方法名称与函数绑定，这里我们把函数fn绑定到方法名hello上。
# 通过type()函数创建的类和直接写class是完全一样的，因为Python解释器遇到class定义时，仅仅是扫描一下class定义的语法，然后调用type()函数创建出cla
Hello = type('Hello', (object,), dict(hello=fn))  # 创建Hello class
h = Hello()
h.hello()

# 如果我们想创建出类呢？那就必须根据metaclass创建出类，所以：先定义metaclass，然后创建类。
# 先定义metaclass，就可以创建类，最后创建实例。

# metaclass是创建类，所以必须从`type`类型派生：
class ListMetaclass(type):  # 继承与type
    # new方法接收到的参数依次是：
    # 当前准备创建的类的对象；
    # 类的名字；
    # 类继承的父类集合；
    # 类的方法集合。
    def __new__(cls, name, bases, attrs):  # 使用默認__new__函數
        attrs['add'] = lambda self, value: self.append(value)
        return type.__new__(cls, name, bases, attrs)
        # 添加的attr是要绑定的参数和方法


class MyList(list):
    __metaclass__ = ListMetaclass  # 指示使用ListMetaclass来定制类
    # 它指示Python解释器在创建MyList时，要通过ListMetaclass.__new__()来创建，在此，我们可以修改类的定义，比如，加上新的方法，然后，返回修改后的定义。


L = MyList()
L.add(1)
print(L)

# 直接在MyList定义中写上add()方法不是更简单吗？正常情况下，确实应该直接写，通过metaclass修改纯属变态

# 但是，总会遇到需要通过metaclass修改类定义的。ORM就是一个典型的例子。
# ORM全称“Object Relational Mapping”，即对象-关系映射，就是把关系数据库的一行映射为一个对象，也就是一个类对应一个表，这样，写代码更简单，不用直接操作SQL语句
# 要编写一个ORM框架，所有的类都只能动态定义，因为只有使用者才能根据表的结构定义出对应的类来
# 让我们来尝试编写一个ORM框架。
# 编写底层模块的第一步，就是先把调用接口写出来。比如，使用者如果使用这个ORM框架，想定义一个User类来操作对应的数据库表User，我们期待他写出这样的代码：


# 首先来定义Field类，它负责保存数据库表的字段名和字段类型：
class Field(object):
    def __init__(self, name, column_type):
        self.name = name
        self.column_type = column_type

    def __str__(self):
        return '<%s:%s>' % (self.__class__.__name__, self.name)


# 在Field的基础上，分别添加根據不同类型定义的field
class StringField(Field):
    def __init__(self, name):
        super(StringField, self).__init__(name, 'varchar(100)')


class IntegerField(Field):
    def __init__(self, name):
        super(IntegerField, self).__init__(name, 'bigint')


# 就是编写最复杂的ModelMetaclass了：
# 在ModelMetaclass中，一共做了几件事情：
# 排除掉对Model类的修改；
# 在当前类（比如User）中查找定义的类的所有属性，如果找到一个Field属性，就把它保存到一个__mappings__的dict中，
# 同时从类属性中删除该Field属性，否则，容易造成运行时错误；
# 把表名保存到__table__中，这里简化为表名默认为类名。
class ModelMetaclass(type):
    def __new__(cls, name, bases, attrs):
        if name == 'Model':
            return type.__new__(cls, name, bases, attrs)
        mappings = dict()
        for k, v in attrs.iteritems():
            if isinstance(v, Field):  # 转移Field属性
                print('Found mapping: %s==>%s' % (k, v))
                mappings[k] = v
        for k in mappings.iterkeys():
            attrs.pop(k)
        attrs['__table__'] = name  # 假设表名和类名一致
        attrs['__mappings__'] = mappings  # 保存属性和列的映射关系
        return type.__new__(cls, name, bases, attrs)


# 当用户定义一个class User(Model)时，
# Python解释器首先在当前类User的定义中查找__metaclass__，
# 如果没有找到，就继续在父类Model中查找__metaclass__，
# 找到了，就使用Model中定义的__metaclass__的ModelMetaclass来创建User类，
# 也就是说，metaclass可以隐式地继承到子类，但子类自己却感觉不到
# 在Model类中，就可以定义各种操作数据库的方法，比如save()，delete()，find()，update等等。
class Model(dict):
    __metaclass__ = ModelMetaclass

    def __init__(self, **kw):
        super(Model, self).__init__(**kw)

    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Model' object has no attribute '%s'" % key)

    def __setattr__(self, key, value):
        self[key] = value

    def save(self):
        fields = []
        params = []
        args = []
        for k, v in self.__mappings__.iteritems():
            fields.append(v.name)
            params.append('?')
            args.append(getattr(self, k, None))
        sql = 'insert into %s (%s) values (%s)' % (self.__table__, ','.join(fields), ','.join(params))
        print('SQL: %s' % sql)
        print('ARGS: %s' % str(args))


class User(Model):
    # 定义类的属性到列的映射：
    id = IntegerField('id')
    name = StringField('username')
    email = StringField('email')
    password = StringField('password')

# 创建一个实例：
u = User(id=12345, name='Michael', email='test@orm.org', password='my-pwd')
# 保存到数据库：
u.save()

# 因此，在编写程序的时候，千万不要把实例属性和类属性使用相同的名字。