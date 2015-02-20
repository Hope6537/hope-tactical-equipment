hope6537-utils
==============
常用的POM依赖和一些工具类，使用Maven进行模块划分，层次结构如下

hope6537-utils(parent)
    1.2-RELEASE
    ——>hope-basic-utils 基本工具类模块，可以随处引用，集成父模块依赖，自身无特殊依赖
        ->org.hope6537
            ->context 应用程序上下文，常量
            ->datastruct 自定义数据结构
            ->date 时间日期类
            ->db 数据库工具类
            ->file I/O操作工具类
            ->http 使用OKHttp框架的http工具类
            ->math 简单可用算法
            ->reflect 反射工具类
            ->security MD5，AES，DES加密
            ->string 正则表达式，字符串工具类
    ——>hope-code-note 部分学习笔记
        ->org.hope6537.note
            ->design 设计模式笔记
            ->effectivejava 《Effective Java》笔记
            ->java8 jdk8新特性
            ->thinking_in_java 《Thinking in Java》笔记
            ->acm ACM刷题记录
    ——>hope-hadoop-utils hadoop工具类和笔记
        ->org.hope6537.hadoop
            ->hbase HBASE工具类(TODO:查询和连接查询)
            ->hdfs HDFS工具类
            ->inverseindex 倒排索引
            ->mr 测试MapReduce
            ->recommend 协同过滤推荐算法
            ->rpc 测试RPCServer和Client
            ->sort MapReduce特殊数据排序
            ->station 基站数据分析
            ->tel 电话抽取
            ->textfitler 文章分类器-分词步骤
    ——>hope-python-note python工程和笔记
    ——>hope-web-utils hibernate(myBatis)+spring(MVC)构建模块和web工具类以及资源配置文件
        ->org.hope6537
            ->ajax Ajax工具类，异步交互前端发送对象
            ->autocomplete 前端自动补全后台业务
            ->date 时间工具
            ->db Hibernate(myBatis)框架初始化
            ->email 邮件工具类
            ->file 文件上传工具类
            ->log 日志切面和工具类
            ->lucene 全文检索工具类
            ->page 分页工具类
            ->spring Spring框架工具类
        ->resource 资源文件
            ->hibernate
            ->mybatis
            ->spring
            ->web


