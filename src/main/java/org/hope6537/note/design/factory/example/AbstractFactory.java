package org.hope6537.note.design.factory.example;

/**
 * <p>Describe: 公共的抽象方法类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午3:49:12</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public abstract class AbstractFactory {

    /**
     * <p>Describe: 创建一个产品对象</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年9月5日下午3:50:14 </p>
     * <p>Author:Hope6537</p>
     *
     * @param cls 该参数可以为不为class
     * @return
     * @see
     */
    public abstract <T extends Product> T createProduct(Class<T> cls);

}
