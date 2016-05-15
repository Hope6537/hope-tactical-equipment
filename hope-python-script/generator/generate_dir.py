# encoding:UTF-8
import os


def generate():
    if not os.path.exists("./dao"):
        os.mkdir("./dao")
    if not os.path.exists("./dataobject"):
        os.mkdir("./dataobject")
    if not os.path.exists("./datamapper"):
        os.mkdir("./datamapper")
    if not os.path.exists("./dto"):
        os.mkdir("./dto")
    if not os.path.exists("./impl"):
        os.mkdir("./impl")
    if not os.path.exists("./service"):
        os.mkdir("./service")
    if not os.path.exists("./impl"):
        os.mkdir("./impl")
    if not os.path.exists("./test"):
        os.mkdir("./test")
    if not os.path.exists("./controller"):
        os.mkdir("./controller")
