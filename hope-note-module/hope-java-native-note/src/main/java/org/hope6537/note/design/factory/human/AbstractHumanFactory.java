package org.hope6537.note.design.factory.human;

/**
 * <p>Describe: 人类创建的抽象工厂 </p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午3:49:45</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public abstract class AbstractHumanFactory {

    /**
     * <p>Describe: 创建一个人类</p>
     * <p>Using: 用于实例化人类对象时使用</p>
     * <p>How To Work: 使用指定的类型进行创建</p>
     * <p>DevelopedTime: 2014年9月5日下午3:35:02 </p>
     * <p>Author:Hope6537</p>
     *
     * @param cls 类型信息
     * @return 人类对象
     * @see
     */
    public abstract <T extends Human> T createHuman(Class<T> cls);
}
