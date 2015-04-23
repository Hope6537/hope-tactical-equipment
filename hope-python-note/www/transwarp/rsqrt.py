#!/usr/bin/env python
# encoding:UTF-8
from cmath import sqrt

__author__ = 'tempUser'


def quick_sqrt(number):
    i = 0x5f3759df
    return sqrt(i)


print(quick_sqrt(0))