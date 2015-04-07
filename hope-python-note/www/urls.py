# encoding:UTF-8
__author__ = 'Hope6537'

from transwarp.web import get, view
from models import User, Blog


# @view('test_users.html')
# @get('/')
# def test_users():
#     users = User.find_all()
#     return dict(users=users)


@view('blogs.html')
@get('/')
def index():
    blogs = Blog.find_all()
    # 查找登陆用户:
    user = User.find_first('where email=?', 'hope6537@qq.com')
    return dict(blogs=blogs, user=user)