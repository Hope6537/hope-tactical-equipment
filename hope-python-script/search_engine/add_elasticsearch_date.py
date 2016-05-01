# encoding:UTF-8
import commands
import json

from comic_hentai import comic_hentai_data_source


def get_comic():
    conn = comic_hentai_data_source.get_conn()
    find_comic_simple_info = "select id, title from Comic"
    cursor = conn.cursor()
    cursor.execute(find_comic_simple_info)
    result = cursor.fetchall()
    operator = "curl -XPUT "

    for r in result:
        id = str(r[0])
        title = str(r[1])
        title = title.replace("'", "&#39")
        data = json.dumps({
            "id": id,
            "title": title
        })
        url = r"'http://db.hope6537.com:9200/comichentai/comic/" + id + r"'"
        tmp = operator + url + " -d \'" + data + '\''
        print(tmp)
        commands.getstatusoutput(tmp)


get_comic()
