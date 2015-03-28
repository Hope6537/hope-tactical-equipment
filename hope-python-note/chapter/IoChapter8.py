# encoding:UTF-8
__author__ = 'Hope6537'
try:
    import cPickle as pickle
except ImportError:
    import pickle
# 数据序列化
d = dict(name='Bob', age=20, score=88)
pickle.dumps(d)
# pickle.dumps()方法把任意对象序列化成一个str，
# 然后，就可以把这个str写入文件。
# 或者用另一个方法pickle.dump()直接把对象序列化后写入一个file-like Object
with open("dump.txt", "w+") as f:
    pickle.dump(d, f)

# 然后我们再从写入处重新读回
with open("dump.txt", "rb") as f:
    d = pickle.load(f)
    # print(d.get("name"))

# 但是pickle不稳定，可能会出现不能反序列化的情况
# 这时候就需要json出场了
import json

with open("dump.txt", "w+") as f:
    json.dump(d, f)

with open("dump.txt", "rb") as f:
    d = json.load(f)
    print(d.get("age"))

# json进阶 类转化

class Student(object):
    def __init__(self, name, age, score):
        self.name = name
        self.age = age
        self.score = score


# def student2dict(std):
# return {
# 'name': std.name,
# 'age': std.age,
# 'score': std.score
# }


s = Student('Bob', 20, 88)
# json不能直接转化对象，所以需要定义一个转化函数
# print(json.dumps(s, default=student2dict))
# 当然我们可以把任意class的实例转化为dict
print(json.dumps(s, default=lambda obj: obj.__dict__))
# 因为通常class的实例都有一个__dict__属性，它就是一个dict，用来存储实例变量。
# 也有少数例外，比如定义了__slots__的class。
json_str = '{"age": 20, "score": 88, "name": "Bob"}'


def dict2student(d):
    return Student(d['name'], d['age'], d['score'])


print(json.loads(json_str, object_hook=dict2student))