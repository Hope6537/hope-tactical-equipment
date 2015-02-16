package org.hope6537.note.design.singleton;

import java.util.ArrayList;
import java.util.Random;

/**
 * <p>Describe: 产生固定数量的皇帝类</p>
 * <p>Using: 作为对象池来出现</p>
 * <p>DevelopedTime: 2014年9月3日上午10:25:16</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class Emperor {

    /**
     * <p>Describe: 单例模式下的最大对象数量级</p>
     * <p>Using: </p>
     */
    private static int maxNumOfEmperor = 2;
    /**
     * <p>Describe: 对象的描述名</p>
     * <p>Using: </p>
     */
    private static ArrayList<String> nameList = new ArrayList<String>();
    /**
     * <p>Describe: 所装载的皇帝對象</p>
     * <p>Using: </p>
     */
    private static ArrayList<Emperor> emperorList = new ArrayList<Emperor>();
    /**
     * <p>Describe: 当前的对象序列号</p>
     * <p>Using: </p>
     */
    private static int countNumOfEmperor = 0;

    static {
        for (int i = 0; i < maxNumOfEmperor; i++) {
            emperorList.add(new Emperor("皇帝" + (i + 1)));
        }
    }

    private Emperor() {

    }

    private Emperor(String string) {
        nameList.add(string);
    }

    public static Emperor getEmperor() {
        Random random = new Random(System.nanoTime());
        countNumOfEmperor = random.nextInt(maxNumOfEmperor);
        return emperorList.get(countNumOfEmperor);
    }

    public static void doSomething() {
        System.out.println(nameList.get(countNumOfEmperor));
    }

    public static void main(String[] args) {
        Minister.main(args);
    }
}

class Minister {
    public static void main(String[] args) {
        int count = 5;
        for (int i = 0; i < count; i++) {
            Emperor emperor = Emperor.getEmperor();
            System.out.println("This" + i + "for");
            emperor.doSomething();
        }
    }
}
