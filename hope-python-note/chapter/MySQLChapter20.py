# encoding:UTF-8
__author__ = 'Hope6537'

import mysql.connector

conn = mysql.connector.connect(user='root', password='*****', database='test', use_unicode=True)
# 获得一个链接实例
cursor = conn.cursor()
# cursor.execute('create table user (id varchar(20) primary key, name varchar(20))')
# MySQL的占位符是%s而不是?
# cursor.execute('insert into user (id, name) values (%s, %s)', ['1', 'Michael'])
print(cursor.rowcount)
# 提交事务
conn.commit()
cursor.close()

# 执行查询
cursor = conn.cursor()
cursor.execute('SELECT * FROM user WHERE id = 1')
values = cursor.fetchall()
print(values)
cursor.close()
conn.close()
