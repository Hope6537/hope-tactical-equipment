import MySQLdb


def get_conn():
    conn = MySQLdb.connect(user='root', passwd='gintama123', host='www.hope6537.com', db='ComicHentai')
    return conn
