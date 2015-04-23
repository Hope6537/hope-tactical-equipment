# encoding:utf-8
# !/usr/bin/env python

# Python语法层面
__author__ = 'Hope6537'

print "Hi,My name is %s , I am %d years old " % ("hope6537", 20)

programLanguages = ["java", "c#", "c++"];
programLanguages.append("python")
programLanguages.insert(1, "javascript")
programLanguages.pop()
programLanguages[2] = "c"
print programLanguages
print programLanguages[0]

print "please input your number"
age = int(raw_input())
if age >= 20:
    print "yes old man", age
else:
    print "yes teenager", age

names = ['Michael', 'Bob', 'Tracy']
for name in names:
    print name

sum = 0
for x in range(101):
    sum = sum + x
print sum

sum = 0
n = 1
while n <= 100:
    sum = sum + n
    n = n + 1
print sum

d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
print d['Michael']
print 'Thomas' in d
print 1 > 2 and 2 < 3

# 参数定义的顺序必须是：必选参数、默认参数、可变参数和关键字参数。

# 必選参数
def my_abs(x):
    if x >= 0:
        return x, x
    else:
        return -x, x


value, origin = my_abs(-12)
print value
print origin

#可变参数
def calc(*numbers):
    sum = 0
    for n in numbers:
        sum = sum + n * n
    return sum


print(calc(1, 2, 3, 4, 5))

#默認參數
def enroll(name, gender, age=6, city='Beijing'):
    print 'name:', name
    print 'gender:', gender
    print 'age:', age
    print 'city:', city


enroll('Sarah', 'F')
enroll('Bob', 'M', 7)
enroll('Adam', 'M', city='Tianjin')

#关键字参数 即里面是一个tuple
def person(name, age, **kw):
    print 'name:', name, 'age:', age, 'other:', kw


person('Michael', 30);
person('Bob', 35, city='Beijing')
person('Adam', 45, gender='M', job='Engineer')
kw = {'city': 'Beijing', 'job': 'Engineer'}
person('Jack', 24, **kw)

