package org.hope6537.note.design.singleton;

/**
 * <p>
 * 单例模式的优点<br/>
 * 1、只有一个实例，减少了内存开支<br/>
 * 2、减少了系统的性能开销<br/>
 * 3、可以避免对资源的多重占用<br/>
 * 4、单例模式可以在系统中设置全局的访问点，优化和共享资源访问<br/>
 * <hr>
 * 单例模式的缺点<br/>
 * 1、没有接口，丧失拓展性<br/>
 * 2、無法進行測試<br/>
 * 3、單例模式把業務邏輯融合在一個類之中<br/>
 * </p>
 * <p>Describe: 单例模式通用代码</p>
 * <p>Using: 单例模式的使用</p>
 * <p>DevelopedTime: 2014年9月3日上午10:03:20</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class SingleTon {

    private static final SingleTon SINGLE_TON = new SingleTon();

    /**
     * <p>Describe: 通过将构造方法私有化，从而使对象无法由外部创建<p>
     */
    private SingleTon() {

    }

    public static SingleTon geSingleTon() {
        return SINGLE_TON;
    }

    /**
     * <p>Describe: 对象中的其他方法，最好是静态的</p>
     * <p>Using: 其他方法举例</p>
     * <p>How To Work: 使用类名直接调用</p>
     * <p>DevelopedTime: 2014年9月3日上午10:05:19 </p>
     * <p>Author:Hope6537</p>
     *
     * @see
     */
    public static void doSomething() {

    }
}

/**
 * <p>Describe: 线程不安全的单例模式的使用</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月3日上午10:20:32</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
class UnSafeSingleTon {

    private static UnSafeSingleTon unSafeSingleTon = null;

    private UnSafeSingleTon() {

    }

    public static UnSafeSingleTon getUnSafeSingleTon() {
        if (unSafeSingleTon == null) {
            unSafeSingleTon = new UnSafeSingleTon();
        }
        return unSafeSingleTon;
    }

}