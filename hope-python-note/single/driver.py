# encoding:UTF-8
__author__ = 'Hope6537'


class Userinfo(object):
    __slots__ = ('_username', '_password', "_name")

    def __init__(self, username, password, name):
        self._username = username
        self._name = name
        self._password = password

    def __str__(self):
        print(self._username + " -> " + self._password + " -> " + self._name)


    _repr = __str__

    @property
    def username(self):
        return self._username

    @property
    def password(self):
        return self._password

    @property
    def name(self):
        return self._name

    def __getattr__(self, item):
        if item == "password":
            print("not have permission to get")
            return ""


def show_console_menu():
    while 1:
        print("====using for simple manage====")
        print("switch options")
        print("1 add object")
        print("2 search object")
        print("0 exit system")

        optionarray = [show_add_object, show_search_object]
        try:
            option = int(raw_input())
        except ValueError:
            print("input error")
            continue
        if option != 0:
            try:
                optionarray[option - 1]()
            except IndexError:
                print("input error")
        else:
            print(">~ Bye!")
            break


def show_add_object():
    print("add object")
    print("print user's info username , password and name")
    username = raw_input()
    password = raw_input()
    name = raw_input()


def show_search_object():
    print("search object")


def add_object():
    pass


if __name__ == '__main__':
    show_console_menu()