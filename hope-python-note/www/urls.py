# encoding:UTF-8
__author__ = 'Hope6537'

from transwarp.web import get, view
from models import User


@view('test_users.html')
@get('/')
def test_users():
    users = User.find_all()
    return dict(users=users)