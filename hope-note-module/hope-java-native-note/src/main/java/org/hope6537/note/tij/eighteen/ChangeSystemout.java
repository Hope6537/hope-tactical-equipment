package org.hope6537.note.tij.eighteen;

import java.io.PrintWriter;

public class ChangeSystemout {
    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("Hello World");
    }
}
