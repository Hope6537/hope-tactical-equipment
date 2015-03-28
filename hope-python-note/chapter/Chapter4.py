# encoding:utf-8
from types import MethodType

from org.hope6537.oop.Userinfo import Userinfo, Student


__author__ = 'Hope6537'

userinfo = Userinfo("hope6537", "password")
userinfo.toString()

print(isinstance(userinfo, Userinfo))
print(dir(userinfo))
print(hasattr(userinfo, '_username'))
print(userinfo.getUsername())


def set_score(self, score):
    self._score = score

# 给class绑定方法
Userinfo.set_score = MethodType(set_score, None, Userinfo)
userinfo.set_score(11)
print(userinfo._score)
# 給對象綁定實例方法
# userinfo.set_score = MethodType(set_score, userinfo, Userinfo)

# 为了达到限制的目的，Python允许在定义class的时候，定义一个特殊的__slots__变量，来限制该class能添加的属性
# __slots__ = ('name', 'age')

# @property
s = Student("username")
s.score = 22
# s.score = 222  # throw exception
print(s.score)
print(userinfo)



