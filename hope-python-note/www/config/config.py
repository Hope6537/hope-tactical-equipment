# encoding:UTF-8
from heapq import merge

import config_default

__author__ = 'Hope6537'
# config.py
configs = config_default.configs

try:
    import config_override

    configs = merge(configs, config_override.configs)
except ImportError:
    pass