# encoding:UTF-8
__author__ = 'Hope6537'

# 使用ORM工具 使用Chapter20中定义的数据库表
class User(object):
    def __init__(self, id, name):
        self.id = id
        self.name = name


[
    User('1', 'Michael'),
    User('2', 'Bob'),
    User('3', 'Adam')
]

# 第一步，导入SQLAlchemy，并初始化DBSession：
from sqlalchemy import Column, String, create_engine, ForeignKey
from sqlalchemy.orm import sessionmaker, relationship
from sqlalchemy.ext.declarative import declarative_base

# 创建对象的基类:
Base = declarative_base()

# 定义User对象:
class User(Base):
    # 表的名字:
    __tablename__ = 'user'

    # 表的结构:
    id = Column(String(20), primary_key=True)
    name = Column(String(20))

# 初始化数据库连接:
# create_engine()用来初始化数据库连接。SQLAlchemy用一个字符串表示连接信息
# '数据库类型+数据库驱动名称://用户名:口令@机器地址:端口号/数据库名'
engine = create_engine('mysql+mysqlconnector://root:*****@localhost:3306/test')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)

# 进行事务处理

# 创建session对象:
# session = DBSession()
# 创建新User对象:
# new_user = User(id='4', name='Alen')
# 添加到session:
# session.add(new_user)
# 提交即保存到数据库:
# session.commit()
# 关闭session:

# 创建Session:
session = DBSession()
# 创建Query查询，filter是where条件，最后调用one()返回唯一行，如果调用all()则返回所有行:
user = session.query(User).filter(User.id == '5').one()
# 打印类型和对象的name属性:
print 'type:', type(user)
print 'name:', user.name
# 关闭Session:
session.close()


# 以下是关于外键和链接的设定
class User(Base):
    __tablename__ = 'user'

    id = Column(String(20), primary_key=True)
    name = Column(String(20))
    # 一对多:
    books = relationship('Book')


class Book(Base):
    # 必须定义表名
    __tablename__ = 'book'

    id = Column(String(20), primary_key=True)
    name = Column(String(20))
    # “多”的一方的book表是通过外键关联到user表的:
    user_id = Column(String(20), ForeignKey('user.id'))