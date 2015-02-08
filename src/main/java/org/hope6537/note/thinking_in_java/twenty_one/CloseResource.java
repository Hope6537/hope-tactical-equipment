package org.hope6537.note.thinking_in_java.twenty_one;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 采用关闭底层资源的方法来终止线程
 * @signdate 2014年8月8日下午4:48:09
 * @company Changchun University&SHXT
 */
public class CloseResource {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080)
                .getInputStream();
        exec.execute(new IOBlocked(socketInput));
        exec.execute(new IOBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("关闭所有的线程");
        exec.shutdownNow();
        // 一旦底层资源被关闭，那么线程将会终止阻塞 有个可以感受到的延时差
        TimeUnit.SECONDS.sleep(1);
        System.out.println("关闭" + socketInput.getClass().getName());
        socketInput.close();
        TimeUnit.SECONDS.sleep(1);
        // 但是实际上貌似没关上
        System.out.println("关闭" + System.in.getClass().getName());
        System.in.close();
    }
    /*
	 * Waiting for Read Waiting for Read 关闭所有的线程 关闭java.net.SocketInputStream
	 * Interrupting from io Exiting IO 关闭java.io.BufferedInputStream
	 * Interrupting from io Exiting IO
	 */

}
