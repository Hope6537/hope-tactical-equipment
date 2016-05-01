# encoding:UTF-8
import hashlib

__author__ = 'Hope6537'

md5 = hashlib.md5()
md5.update("123456")
print md5.hexdigest()

sha1 = hashlib.sha1()
sha1.update('how to use sha1 in ')
sha1.update('python hashlib?')
print sha1.hexdigest()


def get_md5(password):
    md5 = hashlib.md5()
    md5.update(password)
    return md5.hexdigest()


def calc_md5(password):
    return get_md5(password + 'the-Salt')


print(calc_md5("123456"))

# 根据用户输入的登录名和口令模拟用户注册，计算更安全的MD5
db = {}


def register(username, password):
    db[username] = get_md5(password + username + 'the-Salt')
    print("<-" + db[username])


def login(username, password):
    message = db[username]
    print("->" + db[username])
    if message and get_md5(password + username + "the-Salt") == message:
        return True
    else:
        return False


register("hope6537", "1123")
print(login("hope6537", "1123"))
