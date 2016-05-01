# encoding:UTF-8
import os


def generate(objectName, columns):
    """
    生成基础数据对象
    :param objectName:
    :param columns:
    :return:
    """
    text = """
    package com.comichentai.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class {ObjectName}Do extends BasicDo {
    """
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
            /**""" + c[3] + """ */
            private String """ + c[0] + """;
            """
        if c[1] == 'int':
            text += """
            /**""" + c[3] + """ */
            private Integer """ + c[0] + """;
            """
    text += """
    public {ObjectName}Do() {

    }
    """
    params = ""
    body = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"

    text += """
        public {ObjectName}Do(""" + params[0:-1] + """) {

           """ + body + """

        }
        """
    for c in columns:
        firstCharUpper = c[0][0].upper() + c[0][1:]
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
                public String get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(String """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
        if c[1] == 'int':
            text += """
                public Integer get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(Integer """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
    text += """
    }
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./dataobject/" + objectName + "Do.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName
