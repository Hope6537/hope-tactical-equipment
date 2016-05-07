# encoding:utf-8
import os


def generate(project_name):
    """
    要生成一个工程,必须有几个东西
    1.工程名,用来决定文件夹前缀
    2.各个层次文件夹,内部层面基本大同小异,都是经典的maven形态,外部文件夹主要分为
    pom.xml -> 复制依赖
    [p]-dependency -> 复制当前工程
    [p]-relation-database
    [p]-service-default-implement
    [p]-service-restful-client
    [p]-service-rpc-deploy
    [p]-share-module
        [p]-service
        [p]-tools ->  此文件夹下属所有的类全部复制
    3.在各个层次文件夹中构建pom.xml,源数据来自当前hope-tactical-equipment,以便之后随时更新
    使用字符串替换的方式来更改
    groupId -> org.hope6537 该成目标包名
    artifactId -> 改成工程名 + 后缀 (除了我貌似没人以[hope-]的artifactId开头)
    4.包名比较好做,将所有的org.hope6537或com.comichentai换成 目标包名即可
    5.然后运行老版本的generator,将do->test的所有代码生成并移动
    6.需要新加一个controller模板类
    """
    base_dir = os.getcwd()
