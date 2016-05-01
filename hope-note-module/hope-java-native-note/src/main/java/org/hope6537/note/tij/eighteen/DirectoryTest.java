package org.hope6537.note.tij.eighteen;

import java.io.File;

public class DirectoryTest {
    public static void main(String[] args) {
        PPrint.pprint(Directory.walk(".").dirs);
        for (File file : Directory.local(".", "T.*")) {
            System.out.println(file);
        }
        System.out.println("-----------------------------");
        for (File file : Directory.walk(".", "T.*\\.java")) {
            System.out.println(file);
        }
        System.out.println("=============================");
        for (File file : Directory.walk(".", ".*[Zz].*\\.class")) {
            System.out.println(file);
        }
        System.out.println("===========1==============================");
        for (File file : Directory.local(".", ".*")) {
            System.out.println(file);
        }
        System.out.println("===========2==============================");
        System.out.println(Directory.walk("."));
    }

}
