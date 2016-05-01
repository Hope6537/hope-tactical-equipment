# encoding:UTF-8
import itertools

__author__ = 'Hope6537'

# 无限增长
# natuals = itertools.count(1)
# for x in natuals:
# print(x)

# 循环迭代内部
# cs = itertools.cycle('ABC')
# for c in cs:
# print(c)

# 可以内部重复，并指定个数
ns = itertools.repeat('A', 10)
print([x for x in ns])

# 迭代器连接器
print([c for c in itertools.chain('ABC', 'XYZ')])

# 把迭代器中相邻的重复元素挑出来放在一起：
for key, group in itertools.groupby('AAABBBCCAAA'):
    print(key, list(group))
