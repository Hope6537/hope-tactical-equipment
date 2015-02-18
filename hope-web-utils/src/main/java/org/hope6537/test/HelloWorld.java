package org.hope6537.test;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class HelloWorld {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });
        Thread thread2 = new Thread(() ->
        {
            System.out.println("Hello Lambda");
            System.out.println("lambda is good");

        });
        thread.start();
        thread2.start();
    }


}
