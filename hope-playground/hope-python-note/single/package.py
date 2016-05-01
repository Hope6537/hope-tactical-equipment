# encoding:UTF-8
__author__ = 'Hope6537'

# !/usr/bin/env python
# -*- coding: utf-8 -*-

from distutils.core import setup

includes = ["encodings", "encodings.*"]
data_files = ['CameraDll.dll']
options = {"py2exe":
               {"compressed": 1,
                "optimize": 2,
                "bundle_files": 1,
                "includes": includes

                }
           }

setup(
        version="1.0",
        description="Screenshot Tool",
        name="Screenshot  Tool",
        options=options,
        zipfile=None,
        data_files=data_files,
        console=[{"script": "Screenshot.py", "icon_resources": [(1, "Screenshot.ico")]}]
)
