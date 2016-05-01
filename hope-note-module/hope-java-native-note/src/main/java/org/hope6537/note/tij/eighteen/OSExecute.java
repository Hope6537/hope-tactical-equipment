package org.hope6537.note.tij.eighteen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OSExecute {

    public static void command(String command) throws OSExecuteException {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader errors = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            while ((s = errors.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (Exception e) {
            if (!command.startsWith("CMD /C")) {
                command("CMD /C" + command);
            } else {
                throw new RuntimeException(e);
            }
        } finally {

        }
        if (err) {
            throw new OSExecuteException("�������д��� " + command);
        }
    }

    public static void main(String[] args) throws OSExecuteException {
        OSExecute.command("ping www.baidu.com -t");
    }
}

// : net/mindview/util/OSExecute.java
// Run an operating system command
// and send the output to the console.

class OSExecute2 {
    public static void command(String command) throws OSExecuteException {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null)
                System.out.println(s);
            BufferedReader errors = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            // Report errors and return nonzero value
            // to calling process if there are problems:
            while ((s = errors.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (Exception e) {
            // Compensate for Windows 2000, which throws an
            // exception for the default command line:
            if (!command.startsWith("CMD /C"))
                command("CMD /C " + command);
            else
                throw new RuntimeException(e);
        }
        if (err)
            throw new OSExecuteException("Errors executing " + command);
    }
} // /:~

