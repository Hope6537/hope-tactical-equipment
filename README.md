hope6537-utils
==============
常用的POM依赖和一些工具类，使用Maven进行模块划分，层次结构如下<br/>

* hope6537-utils(parent)<br/>
    * 1.2-RELEASE<br/>
    * —>hope-basic-utils 基本工具类模块，可以随处引用，集成父模块依赖，自身无特殊依赖<br/>
        * ->org.hope6537<br/>
            * ->context 应用程序上下文，常量<br/>
            * ->datastruct 自定义数据结构<br/>
            * ->date 时间日期类<br/>
            * ->db 数据库工具类<br/>
            * ->file I/O操作工具类<br/>
            * ->http 使用OKHttp框架的http工具类<br/>
            * ->math 简单可用算法<br/>
            * ->reflect 反射工具类<br/>
            * ->security MD5，AES，DES加密<br/>
            * ->string 正则表达式，字符串工具类<br/>
    * —>hope-code-note 部分学习笔记<br/>
        * ->org.hope6537.note<br/>
            * ->design 设计模式笔记<br/>
            * ->effectivejava 《Effective Java》笔记<br/>
            * ->java8 jdk8新特性<br/>
            * ->thinking_in_java 《Thinking in Java》笔记<br/>
            * ->acm ACM刷题记录<br/>
    * —>hope-hadoop-utils hadoop工具类和笔记<br/>
        * ->org.hope6537.hadoop<br/>
            * ->hbase HBASE工具类(TODO:查询和连接查询)<br/>
            * ->hdfs HDFS工具类<br/>
            * ->inverseindex 倒排索引<br/>
            * ->mr 测试MapReduce<br/>
            * ->recommend 协同过滤推荐算法<br/>
            * ->rpc 测试RPCServer和Client<br/>
            * ->sort MapReduce特殊数据排序<br/>
            * ->station 基站数据分析<br/>
            * ->tel 电话抽取<br/>
            * ->textfitler 文章分类器-分词步骤<br/>
    * —>hope-python-note python工程和笔记<br/>
    * —>hope-web-utils hibernate(myBatis)+spring(MVC)构建模块和web工具类以及资源配置文件<br/>
        * ->org.hope6537<br/>
            * ->ajax Ajax工具类，异步交互前端发送对象<br/>
            * ->autocomplete 前端自动补全后台业务<br/>
            * ->date 时间工具<br/>
            * ->db Hibernate(myBatis)框架初始化<br/>
            * ->email 邮件工具类<br/>
            * ->file 文件上传工具类<br/>
            * ->log 日志切面和工具类<br/>
            * ->lucene 全文检索工具类<br/>
            * ->page 分页工具类<br/>
            * ->spring Spring框架工具类<br/>
        * ->resource 资源文件<br/>
            * ->hibernate<br/>
            * ->mybatis<br/>
            * ->spring<br/>
            * ->web<br/>


