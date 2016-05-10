package org.hope6537.note.jvm.volatile_;

        import static org.hope6537.note.jvm.volatile_.Safe.fucked_up;

/**
 * 无效的volatile场景
 * Created by hope6537 on 16/5/10.
 */
public class NotSafe {

    public static volatile int race = 0;

    public static void increase() {
        //在其他线程中可能加的是过期数据
        race++;
    }

    private static final int THREAD_COUNT = 20;

    public static void main(String[] args) throws Throwable {
        fucked_up(NotSafe.class);
    }

}
