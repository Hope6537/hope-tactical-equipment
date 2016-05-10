package org.hope6537.note.jvm.volatile_;

/**
 * DCL单例模式,避免代码重排序
 * Created by hope6537 on 16/5/10.
 */
public class DCLSingleton {

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        DCLSingleton instance = DCLSingleton.getInstance();
        System.out.println(instance);
    }

}
