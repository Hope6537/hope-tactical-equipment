package org.hope6537.note.design.factory.human;

/**
 * <p>Describe: 黑人的实现类</p>
 * <p>Using: 实现了人类的接口，作为黑人出现</p>
 * <p>DevelopedTime: 2014年9月5日下午3:27:01</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see Human
 */
public class BlackHuman implements Human {

    /*
     * (non-Javadoc)
     * @see org.hope6537.note.design.factory.Human#getColor()
     * @Change:Hope6537
     */
    @Override
    public void getColor() {

        System.out.println("黑色的皮肤");

    }

    /*
     * (non-Javadoc)
     * @see org.hope6537.note.design.factory.Human#talk()
     * @Change:Hope6537
     */
    @Override
    public void talk() {

        System.out.println("BalaBala~");

    }

}
