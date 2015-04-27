package org.hope6537.note.design.mix.chain_command;

/**
 * 文件管理类（伪装）
 */
public class FileManager {

    /**
     * 显示数据
     */
    public static String ls(String path) {
        return "file1\nfile2\n\file3\n\file4\n";
    }

    public static String ls_l(String path) {
        return "drw-rw-rw root system 1024 2015-04-27 20:51 file1\n drw-rw-rw root system 1024 2015-04-27 20:51 file2\n drw-rw-rw root system 1024 2015-04-27 20:51 file3\n";
    }

    public static String ls_a(String path) {
        return ".\n..\nfile1\nfile2\nfile3\n";
    }

}
